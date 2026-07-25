package com.novatech.util;

import com.novatech.model.Usuario;

public class Sesion {

    private static Usuario usuario;

    private Sesion() {

    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Sesion.usuario = usuario;
    }

    public static void cerrarSesion() {
        usuario = null;
    }

    public static boolean haySesion() {
        return usuario != null;
    }

}
