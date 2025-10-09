package com.mycompany.nequi;

import java.util.ArrayList;
import java.util.Scanner;
// prueba

public class NEQUI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<UsuarioNequi> usuarios = new ArrayList<>();
        UsuarioNequi usuarioActual = null;

        // Bancos disponibles
        Banco bancolombia = new Banco("Bancolombia", "001");
        Banco davivienda = new Banco("Davivienda", "002");
        Banco bancoBogota = new Banco("Banco de Bogota", "003");

        int opcion;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Iniciar sesion");
            System.out.println("3. Salir");
            System.out.print("Elija una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.println("\nSeleccione el banco:");
                    System.out.println("1. Bancolombia");
                    System.out.println("2. Davivienda");
                    System.out.println("3. Banco de Bogota");
                    System.out.print("Opcion: ");
                    int opcionBanco = sc.nextInt();
                    sc.nextLine();

                    Banco bancoSeleccionado;
                    switch (opcionBanco) {
                        case 1 -> bancoSeleccionado = bancolombia;
                        case 2 -> bancoSeleccionado = davivienda;
                        case 3 -> bancoSeleccionado = bancoBogota;
                        default -> {
                            System.out.println("Opcion invalida, se asignara Bancolombia por defecto.");
                            bancoSeleccionado = bancolombia;
                        }
                    }

                    System.out.print("Nombre del usuario: ");
                    String nombre = sc.nextLine();
                    System.out.print("Cedula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Numero de cuenta: ");
                    String numeroCuenta = sc.nextLine();
                    System.out.print("Saldo inicial: ");
                    double saldo = sc.nextDouble();
                    sc.nextLine();

                    CuentaNequi cuenta = new CuentaNequi(numeroCuenta, saldo, bancoSeleccionado);
                    UsuarioNequi nuevoUsuario = new UsuarioNequi(nombre, cedula, cuenta);
                    usuarios.add(nuevoUsuario);

                    System.out.println("Cuenta creada con exito en " + bancoSeleccionado.getNombre());
                }

                case 2 -> {
                    System.out.print("Ingrese su cedula: ");
                    String cedLogin = sc.nextLine();
                    System.out.print("Ingrese su numero de cuenta: ");
                    String cuentaLogin = sc.nextLine();

                    usuarioActual = null;
                    for (UsuarioNequi u : usuarios) {
                        if (u.getCedula().equals(cedLogin) &&
                                u.getCuenta().getNumeroCuenta().equals(cuentaLogin)) {
                            usuarioActual = u;
                            break;
                        }
                    }

                    if (usuarioActual != null) {
                        System.out.println("Inicio de sesion exitoso.");
                        int opcionSesion;
                        do {
                            System.out.println("\n=== MENU DE USUARIO ===");
                            System.out.println("1. Consultar saldo");
                            System.out.println("2. Consignar");
                            System.out.println("3. Retirar");
                            System.out.println("4. Ver movimientos");
                            System.out.println("5. Solicitar prestamo");
                            System.out.println("6. Pagar prestamo");
                            System.out.println("7. Mover dinero al colchon");
                            System.out.println("8. Sacar dinero del colchon");
                            System.out.println("9. Cerrar sesion");
                            System.out.print("Elija una opcion: ");
                            opcionSesion = sc.nextInt();
                            sc.nextLine();

                            switch (opcionSesion) {
                                case 1 -> 
                                    System.out.println("Saldo actual: $" + usuarioActual.getCuenta().getSaldo());

                                case 2 -> {
                                    System.out.print("Monto a consignar: ");
                                    double montoCon = sc.nextDouble();
                                    sc.nextLine();
                                    usuarioActual.getCuenta().agregarMovimiento(
                                            new Consignacion(montoCon, "2025-10-02"), usuarioActual
                                    );
                                    System.out.println("Consignacion realizada.");
                                }

                                case 3 -> {
                                    System.out.print("Monto a retirar: ");
                                    double montoRet = sc.nextDouble();
                                    sc.nextLine();
                                    if (montoRet <= usuarioActual.getCuenta().getSaldo()) {
                                        // Crear el movimiento de retiro
                                        Retiros retiro = new Retiros(montoRet, "2025-10-02");
                                        usuarioActual.getCuenta().agregarMovimiento(retiro, usuarioActual);

                                        // Confirmar el retiro en consola
                                        System.out.println("Retiro realizado. Monto: $" + montoRet);

                                        // Preguntar que hacer con la factura
                                        System.out.print("Desea generar factura en PDF del retiro (s), mostrarla en consola (c), o no (n)? ");
                                        String opcionFactura = sc.nextLine();

                                        if (opcionFactura.equalsIgnoreCase("s")) {
                                            Factura.generarFacturaMovimiento(usuarioActual, retiro);
                                        } else if (opcionFactura.equalsIgnoreCase("c")) {
                                            System.out.println("\n--- FACTURA RETIRO ---");
                                            System.out.println("Banco: " + usuarioActual.getCuenta().getBanco().getNombre());
                                            System.out.println("Cliente: " + usuarioActual.getNombre());
                                            System.out.println("Cedula: " + usuarioActual.getCedula());
                                            System.out.println("Cuenta: " + usuarioActual.getCuenta().getNumeroCuenta());
                                            System.out.println("Movimiento: Retiro");
                                            System.out.println("Monto: $" + String.format("%,.2f", retiro.getMonto()));
                                            System.out.println("Fecha: " + retiro.getFecha());
                                            System.out.println("Saldo disponible: $" + String.format("%,.2f", usuarioActual.getCuenta().getSaldo()));
                                            System.out.println("-------------------------\n");
                                        }
                                    } else {
                                        System.out.println("Saldo insuficiente.");
                                    }
                                }

                                case 4 -> 
                                    usuarioActual.getCuenta().mostrarInfo();

                                case 5 -> {
                                    System.out.print("Monto del prestamo: ");
                                    double montoPrest = sc.nextDouble();
                                    sc.nextLine();
                                    usuarioActual.getCuenta().solicitarPrestamo(montoPrest, 0.1, "2025-10-02");
                                }

                                case 6 -> {
                                    System.out.print("Monto a pagar del prestamo: ");
                                    double montoPago = sc.nextDouble();
                                    sc.nextLine();
                                    usuarioActual.getCuenta().pagarPrestamo(montoPago, "2025-10-02");
                                }

                                case 7 -> {
                                    System.out.print("Monto a mover al colchon: ");
                                    double montoCol = sc.nextDouble();
                                    sc.nextLine();
                                    usuarioActual.getCuenta().moverAlColchon(montoCol, "2025-10-02");
                                }

                                case 8 -> {
                                    System.out.print("Monto a retirar del colchon: ");
                                    double montoColOut = sc.nextDouble();
                                    sc.nextLine();
                                    usuarioActual.getCuenta().sacarDelColchon(montoColOut, "2025-10-02");
                                }

                                case 9 -> {
                                    System.out.println("Sesion cerrada.");

                                    // Preguntar si desea generar factura
                                    System.out.print("Desea generar factura de sus movimientos? (s/n): ");
                                    String respuesta = sc.nextLine();
                                    if (respuesta.equalsIgnoreCase("s")) {
                                        Factura.generarFacturaTotal(usuarioActual);
                                        System.out.println("Factura generada exitosamente.");
                                    }

                                    usuarioActual = null;
                                }

                                default -> 
                                    System.out.println("Opcion invalida.");
                            }
                        } while (usuarioActual != null && opcionSesion != 9);
                    } else {
                        System.out.println("Usuario o cuenta incorrectos.");
                    }
                }

                case 3 -> {
                    System.out.println("Saliendo del sistema...");
                    if (usuarioActual != null) {
                        System.out.print("Desea generar factura de sus movimientos antes de salir? (s/n): ");
                        String respuesta = sc.nextLine();
                        if (respuesta.equalsIgnoreCase("s")) {
                            Factura.generarFacturaTotal(usuarioActual);
                            System.out.println("Factura generada exitosamente.");
                        }
                    }
                }

                default -> 
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 3);

        sc.close();
    }
}