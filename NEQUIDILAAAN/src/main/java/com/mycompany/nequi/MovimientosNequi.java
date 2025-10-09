package com.mycompany.nequi;

/**
 * Clase abstracta MovimientosNequi:
 * Representa cualquier movimiento que puede hacer la cuenta (ej: consignación o retiro).
 * No se puede crear directamente, solo sirve como modelo para las subclases.
 */
public abstract class MovimientosNequi {
    protected double monto;
    protected String fecha;

    // Constructor común a todos los movimientos
    public MovimientosNequi(double monto, String fecha) {
        this.monto = monto;
        this.fecha = fecha;
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // Método abstracto que cada movimiento debe implementar a su manera
    public abstract void mostrarInfo();
}
