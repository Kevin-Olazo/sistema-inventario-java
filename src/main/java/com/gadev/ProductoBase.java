package com.gadev;

public abstract class ProductoBase {
    private int id;
    private String nombre;
    private double precioBase;
    private int stock;
    private Categoria categoria;

    public ProductoBase(int id, String nombre, double precioBase, int stock, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
        this.categoria = categoria;
    }

    public abstract double calcularPrecioFinal();

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    protected double calcularImpuesto(){
        return precioBase * getCategoria().getImpuesto();
    }

    protected double getPrecioConImpuesto() {
        return precioBase + calcularImpuesto();
    }
}
