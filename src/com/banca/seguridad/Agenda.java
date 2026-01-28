package com.banca.seguridad;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

// ¡OJO! Debe ser Serializable para que GestorCifrado pueda procesarla
public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

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

    // Método auxiliar para limpiar la agenda
    public void vaciarAgenda() {
        listaContactos.clear();
    }
}