package com.banca.seguridad;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;

public class GestorCifrado {

    private static final String FICHERO_DATOS = "banca_clientes.dat";

    // Generador de Claves (Simétrico - AES)
    // En una app real, esta clave se guardaría en un Keystore seguro, no se generaría cada vez.
    public SecretKey generarClave() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // 128
            return keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Guardar Agenda Cifrada
    public void guardarAgenda(Agenda agenda, SecretKey clave) {
        try {
            // Configuramos el cifrador en modo ENCRIPTAR
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, clave);

            // Creamos los flujos: File -> Cipher -> Object
            FileOutputStream fos = new FileOutputStream(FICHERO_DATOS);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            ObjectOutputStream oos = new ObjectOutputStream(cos);

            // Escribimos el objeto (se cifra al vuelo)
            oos.writeObject(agenda);

            oos.close();
            System.out.println("🔒 Datos bancarios cifrados y guardados en '" + FICHERO_DATOS + "'");

        } catch (Exception e) {
            System.err.println("Error al guardar cifrado: " + e.getMessage());
        }
    }

    //Cargar Agenda Descifrada
    public Agenda cargarAgenda(SecretKey clave) {
        try {
            File archivo = new File(FICHERO_DATOS);
            if (!archivo.exists()) {
                return null; // No hay datos previos
            }

            // Configuramos el cifrador en modo DESENCRIPTAR
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, clave);

            // Creamos los flujos inversos: File -> Cipher -> Object
            FileInputStream fis = new FileInputStream(FICHERO_DATOS);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            ObjectInputStream ois = new ObjectInputStream(cis);

            // Leemos el objeto (se descifra al vuelo)
            Agenda agenda = (Agenda) ois.readObject();

            ois.close();
            System.out.println("🔓 Datos descifrados y cargados en memoria.");
            return agenda;

        } catch (Exception e) {
            System.err.println("Error al cargar (posible clave incorrecta o datos corruptos): " + e.getMessage());
            return new Agenda();
        }
    }
}