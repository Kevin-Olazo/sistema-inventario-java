package com.gadev;

public class Producto extends ProductoBase{
    public Producto(int id, String nombre, double precioBase, int stock, Categoria categoria) {
        super(id, nombre, precioBase, stock, categoria);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioConImpuesto();
    }

    @Override
    public String toString(){
        return super.toString() +
                ", Impuesto: " + calcularImpuesto() +
                ", Precio Final: " + calcularPrecioFinal();
    }


}
