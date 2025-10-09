package com.mycompany.nequi;

/**
 * Clase UsuarioNequi:
 * Representa al usuario del sistema.
 * Relación: Asociación -> un Usuario tiene una CuentaNequi.
 */
public class UsuarioNequi {
    private String nombre;
    private String cedula;
    private CuentaNequi cuenta; // Asociación: un usuario está ligado a una cuenta

    // Constructor: recibimos datos del usuario y su cuenta (puede pasarse null si no tiene)
    public UsuarioNequi(String nombre, String cedula, CuentaNequi cuenta) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.cuenta = cuenta;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public CuentaNequi getCuenta() { return cuenta; }
    public void setCuenta(CuentaNequi cuenta) { this.cuenta = cuenta; }

    /**
     * Mostrar info del usuario.
     * Si tiene cuenta, delega a cuenta.mostrarInfo().
     * (Aquí se nota la asociación: Usuario usa Cuenta.)
     */
    public void mostrarInfo() {
        System.out.println("Usuario: " + nombre + " - Cedula: " + cedula);
        if (cuenta != null) {
            // mostramos también la info de la cuenta asociada
            cuenta.mostrarInfo();
        } else {
            System.out.println("Este usuario aún no tiene una cuenta asociada.");
        }
    }
}
