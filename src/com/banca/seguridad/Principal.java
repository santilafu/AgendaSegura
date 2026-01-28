package com.banca.seguridad;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = new Agenda();
        boolean salir = false;

        System.out.println("--- GESTIÓN DE AGENDA BANCARIA SEGURA ---");

        while (!salir) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Añadir nuevo contacto");
            System.out.println("2. Listar contactos");
            System.out.println("3. Salir");
            System.out.print("> ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\n--- NUEVO REGISTRO ---");

                    System.out.print("Nombre del cliente: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();

                    // --- NUEVOS CAMPOS SOLICITADOS ---
                    System.out.print("Domicilio: ");
                    String domicilio = scanner.nextLine();

                    System.out.print("Código Postal: ");
                    String cp = scanner.nextLine();

                    System.out.print("Ciudad: ");
                    String ciudad = scanner.nextLine();
                    // ---------------------------------

                    // Creamos el contacto con los 5 atributos
                    Contacto nuevoContacto = new Contacto(nombre, telefono, domicilio, cp, ciudad);
                    agenda.anadirContacto(nuevoContacto);
                    System.out.println("✅ Contacto guardado correctamente en memoria.");
                    break;

                case "2":
                    System.out.println("\n--- LISTADO DE CLIENTES ---");
                    if (agenda.getListaContactos().isEmpty()) {
                        System.out.println("No hay contactos registrados.");
                    } else {
                        for (Contacto c : agenda.getListaContactos()) {
                            System.out.println(c); // Esto usa el toString() actualizado
                        }
                    }
                    break;

                case "3":
                    salir = true;
                    System.out.println("Cerrando aplicación segura...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
}