package com.gadev;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class MenuInventario {
    private Scanner scanner;
    private GestorInventario gestorInventario;

    public MenuInventario() {
        this.scanner = new Scanner(System.in);
        this.gestorInventario = new GestorInventario();
    }

    public MenuInventario(GestorInventario gestorInventario, Scanner scanner) {
        this.gestorInventario = gestorInventario;
        this.scanner = scanner;
    }

    public void start() {

        boolean continuar = true;
        int comando = 0;

        while (continuar) {
            printMenu();
            comando = elegirOpcion();

            switch (comando) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    realizarVenta();
                    break;
                case 3:
                    listarProductos();
                    break;
                case 4:
                    System.out.println("4 ");
                    break;
                case 5:
                    System.out.println("5 ");
                    break;
                case 6:
                    System.out.println("6 ");
                    break;
                case 7:
                    continuar = false;
                    break;
                default:
                    System.out.println("Ingrese un comando valido (1-7)");
            }

        }
    }

    public void agregarProducto() {
        ingresarDatosProducto();

    }

    public void ingresarDatosProducto() {
        // 1. Preguntar tipo de producto
        System.out.println("Que tipo de producto desea agregar?");
        System.out.println("1. Producto regular");
        System.out.println("2. Producto perecible");
        int tipoEleccion = Integer.parseInt(scanner.nextLine());

        // 2. Pedir datos comunes
        System.out.print("Ingrese id: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese Precio: ");
        double precioBase = Double.parseDouble(scanner.nextLine());

        System.out.print("Ingrese Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());

        ProductoBase nuevoProducto;


        if (tipoEleccion == 1) {
            Categoria categoria = null;
            System.out.println("Categorías: ");
            System.out.println("1. Electronica");
            System.out.println("2. Vestuario");
            System.out.print("Elige una categoria: ");

            int tipoCategoria = Integer.parseInt(scanner.nextLine());
            switch (tipoCategoria) {
                case 1: categoria = Categoria.ELECTRONICA; break;
                case 2: categoria = Categoria.VESTUARIO; break;
                default:
                    System.out.println("Debes elegir una categoria");
            }
            nuevoProducto = new Producto(id,nombre,precioBase,stock,categoria);
        } else if(tipoEleccion == 2){
            System.out.println("Ingrese fecha de vencimiento (yyyy-MM-dd): ");
            LocalDate fechaVencimiento = LocalDate.parse(scanner.nextLine());
            nuevoProducto = new ProductoPerecible(id,nombre,precioBase,stock,Categoria.ALIMENTOS,fechaVencimiento);
        } else {
            System.out.println("Opción invalida");
            return;
        }

        gestorInventario.registrarNuevoProducto(nuevoProducto);
        System.out.println("Producto agregado con éxito!");
    }


    public void realizarVenta(){
        System.out.print("Ingrese el nombre del producto que desea vender: ");
        String nombre = scanner.nextLine();

        try {
            Optional<ProductoBase> productoBase = gestorInventario.buscarPorNombre(nombre);
            if (productoBase.isPresent()){
                System.out.println("Stock actual: " + productoBase.get().getStock());
                System.out.print("Ingrese la cantidad que desea vender: ");
                int cantidadVenta = Integer.parseInt(scanner.nextLine());
                productoBase.get().disminuirStock(cantidadVenta);
            } else{
                System.out.println("Producto no encontrado");
            }
        } catch (ProductoNoEncontradoException e){
            System.out.println("Error: " + e.getMessage());
        } catch (StockInsuficienteException ex){
            System.out.println("No hay suficiente stock.");
        }

        System.out.println("Venta realizada exitosamente!");

    }


    public void listarProductos(){
        for(ProductoBase pb : gestorInventario.listarTodos()){
            System.out.println(pb.toString());
        }
    }

    public int elegirOpcion() {
        int comando = 0;
        System.out.print("Elige una opción: ");
        try {
            comando = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un valor numérico");
        }

        return comando;
    }

    public void printMenu() {
        System.out.println("1. Agregar Producto");
        System.out.println("2. Realizar Venta");
        System.out.println("3. Ver todos los productos");
        System.out.println("4. Ver productos perecibles a punto de vencer");
        System.out.println("5. Ver producto por categoria");
        System.out.println("6. Estadísticas");
        System.out.println("7. Salir");
    }
}
