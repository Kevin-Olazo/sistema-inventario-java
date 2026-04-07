package com.gadev;

public enum Categoria {
    ELECTRONICA(0.15),
    ALIMENTOS(0.05),
    VESTUARIO(0.10);

    private final double impuesto;

    Categoria(double impuesto){
        this.impuesto = impuesto;
    };

    public double getImpuesto() {
        return impuesto;
    }
}
