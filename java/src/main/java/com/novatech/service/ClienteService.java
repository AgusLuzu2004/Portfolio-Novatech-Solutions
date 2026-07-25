package com.novatech.service;

import com.novatech.dao.ClienteDAO;
import com.novatech.model.Cliente;
import java.time.LocalDate;
import java.util.List;

public class ClienteService {

    private ClienteDAO clienteDAO = new ClienteDAO();

    public void guardarCliente(Cliente cliente) {

        validarCliente(cliente);

        clienteDAO.insertar(cliente);

    }

    public void actualizarCliente(Cliente cliente) {

        validarCliente(cliente);

        clienteDAO.actualizar(cliente);

    }

    public void eliminarCliente(int id) {

        clienteDAO.eliminar(id);

    }

    private void validarCliente(Cliente cliente) {

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (cliente.getApellido() == null || cliente.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }

        if (cliente.getEdad() < 18 || cliente.getEdad() > 75) {
            throw new IllegalArgumentException("La edad debe estar entre 18 y 75 años.");
        }

        if (cliente.getProvincia() == null || cliente.getProvincia().isBlank()) {
            throw new IllegalArgumentException("Debe seleccionar una provincia.");
        }

        if (cliente.getCiudad() == null || cliente.getCiudad().isBlank()) {
            throw new IllegalArgumentException("Debe ingresar una ciudad.");
        }

        if (cliente.getFechaAlta() == null) {
            throw new IllegalArgumentException("Debe ingresar una fecha.");
        }

        if (cliente.getFechaAlta().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser futura.");
        }

        if (cliente.getNombre().length() > 80) {
            throw new IllegalArgumentException("El nombre es demasiado largo.");
        }

        if (cliente.getApellido().length() > 80) {
            throw new IllegalArgumentException("El apellido es demasiado largo.");
        }

    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listarClientes();
    }

    public List<Cliente> buscar(String texto) {
        return clienteDAO.buscarPorNombre(texto);
    }

    public List<Cliente> buscarPorProvincia(String provincia) {
        return clienteDAO.buscarPorProvincia(provincia);
    }

    public List<String> listarProvincias() {
        return clienteDAO.listarProvincias();
    }
}
