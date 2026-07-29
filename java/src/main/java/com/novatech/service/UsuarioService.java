package com.novatech.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatech.dao.AuditoriaDAO;
import com.novatech.dao.UsuarioDAO;
import com.novatech.model.Auditoria;
import com.novatech.model.Usuario;
import com.novatech.util.PasswordUtil;
import com.novatech.util.Sesion;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;
    private AuditoriaDAO auditoriaDAO;

    private static final Logger logger =
        LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.auditoriaDAO = new AuditoriaDAO();
    }

    public UsuarioService(UsuarioDAO usuarioDAO, AuditoriaDAO auditoriaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.auditoriaDAO = auditoriaDAO;
    }

    public Usuario iniciarSesion(String usuario, String contraseña) {

        try {

            logger.info("Intento de inicio de sesión para el usuario: {}", usuario);

            if (usuario == null || usuario.trim().isEmpty()) {

                logger.warn("Nombre de usuario vacío.");

                return null;
            }

            if (contraseña == null || contraseña.trim().isEmpty()) {

                logger.warn("Contraseña vacía para el usuario: {}", usuario);

                return null;
            }

            Usuario usuarioBD = usuarioDAO.buscarPorUsuario(usuario);

            if (usuarioBD == null) {

                logger.warn("Usuario inexistente: {}", usuario);

                return null;
            }

            if (!usuarioBD.isActivo()) {

                logger.warn("Intento de acceso con usuario inactivo: {}", usuario);

                return null;
            }

            if (!PasswordUtil.verificar(contraseña, usuarioBD.getContraseña())) {

                logger.warn("Contraseña incorrecta para el usuario: {}", usuario);

                return null;
            }

            Sesion.setUsuario(usuarioBD);

            auditoriaDAO.registrar(new Auditoria(
                    usuarioBD.getIdUsuario(),
                    usuarioBD.getUsuario(),
                    "LOGIN",
                    "Usuarios",
                    LocalDateTime.now()
            ));

            logger.info("Inicio de sesión exitoso: {}", usuario);

            return usuarioBD;

        } catch (Exception e) {

            logger.error("Error durante el inicio de sesión.", e);

            throw e;

        }

    }

    public List<Usuario> listarUsuarios() {

        return usuarioDAO.listarUsuarios();

    }

    public boolean crearUsuario(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getUsuario().trim().isEmpty()) {
            return false;
        }

        if (usuario.getNombre().trim().isEmpty()) {
            return false;
        }

        if (usuario.getContraseña().length() < 8) {
            return false;
        }

        if (usuario.getRol() == null) {
            return false;
        }

        if (usuarioDAO.buscarPorUsuario(usuario.getUsuario()) != null) {
            return false;
        }

        usuario.setContraseña(PasswordUtil.hashear(usuario.getContraseña()));

        boolean resultado = usuarioDAO.insertar(usuario);

        try {

            logger.info("Intentando crear usuario: {}", usuario.getUsuario());

            if (resultado) {

                logger.info(
                    "Usuario creado correctamente: {}",
                    usuario.getUsuario());

            }

            return resultado;

        } catch (Exception e) {

            logger.error("Error al crear usuario.", e);

            throw e;

        }

    }

    public boolean actualizarUsuario(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getNombre().trim().isEmpty()) {
            return false;
        }

        boolean resultado = usuarioDAO.actualizar(usuario);

        logger.info(
            "Actualizando usuario ID {}",
            usuario.getIdUsuario()
        );

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "UPDATE",
                    "Usuarios",
                    LocalDateTime.now()
            ));

        }

        logger.info(
            "Usuario actualizado correctamente. ID {}",
            usuario.getIdUsuario()
        );

        return resultado;

    }

    public boolean cambiarContraseña(int id, String actual, String nueva, String confirmar) {

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        if (!PasswordUtil.verificar(actual, usuario.getContraseña())) {
            return false;
        }

        if (nueva.length() < 8) {
            return false;
        }

        if (!nueva.equals(confirmar)) {
            return false;
        }

        if (actual.equals(nueva)) {
            return false;
        }

        boolean resultado = usuarioDAO.cambiarContraseña(id, PasswordUtil.hashear(nueva));

        logger.info(
            "Cambio de contraseña solicitado para el usuario ID {}",
            id
        );

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    usuario.getIdUsuario(),
                    usuario.getUsuario(),
                    "UPDATE",
                    "Usuarios",
                    LocalDateTime.now()
            ));

        }

        logger.info(
            "Contraseña modificada correctamente para el usuario ID {}",
            id
        );

        return resultado;

    }

    public boolean activarUsuario(int id) {

        boolean resultado = usuarioDAO.activar(id);

        if (resultado) {

            Usuario usuario = usuarioDAO.buscarPorId(id);

            logger.info(
                "Activando usuario ID {}",
                id
            );

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "ACTIVAR",
                    "Usuarios",
                    LocalDateTime.now()
            ));

        }

        logger.info(
            "Usuario activado correctamente. ID {}",
            id
        );

        return resultado;

    }

    public boolean desactivarUsuario(int id) {

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        if (Sesion.getUsuario().getIdUsuario() == id) {
            return false;
        }

        boolean resultado = usuarioDAO.desactivar(id);

        logger.info(
            "Desactivando usuario ID {}",
            id
        );

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "DESACTIVAR",
                    "Usuarios",
                    LocalDateTime.now()
            ));

        }

        logger.info(
            "Usuario desactivado correctamente. ID {}",
            id
        );

        logger.warn(
            "El usuario {} intentó desactivar su propia cuenta.",
            Sesion.getUsuario().getUsuario()
        );

        return resultado;

    }

    public List<Usuario> buscar(String texto) {
        return usuarioDAO.buscarPorNombre(texto);
    }

    public boolean eliminarUsuario(int id) {

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        if (Sesion.getUsuario().getIdUsuario() == id) {
            return false;
        }

        boolean resultado = usuarioDAO.eliminar(id);

        logger.info(
            "Intentando eliminar usuario ID {}",
            id
        );

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "ELIMINAR",
                    "Usuarios",
                    LocalDateTime.now()
            ));

        }

        logger.info(
            "Usuario eliminado correctamente. ID {}",
            id
        );

        logger.warn(
            "El usuario {} intentó eliminar su propia cuenta.",
            Sesion.getUsuario().getUsuario()
        );

        return resultado;

    }

}
