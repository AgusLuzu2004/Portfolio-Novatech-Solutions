package com.novatech.service;

import com.novatech.dao.ClienteDAO;
import com.novatech.model.Cliente;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClienteService {

    private ClienteDAO clienteDAO;

    private static final Logger logger =
            LoggerFactory.getLogger(ClienteService.class);

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void guardarCliente(Cliente cliente) {

        try {

            validarCliente(cliente);

            logger.info("Intentando guardar cliente: {} {}",
                    cliente.getNombre(),
                    cliente.getApellido());

            clienteDAO.insertar(cliente);

            logger.info("Cliente guardado correctamente. ID: {}",
                    cliente.getIdCliente());

        } catch (IllegalArgumentException e) {

            logger.warn("Error de validación al guardar cliente: {}", e.getMessage());
            throw e;

        } catch (Exception e) {

            logger.error("Error inesperado al guardar cliente.", e);
            throw e;

        }

    }

    public void actualizarCliente(Cliente cliente) {

        try {

            validarCliente(cliente);

            logger.info("Intentando actualizar cliente: {} {}",
                    cliente.getNombre(),
                    cliente.getApellido());

            clienteDAO.actualizar(cliente);

            logger.info("Cliente actualizado correctamente. ID: {}",
                    cliente.getIdCliente());

        } catch (IllegalArgumentException e) {

            logger.warn("Error de validación al actualizar cliente: {}", e.getMessage());
            throw e;

        } catch (Exception e) {

            logger.error("Error inesperado al actualizar cliente.", e);
            throw e;

        }

    }

    public void eliminarCliente(int id) {

        try {

            logger.info("Intentando eliminar cliente con ID: {}", id);

            clienteDAO.eliminar(id);

            logger.info("Cliente eliminado correctamente. ID: {}", id);

        } catch (Exception e) {

            logger.error("Error al eliminar el cliente con ID: {}", id, e);
            throw e;

        }

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
