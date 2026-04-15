package com.gadev;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorInventario gestorInventario = new GestorInventario();

        MenuInventario menuInventario = new MenuInventario(gestorInventario, scanner);

        menuInventario.start();

    }

}