package com.gadev;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProductoPerecible extends Producto {
    private LocalDate fechaVencimiento;

    public ProductoPerecible(int id, String nombre, double precioBase, int stock, Categoria categoria, LocalDate fechaVencimiento) {
        super(id, nombre, precioBase, stock, categoria);
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public boolean isExpired(){
        return fechaVencimiento.isBefore(LocalDate.now());
    }

    @Override
    public double calcularPrecioFinal() {
        double precioConImpuesto = getPrecioConImpuesto();

        LocalDate hoy = LocalDate.now();

        long diasFaltantes = ChronoUnit.DAYS.between(hoy, fechaVencimiento);

        if (diasFaltantes >= 0 && diasFaltantes < 5){
            precioConImpuesto = precioConImpuesto * 0.80; // 20% de descuento
        }

        return Math.round(precioConImpuesto * 100.0) / 100.0;
    }

    @Override
    public String toString(){
        return super.toString() +
                ", Fecha de vencimiento: " + getFechaVencimiento();
    }
}
