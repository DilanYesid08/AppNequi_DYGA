package com.mycompany.nequi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Consignacion extends MovimientosNequi {

    // el constructor antiguo (fecha pasada como String)
    public Consignacion(double monto, String fecha) {
        super(monto, fecha);
    }

    // Nuevo constructor que genera la fecha actual automáticamente (formato dd/MM/yyyy HH:mm:ss)
    public Consignacion(double monto) {
        super(monto, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Consignacion de $" + monto + " el " + fecha);
    }
}
