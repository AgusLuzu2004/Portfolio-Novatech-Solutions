package com.novatech.dao;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import com.novatech.model.Cliente;

class ClienteDAOTest {

    private ClienteDAO clienteDAO;

    @BeforeEach
    void setUp() {

        clienteDAO = new ClienteDAO();

    }

    @Test
    void deberiaInsertarCliente() {

        Cliente cliente = new Cliente();

        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setEdad(30);
        cliente.setProvincia("Buenos Aires");
        cliente.setCiudad("La Plata");

        clienteDAO.insertar(cliente);

        Cliente resultado =
                clienteDAO.buscarPorId(cliente.getIdCliente());

        assertNotNull(resultado);

        assertEquals(
                "Juan",
                resultado.getNombre()
        );

    }

    @Test
    void deberiaBuscarClientePorId() {

        Cliente cliente =
                clienteDAO.buscarPorId(1);

        assertNotNull(cliente);

    }

    @Test
    void deberiaBuscarPorNombre() {

        List<Cliente> clientes =
                clienteDAO.buscarPorNombre("Juan");

        assertFalse(clientes.isEmpty());

    }

    @Test
    void deberiaActualizarCliente() {

        Cliente cliente =
                clienteDAO.buscarPorId(1);

        cliente.setCiudad("Quilmes");

        clienteDAO.actualizar(cliente);

        Cliente actualizado =
                clienteDAO.buscarPorId(1);

        assertEquals(
                "Quilmes",
                actualizado.getCiudad()
        );

    }

    @Test
    void deberiaEliminarCliente() {

        Cliente cliente = new Cliente();

        cliente.setNombre("Eliminar");

        clienteDAO.insertar(cliente);

        int id = cliente.getIdCliente();

        clienteDAO.eliminar(id);

        Cliente eliminado =
                clienteDAO.buscarPorId(id);

        assertNull(eliminado);

    }

    @Test
    void deberiaListarClientes() {

        List<Cliente> clientes =
                clienteDAO.listarClientes();

        assertNotNull(clientes);

        assertFalse(clientes.isEmpty());

    }

}
