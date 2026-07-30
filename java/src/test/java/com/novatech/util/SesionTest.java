package com.novatech.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.novatech.model.Rol;
import com.novatech.model.Usuario;

public class SesionTest {

    @BeforeEach
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
