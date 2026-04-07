package com.gadev;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Producto producto1 = new Producto(1, "Laptop", 1000.0, 10, Categoria.ELECTRONICA);
        Producto producto2 = new Producto(2, "Manzana", 0.5, 100, Categoria.ALIMENTOS);
        Producto producto3 = new Producto(3, "Camisa", 20.0, 50, Categoria.VESTUARIO);

        Producto producto4 = new ProductoPerecible(4, "Leche", 1.0, 30, Categoria.ALIMENTOS, LocalDate.now().plusDays(3));
        Producto producto5 = new ProductoPerecible(5, "Yogur", 0.8, 20, Categoria.ALIMENTOS, LocalDate.now().plusDays(10));
        Producto producto6 = new ProductoPerecible(6, "Queso", 5.0, 15, Categoria.ALIMENTOS, LocalDate.now().plusDays(1));


        System.out.println(producto1);

        System.out.println();
        System.out.println(producto2);

        System.out.println();
        System.out.println(producto3);

        System.out.println();
        System.out.println(producto4);

        System.out.println();
        System.out.println(producto5);

        System.out.println();
        System.out.println(producto6);

    }

}