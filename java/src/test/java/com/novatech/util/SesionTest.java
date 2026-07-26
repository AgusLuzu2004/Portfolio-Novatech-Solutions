package com.novatech.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.novatech.model.Rol;
import com.novatech.model.Usuario;

public class SesionTest {

    @Before
    public void setUp() {
        Sesion.cerrarSesion();
    }

    @Test
    public void debeIniciarSinUnaSesionActiva() {
        assertNull(Sesion.getUsuario());
        assertFalse(Sesion.haySesion());
    }

    @Test
    public void debeGestionarElEstadoDeLaSesion() {
        Usuario usuario = new Usuario(1, "admin", "Administrador", "secreto", Rol.ADMINISTRADOR, true);

        Sesion.setUsuario(usuario);

        assertSame(usuario, Sesion.getUsuario());
        assertTrue(Sesion.haySesion());

        Sesion.cerrarSesion();

        assertNull(Sesion.getUsuario());
        assertFalse(Sesion.haySesion());
    }
}
