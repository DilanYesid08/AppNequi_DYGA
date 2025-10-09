package com.mycompany.nequi;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Retiros extends MovimientosNequi {

    // Constructor con fecha explícita (como tenía)
    public Retiros(double monto, String fecha) {
        super(monto, fecha);
    }

    // Constructor que asigna la fecha actual automáticamente
    public Retiros(double monto) {
        super(monto, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Retiro de $" + monto + " el " + fecha);
    }
}
