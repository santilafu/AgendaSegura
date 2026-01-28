package com.banca.seguridad;

import javax.crypto.SecretKey;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Iniciamos seguridad y generamos la clave de esta sesión (La clave "buena")
        GestorCifrado seguridad = new GestorCifrado();
        SecretKey claveSesion = seguridad.generarClave();

        Agenda agenda = new Agenda();
        boolean salir = false;

        System.out.println("=== 🏦 SISTEMA BANCARIO SEGURO (AES-128) ===");
        System.out.println("ℹ️  Nota: La clave de cifrado se ha generado en memoria.");

        while (!salir) {
            System.out.println("\n--- MENÚ DE OPERACIONES ---");
            System.out.println("1. Añadir cliente (Memoria)");
            System.out.println("2. Ver listado (Memoria)");
            System.out.println("3. 💾 GUARDAR (Cifrar y volcar a disco)");
            System.out.println("4. 📂 CARGAR (Leer de disco y descifrar)");
            System.out.println("5. 🗑️ Vaciar memoria (Para probar carga limpia)");
            System.out.println("6. 🏴‍☠️ SIMULAR ATAQUE (Intento de robo de datos)");
            System.out.println("7. Salir");
            System.out.print("> Elige opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Nombre: "); String n = scanner.nextLine();
                    System.out.print("Teléfono: "); String t = scanner.nextLine();
                    System.out.print("Domicilio: "); String d = scanner.nextLine();
                    System.out.print("CP: "); String cp = scanner.nextLine();
                    System.out.print("Ciudad: "); String c = scanner.nextLine();

                    agenda.anadirContacto(new Contacto(n, t, d, cp, c));
                    System.out.println("✅ Cliente añadido a memoria.");
                    break;

                case "2":
                    System.out.println("\n--- LISTA DE CLIENTES EN MEMORIA ---");
                    if (agenda.getListaContactos().isEmpty()) System.out.println("(Lista vacía)");
                    for (Contacto contacto : agenda.getListaContactos()) {
                        System.out.println(contacto);
                    }
                    break;

                case "3":
                    seguridad.guardarAgenda(agenda, claveSesion);
                    break;

                case "4":
                    Agenda cargada = seguridad.cargarAgenda(claveSesion);
                    if (cargada != null) {
                        agenda = cargada;
                        System.out.println("📂 Datos recuperados y descifrados correctamente.");
                    }
                    break;

                case "5":
                    agenda.vaciarAgenda();
                    System.out.println("⚠️ Memoria vaciada. La lista actual está en blanco.");
                    break;

                case "6":
                    System.out.println("\n--- 🏴‍☠️ INICIANDO SIMULACIÓN DE ATAQUE ---");
                    System.out.println("Escenario: Un atacante ha copiado el archivo 'clientes_seguros.aes'");
                    System.out.println("Acción: El atacante intenta leerlo con su propia clave generada.");

                    // 1. Generamos una clave FALSA (distinta a claveSesion)
                    SecretKey claveAtacante = seguridad.generarClave();

                    // 2. Intentamos descifrar con la clave falsa
                    Agenda agendaRobada = seguridad.cargarAgenda(claveAtacante);

                    // 3. Análisis del resultado
                    if (agendaRobada == null) {
                        System.out.println("🛡️ SEGURIDAD ROBUSTA: El sistema ha rechazado el descifrado.");
                        System.out.println("   El archivo es ilegible sin la clave original.");
                    } else {
                        System.out.println("❌ ERROR CRÍTICO: Se ha podido leer el archivo (esto no debería pasar).");
                    }
                    break;

                case "7":
                    salir = true;
                    System.out.println("Cerrando sistema...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }
}