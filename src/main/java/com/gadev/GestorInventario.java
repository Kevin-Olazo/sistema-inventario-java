package com.gadev;

import java.util.List;
import java.util.Optional;

public class GestorInventario {
    private Inventario<ProductoBase> inventario;

    public GestorInventario(Inventario<ProductoBase> inventario) {
        this.inventario = inventario;
    }

    public GestorInventario() {
        this.inventario = new Inventario<>();
    }

    public List<ProductoBase> generarReporteBajoStock(int limite) {
        return inventario.obtenerTodos().stream()
                .filter(productoBase -> productoBase.getStock() < limite)
                .toList();
    }

    public List<ProductoPerecible> listarSoloPereciblesVencidos() {
        return inventario.obtenerTodos().stream()
                .filter(p -> p instanceof ProductoPerecible)
                .map(p -> (ProductoPerecible) p)
                .filter(ProductoPerecible::isExpired)
                .toList();
    }

    public double calcularValorTotalInventario() {
        return inventario.obtenerTodos().stream()
                .mapToDouble(p -> p.calcularPrecioFinal() * p.getStock())
                .sum();
    }

    public Optional<ProductoBase> buscarPorNombre(String nombre) throws ProductoNoEncontradoException {
        return inventario.obtenerTodos().stream()
                .filter(productoBase -> productoBase.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    public ProductoBase obtenerParaVenta(String nombre) throws ProductoNoEncontradoException {
        return buscarPorNombre(nombre)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto con nombre '" + nombre + "' no encontrado"));
    }


    public ProductoBase buscarPorId(int id) throws ProductoNoEncontradoException {
        return inventario.buscarPorId(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado"));
    }

    public boolean existeProducto(int id){
        return inventario.buscarPorId(id).isPresent();
    }


    public List<ProductoBase> listarTodos(){
        return inventario.obtenerTodos();
    }

    public List<ProductoBase> filtrarPorCategoria(Categoria categoria){
        return inventario.obtenerTodos().stream()
                .filter(productoBase -> productoBase.getCategoria().equals(categoria))
                .toList();
    }

    public void registrarNuevoProducto(ProductoBase producto){
        inventario.agregar(producto);
    }

}
