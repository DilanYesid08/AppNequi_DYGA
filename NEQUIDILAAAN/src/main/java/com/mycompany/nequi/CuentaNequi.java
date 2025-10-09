package com.mycompany.nequi;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase CuentaNequi:
 * Representa la cuenta de un usuario en Nequi.
 * Incluye saldo, movimientos, préstamos y colchón de ahorro.
 */
public class CuentaNequi {
    private String numeroCuenta;
    private double saldo;
    private Banco banco;
    private List<MovimientosNequi> movimientos;

    // --- NUEVOS ATRIBUTOS ---
    private double deuda;       // deuda total (préstamos)
    private double colchon;     // dinero apartado en el colchón

    // Constructor
    public CuentaNequi(String numeroCuenta, double saldo, Banco banco) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.banco = banco;
        this.movimientos = new ArrayList<>();
        this.deuda = 0;
        this.colchon = 0;
    }

    /**
     * Versión simple: agrega movimiento y actualiza saldo (no genera factura)
     */
    public void agregarMovimiento(MovimientosNequi mov) {
        movimientos.add(mov);
        if (mov instanceof Consignacion) {
            saldo += mov.getMonto();
        } else if (mov instanceof Retiros) {
            saldo -= mov.getMonto();
        }
    }

    /**
     * Versión con usuario: agrega movimiento, actualiza saldo y genera factura PDF para ese movimiento
     */
    public void agregarMovimiento(MovimientosNequi mov, UsuarioNequi usuario) {
        agregarMovimiento(mov); // reutiliza la lógica de arriba
        // Generar factura automática (cada movimiento tiene su factura)
        Factura.generarFacturaMovimiento(usuario, mov);
    }

    // --- MÉTODOS DE PRÉSTAMOS ---
    public boolean solicitarPrestamo(double monto, double interes, String fecha) {
        if (monto <= 0) return false;
        double totalDeuda = monto + (monto * interes);
        deuda += totalDeuda;
        saldo += monto; // se abona a la cuenta el monto prestado
        // registramos el movimiento usando la versión simple (sin usuario)
        agregarMovimiento(new Consignacion(monto, fecha));
        System.out.println("Préstamo aprobado por $" + monto + ". Deuda total con interés: $" + totalDeuda);
        return true;
    }

    public boolean pagarPrestamo(double monto, String fecha) {
        if (monto <= 0 || monto > saldo || deuda == 0) return false;
        deuda -= monto;
        // registramos el retiro que corresponde al pago (actualiza saldo)
        agregarMovimiento(new Retiros(monto, fecha));
        System.out.println("Pago de préstamo realizado. Deuda restante: $" + deuda);
        return true;
    }

    public double getDeuda() { return deuda; }

    // --- MÉTODOS DEL COLCHÓN ---
    public boolean moverAlColchon(double monto, String fecha) {
        if (monto <= 0 || monto > saldo) return false; 
        // movemos al colchón y registramos el retiro (actualiza saldo)
        colchon += monto;
        agregarMovimiento(new Retiros(monto, fecha));
        System.out.println("Se movieron $" + monto + " al colchón.");
        return true;
    }

    public boolean sacarDelColchon(double monto, String fecha) {
        if (monto <= 0 || monto > colchon) return false;
        colchon -= monto;
        // ingresar a cuenta y registrar consignación (actualiza saldo)
        agregarMovimiento(new Consignacion(monto, fecha));
        System.out.println("Se retiraron $" + monto + " del colchón.");
        return true;
    }

    public double getColchon() { return colchon; }

    // --- INFORMACIÓN ---
    public void mostrarInfo() {
        System.out.println("Cuenta N.: " + numeroCuenta + " - Banco: " + banco.getNombre());
        System.out.println("Saldo actual: $" + saldo);
        System.out.println("Deuda actual (préstamos): $" + deuda);
        System.out.println("Dinero en colchón: $" + colchon);
        System.out.println("Movimientos:");
        for (MovimientosNequi m : movimientos) {
            m.mostrarInfo();
        }
    }

    // Getters y setters
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public Banco getBanco() { return banco; }
    public void setBanco(Banco banco) { this.banco = banco; }

    // 🔹 NUEVO: Getter para movimientos
    public List<MovimientosNequi> getMovimientos() {
        return movimientos;
    }
}
