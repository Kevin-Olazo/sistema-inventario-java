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

    public Optional<ProductoBase> buscarPorNombre(String nombre) {

        return inventario.obtenerTodos().stream()
                .filter(productoBase -> productoBase.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .findFirst();

    }

}
