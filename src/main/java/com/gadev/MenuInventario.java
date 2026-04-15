package com.gadev;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

        agregarDatosPrueba();
        listarProductos();

        while (continuar) {
            printMenu();
            comando = elegirOpcion();

            switch (comando) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    agregarStock();
                    break;
                case 3:
                    realizarVenta();
                    break;
                case 4:
                    buscarProductoPorNombre();
                    break;
                case 5:
                    printMenuReportes();
                    break;
                case 6:
                    mostrarEstadisticas();
                    break;
                case 7:
                    continuar = false;
                    break;
                default:
                    System.out.println("Ingrese un comando valido (1-7)");
            }

        }
    }

    // 1. Agregar producto
    public void agregarProducto() {
        ingresarDatosProducto();
    }

    public void ingresarDatosProducto() {
        // 1. Preguntar tipo de producto
        System.out.println("Que tipo de producto desea agregar?");
        System.out.println("1. Producto regular");
        System.out.println("2. Producto perecible");
        int tipoEleccion = pedirNumeroEntero("Ingrese el tipo de producto (1-2): ");

        // 2. Pedir datos comunes
        int id;
        while (true) {
            id = pedirNumeroEntero("Ingrese id: ");

            if (gestorInventario.existeProducto(id)) {
                System.out.println("Ya existe un producto registrado con el ID " + id + ". Ingrese uno diferente");
            } else {
                break;
            }
        }

        System.out.print("Ingrese Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese Precio: ");
        double precioBase = Double.parseDouble(scanner.nextLine());

        int stock = pedirNumeroEntero("Ingrese Stock: ");

        ProductoBase nuevoProducto;

        if (tipoEleccion == 1) {
            Categoria categoria = null;
            System.out.println("Categorías: ");
            System.out.println("1. Electronica");
            System.out.println("2. Vestuario");
            int tipoCategoria = pedirNumeroEntero("Elige una categoria: ");
            switch (tipoCategoria) {
                case 1:
                    categoria = Categoria.ELECTRONICA;
                    break;
                case 2:
                    categoria = Categoria.VESTUARIO;
                    break;
                default:
                    System.out.println("Debes elegir una categoria");
            }
            nuevoProducto = new Producto(id, nombre, precioBase, stock, categoria);
        } else if (tipoEleccion == 2) {
            System.out.println("Ingrese fecha de vencimiento (yyyy-MM-dd): ");
            LocalDate fechaVencimiento = LocalDate.parse(scanner.nextLine());
            nuevoProducto = new ProductoPerecible(id, nombre, precioBase, stock, Categoria.ALIMENTOS, fechaVencimiento);
        } else {
            System.out.println("Opción invalida");
            return;
        }

        gestorInventario.registrarNuevoProducto(nuevoProducto);
        System.out.println("Producto agregado con éxito!");
    }

    public void agregarStock() {
        int id = pedirNumeroEntero("Ingrese el ID del producto a agregar stock");
        try {
            ProductoBase p = gestorInventario.buscarPorId(id);
            if (p != null) {
                System.out.println("Stock actual - " + p.getStock());
                int stock = pedirNumeroEntero("Ingrese stock a agregar:");
                p.incrementarStock(stock);
                System.out.println("Nuevo stock: " + p.getStock());
            }
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void buscarProductoPorNombre() {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();

        Optional<ProductoBase> resultado = gestorInventario.buscarPorNombre(nombre);
        if (resultado.isPresent()) {
            System.out.println(resultado.get());
        } else {
            System.out.println("No existe");
        }

    }

    public void mostrarEstadisticas() {
        System.out.println("Total inventario: $" + gestorInventario.calcularValorTotalInventario());
    }

    public void realizarVenta() {
        System.out.print("Ingrese el nombre del producto que desea vender: ");
        String nombre = scanner.nextLine();

        try {
            ProductoBase producto = gestorInventario.obtenerParaVenta(nombre);

            System.out.println("Stock actual: " + producto.getStock());
            int cantidadVenta = pedirNumeroEntero("Ingrese la cantidad que desea vender: ");

            producto.disminuirStock(cantidadVenta);
            System.out.println("Venta realizada exitosamente!");

        } catch (ProductoNoEncontradoException e) {
            System.out.println("Aviso: " + e.getMessage());
        } catch (StockInsuficienteException ex) {
            System.out.println("No hay suficiente stock.");
        }
    }


    public void listarProductos() {
        for (ProductoBase pb : gestorInventario.listarTodos()) {
            System.out.println(pb.toString());
        }
    }

    public void listarProductosVencidos() {
        for (ProductoPerecible p : gestorInventario.listarSoloPereciblesVencidos()) {
            System.out.println(p.toString());
        }
    }

    public void listarProductosCategoria() {
        System.out.println("Categorías: ");
        System.out.println("1. Electrónicos");
        System.out.println("2. Alimentos");
        System.out.println("3. Vestuario");

        int categoriaEleccion = pedirNumeroEntero("Elige una categoria: ");

        switch (categoriaEleccion) {
            case 1:
                printListaCategoria(gestorInventario.filtrarPorCategoria(Categoria.ELECTRONICA));
                break;
            case 2:
                printListaCategoria(gestorInventario.filtrarPorCategoria(Categoria.ALIMENTOS));
                break;
            case 3:
                printListaCategoria(gestorInventario.filtrarPorCategoria(Categoria.VESTUARIO));
                break;
            default:
                System.out.println("Elige una categoria valida (1-3)");
        }
    }

    public void verProductosBajoStock() {
        int limite = pedirNumeroEntero("Ingrese un limite de bajo stock: ");
        List<ProductoBase> lista = gestorInventario.generarReporteBajoStock(limite);
        if (!lista.isEmpty()) {
            for (ProductoBase p : lista) {
                System.out.println(p);
            }
        }
    }

    public int elegirOpcion() {
        return pedirNumeroEntero("Elige una opción: ");
    }

    public void printListaCategoria(List<ProductoBase> productos) {
        for (ProductoBase p : productos) {
            System.out.println(p.toString());
        }

    }

    public void printMenu() {
        System.out.println("1. Agregar Producto");
        System.out.println("2. Agregar stock");
        System.out.println("3. Realizar Venta");
        System.out.println("4. Buscar producto por nombre");
        System.out.println("5. Reportes");
        System.out.println("6. Estadísticas");
        System.out.println("7. Salir");
    }

    public void printMenuReportes() {
        System.out.println("1. Ver todos los productos");
        System.out.println("2. Ver productos con bajo stock");
        System.out.println("3. Ver productos perecibles Vencidos");
        System.out.println("4. Ver productos por categoria");
        int comando = pedirNumeroEntero("Elige una opción: ");

        switch (comando) {
            case 1:
                listarProductos();
                break;
            case 2:
                verProductosBajoStock();
                break;
            case 3:
                listarProductosVencidos();
                break;
            case 4:
                listarProductosCategoria();
                break;
            default:
                System.out.println("Elige una opción valida (1-3)");
        }

    }

    public int pedirNumeroEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese solo caracteres numéricos.");
            }
        }
    }

    public void agregarDatosPrueba() {
        // 30 productos de prueba
        ProductoBase p1 = new Producto(1, "Laptop", 999.99, 10, Categoria.ELECTRONICA);
        ProductoBase p2 = new Producto(2, "Smartphone", 499.99, 20, Categoria.ELECTRONICA);
        ProductoBase p3 = new Producto(3, "Tablet", 299.99, 15, Categoria.ELECTRONICA);
        ProductoBase p4 = new Producto(4, "Cámara", 199.99, 5, Categoria.ELECTRONICA);
        ProductoBase p5 = new Producto(5, "Auriculares", 49.99, 25, Categoria.ELECTRONICA);
        ProductoBase p6 = new Producto(6, "Ropa", 39.99, 30, Categoria.VESTUARIO);
        ProductoBase p7 = new Producto(7, "Zapatos", 79.99, 20, Categoria.VESTUARIO);
        ProductoBase p8 = new Producto(8, "Chaqueta", 59.99, 10, Categoria.VESTUARIO);
        ProductoBase p9 = new Producto(9, "Camisa", 29.99, 50, Categoria.VESTUARIO);
        ProductoBase p10 = new Producto(10, "Pantalones", 49.99, 40, Categoria.VESTUARIO);
        ProductoBase p11 = new ProductoPerecible(11, "Leche", 1.99, 100, Categoria.ALIMENTOS, LocalDate.now().plusDays(5));
        ProductoBase p12 = new ProductoPerecible(12, "Pan", 0.99, 50, Categoria.ALIMENTOS, LocalDate.now().plusDays(2));
        ProductoBase p13 = new ProductoPerecible(13, "Queso", 4.99, 30, Categoria.ALIMENTOS, LocalDate.now().plusDays(10));
        ProductoBase p14 = new ProductoPerecible(14, "Yogur", 0.99, 20, Categoria.ALIMENTOS, LocalDate.now().plusDays(7));
        ProductoBase p15 = new ProductoPerecible(15, "Carne", 9.99, 15, Categoria.ALIMENTOS, LocalDate.now().plusDays(3));
        ProductoBase p16 = new ProductoPerecible(16, "Frutas", 2.99, 40, Categoria.ALIMENTOS, LocalDate.now().plusDays(4));
        ProductoBase p17 = new ProductoPerecible(17, "Verduras", 1.99, 60, Categoria.ALIMENTOS, LocalDate.now().plusDays(6));
        ProductoBase p18 = new ProductoPerecible(18, "Huevos", 3.99, 30, Categoria.ALIMENTOS, LocalDate.now().plusDays(8));
        ProductoBase p19 = new ProductoPerecible(19, "Mantequilla", 2.99, 25, Categoria.ALIMENTOS, LocalDate.now().plusDays(9));
        ProductoBase p20 = new ProductoPerecible(20, "Jamon", 5.99, 20, Categoria.ALIMENTOS, LocalDate.now().plusDays(12));
        ProductoBase p21 = new Producto(21, "Monitor", 199.99, 10, Categoria.ELECTRONICA);
        ProductoBase p22 = new Producto(22, "Teclado", 49.99, 20, Categoria.ELECTRONICA);
        ProductoBase p23 = new Producto(23, "Ratón", 29.99, 30, Categoria.ELECTRONICA);
        ProductoBase p24 = new Producto(24, "Impresora", 149.99, 5, Categoria.ELECTRONICA);
        ProductoBase p25 = new Producto(25, "Router", 89.99, 15, Categoria.ELECTRONICA);
        ProductoBase p26 = new Producto(26, "Camiseta", 19.99, 50, Categoria.VESTUARIO);
        ProductoBase p27 = new Producto(27, "Pantalones cortos", 29.99, 40, Categoria.VESTUARIO);
        ProductoBase p28 = new Producto(28, "Vestido", 49.99, 20, Categoria.VESTUARIO);
        ProductoBase p29 = new Producto(29, "Falda", 39.99, 30, Categoria.VESTUARIO);
        ProductoBase p30 = new Producto(30, "Suéter", 59.99, 25, Categoria.VESTUARIO);

        // Agrega productos vencidos
        ProductoBase p31 = new ProductoPerecible(31, "Leche Vencida", 1.99, 100, Categoria.ALIMENTOS, LocalDate.now().minusDays(5));
        ProductoBase p32 = new ProductoPerecible(32, "Pan Vencido", 0.99, 50, Categoria.ALIMENTOS, LocalDate.now().minusDays(2));
        ProductoBase p33 = new ProductoPerecible(33, "Queso Vencido", 4.99, 30, Categoria.ALIMENTOS, LocalDate.now().minusDays(10));
        ProductoBase p34 = new ProductoPerecible(34, "Yogur Vencido", 0.99, 20, Categoria.ALIMENTOS, LocalDate.now().minusDays(7));
        ProductoBase p35 = new ProductoPerecible(35, "Carne Vencida", 9.99, 15, Categoria.ALIMENTOS, LocalDate.now().minusDays(3));

        List<ProductoBase> productosPrueba = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10,
                p11, p12, p13, p14, p15, p16, p17, p18, p19, p20,
                p21, p22, p23, p24, p25, p26, p27, p28, p29, p30,
                p31, p32, p33, p34, p35);

        for (ProductoBase p : productosPrueba) {
            gestorInventario.registrarNuevoProducto(p);
        }

    }
}
