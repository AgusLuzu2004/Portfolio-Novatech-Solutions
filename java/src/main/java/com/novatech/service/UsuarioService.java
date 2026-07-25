package com.novatech.service;

import java.time.LocalDate;
import java.util.List;

import com.novatech.dao.AuditoriaDAO;
import com.novatech.dao.UsuarioDAO;
import com.novatech.model.Auditoria;
import com.novatech.model.Usuario;
import com.novatech.util.Sesion;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();

    public Usuario iniciarSesion(String usuario, String contraseña) {

        if (usuario == null || usuario.trim().isEmpty()) {
            return null;
        }

        if (contraseña == null || contraseña.trim().isEmpty()) {
            return null;
        }

        Usuario usuarioBD = usuarioDAO.buscarPorUsuario(usuario);

        if (usuarioBD == null) {
            return null;
        }

        if (!usuarioBD.isActivo()) {
            return null;
        }

        if (!usuarioBD.getContraseña().equals(contraseña)) {
            return null;
        }

        Sesion.setUsuario(usuarioBD);

        auditoriaDAO.registrar(new Auditoria(
                usuarioBD.getIdUsuario(),
                usuarioBD.getUsuario(),
                "LOGIN",
                "Usuarios",
                LocalDate.now()
        ));

        return usuarioBD;

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

        boolean resultado = usuarioDAO.insertar(usuario);

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "INSERT",
                    "Usuarios",
                    LocalDate.now()
            ));

        }

        return resultado;

    }

    public boolean actualizarUsuario(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getNombre().trim().isEmpty()) {
            return false;
        }

        boolean resultado = usuarioDAO.actualizar(usuario);

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "UPDATE",
                    "Usuarios",
                    LocalDate.now()
            ));

        }

        return resultado;

    }

    public boolean cambiarContraseña(int id, String actual, String nueva, String confirmar) {

        Usuario usuario = usuarioDAO.buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        if (!usuario.getContraseña().equals(actual)) {
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

        boolean resultado = usuarioDAO.cambiarContraseña(id, nueva);

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    usuario.getIdUsuario(),
                    usuario.getUsuario(),
                    "UPDATE",
                    "Usuarios",
                    LocalDate.now()
            ));

        }

        return resultado;

    }

    public boolean activarUsuario(int id) {

        boolean resultado = usuarioDAO.activar(id);

        if (resultado) {

            Usuario usuario = usuarioDAO.buscarPorId(id);

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "ACTIVAR",
                    "Usuarios",
                    LocalDate.now()
            ));

        }

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

        if (resultado) {

            auditoriaDAO.registrar(new Auditoria(
                    Sesion.getUsuario().getIdUsuario(),
                    Sesion.getUsuario().getUsuario(),
                    "DESACTIVAR",
                    "Usuarios",
                    LocalDate.now()
            ));

        }

        return resultado;

    }

    public List<Usuario> buscar(String texto) {
        return usuarioDAO.buscarPorNombre(texto);
    }

}
