package com.novatech.service;

import com.novatech.dao.AuditoriaDAO;
import com.novatech.dao.UsuarioDAO;
import com.novatech.model.Rol;
import com.novatech.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioDAO usuarioDAO;
    private AuditoriaDAO auditoriaDAO;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {

        usuarioDAO = Mockito.mock(UsuarioDAO.class);

        usuarioService = new UsuarioService(usuarioDAO, auditoriaDAO);

    }

    private Usuario crearUsuarioValido() {

        Usuario usuario = new Usuario();

        usuario.setUsuario("admin");
        usuario.setNombre("Administrador");
        usuario.setContraseña("Password123");
        usuario.setRol(Rol.ADMINISTRADOR);
        usuario.setActivo(true);

        return usuario;

    }

    @Test
    void deberiaGuardarUsuarioValido() {

        Usuario usuario = crearUsuarioValido();

        assertDoesNotThrow(() -> usuarioService.crearUsuario(usuario));

        verify(usuarioDAO).insertar(usuario);

    }

    @Test
    void deberiaLanzarExcepcionSiUsuarioEstaVacio() {

        Usuario usuario = crearUsuarioValido();

        usuario.setUsuario("");

        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.crearUsuario(usuario)
        );

        verify(usuarioDAO, never()).insertar(any());

    }

    @Test
    void deberiaLanzarExcepcionSiNombreEstaVacio() {

        Usuario usuario = crearUsuarioValido();

        usuario.setNombre("");

        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.crearUsuario(usuario)
        );

    }

    @Test
    void deberiaLanzarExcepcionSiPasswordTieneMenosDe8Caracteres() {

        Usuario usuario = crearUsuarioValido();

        usuario.setContraseña("12345");

        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.crearUsuario(usuario)
        );

    }

    @Test
    void deberiaIniciarSesionCorrectamente() {

        Usuario usuario = crearUsuarioValido();

        when(usuarioDAO.buscarPorUsuario("admin"))
                .thenReturn(usuario);

        Usuario resultado =
                usuarioService.iniciarSesion(
                        "admin",
                        "Password123"
                );

        assertNotNull(resultado);

    }

    @Test
    void deberiaLanzarExcepcionSiUsuarioNoExiste() {

        when(usuarioDAO.buscarPorUsuario("admin"))
                .thenReturn(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.iniciarSesion(
                        "admin",
                        "Password123"
                )
        );

    }

    @Test
    void deberiaLanzarExcepcionSiUsuarioEstaInactivo() {

        Usuario usuario = crearUsuarioValido();

        usuario.setActivo(false);

        when(usuarioDAO.buscarPorUsuario("admin"))
                .thenReturn(usuario);

        assertThrows(
                IllegalStateException.class,
                () -> usuarioService.iniciarSesion(
                        "admin",
                        "Password123"
                )
        );

    }

    @Test
    void deberiaCambiarPassword() {

        Usuario usuario = crearUsuarioValido();

        when(usuarioDAO.buscarPorUsuario("admin"))
                .thenReturn(usuario);

        assertDoesNotThrow(() ->
                usuarioService.cambiarContraseña(1,
                        "admin",
                        "Password123",
                        "NuevaPassword123"
                )
        );

        verify(usuarioDAO).actualizar(any());

    }

    @Test
    void deberiaActivarUsuario() {

        Usuario usuario = crearUsuarioValido();

        usuario.setActivo(false);

        when(usuarioDAO.buscarPorId(1))
                .thenReturn(usuario);

        usuarioService.activarUsuario(1);

        assertTrue(usuario.isActivo());

        verify(usuarioDAO).actualizar(usuario);

    }

    @Test
    void deberiaDesactivarUsuario() {

        Usuario usuario = crearUsuarioValido();

        when(usuarioDAO.buscarPorId(1))
                .thenReturn(usuario);

        usuarioService.desactivarUsuario(1);

        assertFalse(usuario.isActivo());

        verify(usuarioDAO).actualizar(usuario);

    }

}
