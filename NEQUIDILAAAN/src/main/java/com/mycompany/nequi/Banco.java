package com.mycompany.nequi;

/**
 * Clase Banco: representa un banco dentro del sistema Nequi.
 * Una cuenta Nequi está asociada a un banco.
 */
public class Banco {
    private String nombre;
    private String codigo;

    // Constructor: sirve para crear un banco con su nombre y código.
    public Banco(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    // Métodos para obtener o cambiar los datos del banco.
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    // Mostrar la información del banco
    public void mostrarInfo() {
        System.out.println("Banco: " + nombre + " - Codigo: " + codigo);
    }
}
