package com.novatech.service;

import java.time.LocalDate;
import java.util.List;

import com.novatech.dao.AuditoriaDAO;
import com.novatech.model.Auditoria;
import com.novatech.model.Usuario;
import com.novatech.util.Sesion;

public class AuditoriaService {

    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();

    public void registrar(String accion, String modulo, String descripcion) {

        Usuario usuario = Sesion.getUsuario();

        Auditoria auditoria = new Auditoria();

        auditoria.setIdAuditoria(usuario.getIdUsuario());
        auditoria.setUsuario(usuario.getUsuario());
        auditoria.setAccion(accion);
        auditoria.setModulo(modulo);
        auditoria.setFecha(LocalDate.now());

        auditoriaDAO.registrar(auditoria);

    }

    public List<Auditoria> listar() {

        return auditoriaDAO.listar();

    }

    public List<Auditoria> buscarPorUsuario(String usuario) {

        return auditoriaDAO.buscarPorUsuario(usuario);

    }

    public List<Auditoria> buscarPorModulo(String modulo) {

        return auditoriaDAO.buscarPorModulo(modulo);

    }

    public List<Auditoria> buscarPorFechas(LocalDate inicio, LocalDate fin) {

        return auditoriaDAO.buscarPorFecha(inicio, fin);

    }

}
