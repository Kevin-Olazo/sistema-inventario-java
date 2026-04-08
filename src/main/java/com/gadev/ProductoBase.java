package com.gadev;

public abstract class ProductoBase implements ServicioStock {
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
        return Math.round(precioBase * categoria.getImpuesto() * 100.0) / 100.0;
    }

    protected double getPrecioConImpuesto() {
        return Math.round((precioBase + calcularImpuesto()) * 100.0) / 100.0;
    }

    @Override
    public void incrementarStock(int cantidad) {
        if (cantidad <= 0){
            throw new IllegalArgumentException("Error, cantidad no es un numero valido");
        }

        this.stock = this.stock + cantidad;
    }

    @Override
    public void disminuirStock(int cantidad) throws StockInsuficienteException {
        if (cantidad > this.stock){
            throw new StockInsuficienteException("No hay suficiente stock");
        }
        this.stock = this.stock - cantidad;
    }

    @Override
    public String toString() {
        return "Producto " +
                "ID: " + id +
                ", Nombre: " + nombre +
                ", Precio Base: " + precioBase +
                ", Stock: " + stock +
                ", Categoria: " + categoria;
    }
}
