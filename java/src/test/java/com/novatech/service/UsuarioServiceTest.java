package com.novatech.service;

import com.novatech.dao.AuditoriaDAO;
import com.novatech.dao.UsuarioDAO;
import com.novatech.model.Rol;
import com.novatech.model.Usuario;
import com.novatech.util.PasswordUtil;
import com.novatech.util.Sesion;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioDAO usuarioDAO;
    private AuditoriaDAO auditoriaDAO;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {

        usuarioDAO = Mockito.mock(UsuarioDAO.class);
        auditoriaDAO = Mockito.mock(AuditoriaDAO.class);

        usuarioService = new UsuarioService(usuarioDAO, auditoriaDAO);

        Sesion.cerrarSesion();

    }

    @AfterEach
    void tearDown() {
        Sesion.cerrarSesion();
    }

    private Usuario usuarioConPassword(int id, String usuarioNombre, String passwordPlana, boolean activo) {

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(id);
        usuario.setUsuario(usuarioNombre);
        usuario.setNombre("Nombre " + usuarioNombre);
        usuario.setContraseña(PasswordUtil.hashear(passwordPlana));
        usuario.setRol(Rol.VENDEDOR);
        usuario.setActivo(activo);

        return usuario;

    }

    @Test
    void deberiaGuardarUsuarioValido() {

        Usuario nuevo = new Usuario();
        nuevo.setUsuario("nuevo");
        nuevo.setNombre("Usuario Nuevo");
        nuevo.setContraseña("Password123");
        nuevo.setRol(Rol.VENDEDOR);

        when(usuarioDAO.buscarPorUsuario("nuevo")).thenReturn(null);
        when(usuarioDAO.insertar(any())).thenReturn(true);

        Sesion.setUsuario(usuarioConPassword(1, "admin", "Admin1234!", true));

        boolean resultado = usuarioService.crearUsuario(nuevo);

        assertTrue(resultado);
        verify(usuarioDAO).insertar(nuevo);
        verify(auditoriaDAO).registrar(any());

        assertNotEquals("Password123", nuevo.getContraseña());
        assertTrue(PasswordUtil.verificar("Password123", nuevo.getContraseña()));

    }

    @Test
    void deberiaRechazarContraseñaCorta() {

        Usuario nuevo = new Usuario();
        nuevo.setUsuario("nuevo");
        nuevo.setNombre("Usuario Nuevo");
        nuevo.setContraseña("corta");
        nuevo.setRol(Rol.VENDEDOR);

        boolean resultado = usuarioService.crearUsuario(nuevo);

        assertFalse(resultado);
        verify(usuarioDAO, never()).insertar(any());

    }

    @Test
    void deberiaRechazarUsuarioDuplicado() {

        Usuario nuevo = new Usuario();
        nuevo.setUsuario("existente");
        nuevo.setNombre("Usuario Existente");
        nuevo.setContraseña("Password123");
        nuevo.setRol(Rol.VENDEDOR);

        when(usuarioDAO.buscarPorUsuario("existente"))
                .thenReturn(usuarioConPassword(2, "existente", "Cualquiera1", true));

        boolean resultado = usuarioService.crearUsuario(nuevo);

        assertFalse(resultado);
        verify(usuarioDAO, never()).insertar(any());

    }

    @Test
    void deberiaRetornarNullSiUsuarioNoExiste() {

        when(usuarioDAO.buscarPorUsuario("fantasma")).thenReturn(null);

        Usuario resultado = usuarioService.iniciarSesion("fantasma", "cualquiera");

        assertNull(resultado);

    }

    @Test
    void deberiaRetornarNullSiUsuarioEstaInactivo() {

        Usuario inactivo = usuarioConPassword(3, "inactivo", "Password123", false);

        when(usuarioDAO.buscarPorUsuario("inactivo")).thenReturn(inactivo);

        Usuario resultado = usuarioService.iniciarSesion("inactivo", "Password123");

        assertNull(resultado);

    }

    @Test
    void deberiaRetornarNullSiContraseñaEsIncorrecta() {

        Usuario usuario = usuarioConPassword(4, "admin", "Password123", true);

        when(usuarioDAO.buscarPorUsuario("admin")).thenReturn(usuario);

        Usuario resultado = usuarioService.iniciarSesion("admin", "claveIncorrecta");

        assertNull(resultado);

    }

    @Test
    void deberiaIniciarSesionCorrectamente() {

        Usuario usuario = usuarioConPassword(5, "admin", "Password123", true);

        when(usuarioDAO.buscarPorUsuario("admin")).thenReturn(usuario);

        Usuario resultado = usuarioService.iniciarSesion("admin", "Password123");

        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsuario());
        assertEquals(usuario, Sesion.getUsuario());
        verify(auditoriaDAO).registrar(any());

    }

    @Test
    void deberiaActivarUsuario() {

        Sesion.setUsuario(usuarioConPassword(1, "admin", "Admin1234!", true));

        when(usuarioDAO.activar(10)).thenReturn(true);
        when(usuarioDAO.buscarPorId(10))
                .thenReturn(usuarioConPassword(10, "vendedor", "Vend1234!", false));

        boolean resultado = usuarioService.activarUsuario(10);

        assertTrue(resultado);
        verify(usuarioDAO).activar(10);
        verify(usuarioDAO, never()).actualizar(any());
        verify(auditoriaDAO).registrar(any());

    }

    @Test
    void deberiaDesactivarUsuario() {

        Sesion.setUsuario(usuarioConPassword(1, "admin", "Admin1234!", true));

        when(usuarioDAO.buscarPorId(10))
                .thenReturn(usuarioConPassword(10, "vendedor", "Vend1234!", true));

        when(usuarioDAO.desactivar(10)).thenReturn(true);

        boolean resultado = usuarioService.desactivarUsuario(10);

        assertTrue(resultado);
        verify(usuarioDAO).desactivar(10);
        verify(usuarioDAO, never()).actualizar(any());
        verify(auditoriaDAO).registrar(any());

    }

    @Test
    void deberiaRechazarAutoDesactivacion() {

        Usuario admin = usuarioConPassword(1, "admin", "Admin1234!", true);

        Sesion.setUsuario(admin);

        when(usuarioDAO.buscarPorId(1)).thenReturn(admin);

        boolean resultado = usuarioService.desactivarUsuario(1);

        assertFalse(resultado);
        verify(usuarioDAO, never()).desactivar(anyInt());

    }

    @Test
    void deberiaEliminarUsuario() {

        Sesion.setUsuario(usuarioConPassword(1, "admin", "Admin1234!", true));

        when(usuarioDAO.buscarPorId(10))
                .thenReturn(usuarioConPassword(10, "vendedor", "Vend1234!", true));

        when(usuarioDAO.eliminar(10)).thenReturn(true);

        boolean resultado = usuarioService.eliminarUsuario(10);

        assertTrue(resultado);
        verify(usuarioDAO).eliminar(10);
        verify(auditoriaDAO).registrar(any());

    }

    @Test
    void deberiaRechazarAutoEliminacion() {

        Usuario admin = usuarioConPassword(1, "admin", "Admin1234!", true);

        Sesion.setUsuario(admin);

        when(usuarioDAO.buscarPorId(1)).thenReturn(admin);

        boolean resultado = usuarioService.eliminarUsuario(1);

        assertFalse(resultado);
        verify(usuarioDAO, never()).eliminar(anyInt());

    }

    @Test
    void deberiaCambiarPasswordCorrectamente() {

        Usuario usuario = usuarioConPassword(10, "vendedor", "ActualPass1", true);

        when(usuarioDAO.buscarPorId(10)).thenReturn(usuario);
        when(usuarioDAO.cambiarContraseña(eq(10), anyString())).thenReturn(true);

        boolean resultado = usuarioService.cambiarContraseña(
                10, "ActualPass1", "NuevaPass1", "NuevaPass1");

        assertTrue(resultado);
        verify(usuarioDAO).cambiarContraseña(eq(10), anyString());
        verify(usuarioDAO, never()).actualizar(any());
        verify(auditoriaDAO).registrar(any());

    }

    @Test
    void deberiaRechazarCambioSiContraseñaActualEsIncorrecta() {

        Usuario usuario = usuarioConPassword(10, "vendedor", "ActualPass1", true);

        when(usuarioDAO.buscarPorId(10)).thenReturn(usuario);

        boolean resultado = usuarioService.cambiarContraseña(
                10, "ClaveMala", "NuevaPass1", "NuevaPass1");

        assertFalse(resultado);
        verify(usuarioDAO, never()).cambiarContraseña(anyInt(), anyString());

    }

    @Test
    void deberiaRechazarCambioSiNoCoincideConfirmacion() {

        Usuario usuario = usuarioConPassword(10, "vendedor", "ActualPass1", true);

        when(usuarioDAO.buscarPorId(10)).thenReturn(usuario);

        boolean resultado = usuarioService.cambiarContraseña(
                10, "ActualPass1", "NuevaPass1", "OtraCosa1");

        assertFalse(resultado);
        verify(usuarioDAO, never()).cambiarContraseña(anyInt(), anyString());

    }

}
