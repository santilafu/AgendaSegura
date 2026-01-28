package com.banca.seguridad;

import java.util.LinkedList;
import java.util.List;

public class Agenda {

    private List<Contacto> listaContactos;

    public Agenda() {
        this.listaContactos = new LinkedList<>();
    }

    public void anadirContacto(Contacto c) {
        listaContactos.add(c);
    }

    public List<Contacto> getListaContactos() {
        return listaContactos;
    }

    // Aquí irían tus métodos de guardar/cargar con cifrado (AES/DES)
    // que veremos en la implementación de seguridad completa.
}