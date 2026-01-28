package com.banca.seguridad;

import java.io.Serializable;

public class Contacto implements Serializable {
    // Version UID para asegurar compatibilidad en la serialización
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String telefono;
    // Nuevos atributos para la aplicación bancaria
    private String domicilio;
    private String codigoPostal;
    private String ciudad;

    // Constructor actualizado con los 5 parámetros
    public Contacto(String nombre, String telefono, String domicilio, String codigoPostal, String ciudad) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    // Método toString actualizado para mostrar toda la información
    @Override
    public String toString() {
        return "Cliente: " + nombre +
                " | Tlf: " + telefono +
                " | Dirección: " + domicilio + ", " + codigoPostal + " (" + ciudad + ")";
    }
}