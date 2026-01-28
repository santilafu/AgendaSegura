package com.banca.seguridad;

import javax.crypto.SecretKey;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scannerInput = new Scanner(System.in); // Scanner solo para el login

        System.out.println("=== 🏦 PORTAL DE BANCA SEGURA (UNIDAD 5) ===");

        GestorFirma gestorIdentidad = new GestorFirma();

        // Generamos los archivos necesarios la primera vez para que puedas probar
        gestorIdentidad.generarFirmaDePrueba();


        // PASO 1: SOLICITUD MANUAL DE CREDENCIALES
        System.out.println("\n🔒 CONTROL DE ACCESO REQUERIDO");
        System.out.println("Por favor, introduzca la ruta de su archivo de Firma Digital.");
        System.out.print("> Archivo de firma: ");

        // El usuario escribe el nombre (ej: identidad.firma)
        String rutaFirma = scannerInput.nextLine();

        System.out.println("Analizando archivo '" + rutaFirma + "'...");

        // Pasamos lo que ha escrito el usuario al verificador
        boolean accesoPermitido = gestorIdentidad.verificarIdentidad(rutaFirma);

        if (accesoPermitido) {
            System.out.println("\n✅ IDENTIDAD CONFIRMADA.");
            System.out.println("   Bienvenido, Operador de Banca.");
            ejecutarMenuPrincipal(); // Entramos a la app
        } else {
            System.out.println("\n⛔ ACCESO DENEGADO.");
            System.out.println("   La firma digital no es válida o no corresponde a un usuario autorizado.");
            System.exit(403);
        }
    }

            // EJERCICIO 6: GESTIÓN DE DATOS CIFRADOS (AES)
            private static void ejecutarMenuPrincipal() {
                Scanner scanner = new Scanner(System.in);

                // Preparamos el cifrado AES para los datos (Agenda)
                GestorCifrado seguridadDatos = new GestorCifrado();
                SecretKey claveSesion = seguridadDatos.generarClave();

                Agenda agenda = new Agenda();
                boolean salir = false;

                System.out.println("\n--- SISTEMA DE GESTIÓN DE CLIENTES (Cifrado AES-128) ---");

                while (!salir) {
                    System.out.println("\n--- MENÚ OPERATIVO ---");
                    System.out.println("1. 📝 Añadir cliente (Memoria)");
                    System.out.println("2. 👁️ Ver listado (Memoria)");
                    System.out.println("3. 💾 GUARDAR (Cifrar a disco)");
                    System.out.println("4. 📂 CARGAR (Descifrar de disco)");
                    System.out.println("5. 🗑️ Vaciar memoria (Pruebas)");
                    System.out.println("6. 🏴‍☠️ SIMULAR ATAQUE (Robo de datos AES)"); // ¡RECUPERADO!
                    System.out.println("7. 🚪 Salir");
                    System.out.print("> Seleccione opción: ");

                    String opcion = scanner.nextLine();

                    switch (opcion) {
                        case "1":
                            System.out.print("Nombre: "); String n = scanner.nextLine();
                            System.out.print("Teléfono: "); String t = scanner.nextLine();
                            System.out.print("Domicilio: "); String d = scanner.nextLine(); // Nuevos atributos
                            System.out.print("CP: "); String cp = scanner.nextLine();
                            System.out.print("Ciudad: "); String c = scanner.nextLine();
                            agenda.anadirContacto(new Contacto(n, t, d, cp, c));
                            System.out.println("   -> Cliente registrado en RAM.");
                            break;

                        case "2":
                            System.out.println("\n--- LISTADO ---");
                            if(agenda.getListaContactos().isEmpty()) System.out.println("(Vacío)");
                            for(Contacto con : agenda.getListaContactos()) System.out.println(con);
                            break;

                        case "3":
                            seguridadDatos.guardarAgenda(agenda, claveSesion);
                            break;

                        case "4":
                            Agenda cargada = seguridadDatos.cargarAgenda(claveSesion);
                            if(cargada != null) {
                                agenda = cargada;
                                System.out.println("   -> Base de datos descifrada y cargada.");
                            }
                            break;

                        case "5":
                            agenda.vaciarAgenda();
                            System.out.println("   -> Memoria local borrada.");
                            break;

                        case "6":
                            // Lógica del Ejercicio 6: Intento de descifrado con clave incorrecta
                            System.out.println("\n--- 🚨 SIMULACIÓN DE ROBO DE ARCHIVOS ---");
                            System.out.println("Escenario: Un atacante roba 'clientes_seguros.aes' e intenta abrirlo.");

                            SecretKey claveHacker = seguridadDatos.generarClave(); // Clave distinta
                            Agenda intentoRobo = seguridadDatos.cargarAgenda(claveHacker);

                            if (intentoRobo == null) {
                                System.out.println("🛡️ RESULTADO: El cifrado AES ha resistido.");
                                System.out.println("   Java lanzó 'BadPaddingException' al no coincidir la clave.");
                            } else {
                                System.out.println("❌ FALLO: Se han leído datos (Imposible si AES funciona).");
                            }
                            break;

                        case "7":
                            salir = true;
                            System.out.println("Cerrando sesión segura...");
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                }
                scanner.close();
            }
        }