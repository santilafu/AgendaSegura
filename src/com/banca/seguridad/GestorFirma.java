package com.banca.seguridad;

import java.io.*;
import java.security.*;

public class GestorFirma {

    private static final String FICHERO_CLAVE_PUB = "banco_clave.publica";
    private static final String FICHERO_CLAVE_PRIV = "banco_clave.privada";

    private static final String DATOS_A_VERIFICAR = "SOLICITUD_ACCESO_BANCA_2026";

    /**
     * Genera un archivo 'identidad.firma' válido para que tengas algo con lo que probar.
     */
    public void generarFirmaDePrueba() {
        File fPub = new File(FICHERO_CLAVE_PUB);
        if (!fPub.exists()) {
            System.out.println("⚙️ Generando entorno de pruebas (Claves y Firma 'identidad.firma')...");
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(2048);
                KeyPair par = keyGen.generateKeyPair();

                // Guardamos las claves
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_CLAVE_PUB))) {
                    oos.writeObject(par.getPublic());
                }

                // Creamos la firma válida por defecto
                Signature firma = Signature.getInstance("SHA256withRSA");
                firma.initSign(par.getPrivate());
                firma.update(DATOS_A_VERIFICAR.getBytes());
                byte[] firmaBytes = firma.sign();

                try (FileOutputStream fos = new FileOutputStream("identidad.firma")) {
                    fos.write(firmaBytes);
                }
                System.out.println("✅ Firma de prueba generada en archivo: 'identidad.firma'");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * AHORA RECIBE LA RUTA DEL ARCHIVO
     */
    public boolean verificarIdentidad(String rutaArchivoFirma) {
        try {
            File ficheroFirma = new File(rutaArchivoFirma);
            if (!ficheroFirma.exists()) {
                System.out.println("❌ Error: El archivo '" + rutaArchivoFirma + "' no existe.");
                return false;
            }

            //Cargar Clave Pública
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO_CLAVE_PUB));
            PublicKey clavePublica = (PublicKey) ois.readObject();
            ois.close();

            // Leer la firma que ha indicado el usuario
            byte[] firmaBytes = new byte[(int) ficheroFirma.length()];
            try (FileInputStream fis = new FileInputStream(ficheroFirma)) {
                fis.read(firmaBytes);
            }

            //Verificar
            Signature verificador = Signature.getInstance("SHA256withRSA");
            verificador.initVerify(clavePublica);
            verificador.update(DATOS_A_VERIFICAR.getBytes());

            return verificador.verify(firmaBytes);

        } catch (Exception e) {
            System.err.println("Error técnico en verificación: " + e.getMessage());
            return false;
        }
    }
}