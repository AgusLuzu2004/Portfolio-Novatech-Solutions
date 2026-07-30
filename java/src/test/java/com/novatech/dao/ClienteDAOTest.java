package com.novatech.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.novatech.model.Cliente;

class ClienteDAOTest {

    private ClienteDAO clienteDAO;
    private Cliente clienteDePrueba;

    @BeforeEach
    void setUp() {

        clienteDAO = new ClienteDAO();

        clienteDePrueba = new Cliente();
        clienteDePrueba.setNombre("TestDAO_" + System.nanoTime());
        clienteDePrueba.setApellido("Pérez");
        clienteDePrueba.setEdad(30);
        clienteDePrueba.setSexo("Otro");
        clienteDePrueba.setProvincia("Buenos Aires");
        clienteDePrueba.setCiudad("La Plata");
        clienteDePrueba.setFechaAlta(LocalDate.now());

    }

    @AfterEach
    void tearDown() {

        for (Cliente c : clienteDAO.buscarPorNombre(clienteDePrueba.getNombre())) {
            clienteDAO.eliminar(c.getIdCliente());
        }

    }

    @Test
    void deberiaInsertarCliente() {

        clienteDAO.insertar(clienteDePrueba);

        List<Cliente> resultado = clienteDAO.buscarPorNombre(clienteDePrueba.getNombre());

        assertFalse(resultado.isEmpty());
        assertEquals(clienteDePrueba.getNombre(), resultado.get(0).getNombre());
        assertEquals("Pérez", resultado.get(0).getApellido());

    }

    @Test
    void deberiaBuscarPorNombre() {

        clienteDAO.insertar(clienteDePrueba);

        List<Cliente> clientes = clienteDAO.buscarPorNombre(clienteDePrueba.getNombre());

        assertFalse(clientes.isEmpty());

    }

    @Test
    void deberiaActualizarCliente() {

        clienteDAO.insertar(clienteDePrueba);

        Cliente insertado = clienteDAO.buscarPorNombre(clienteDePrueba.getNombre()).get(0);

        insertado.setCiudad("Quilmes");

        clienteDAO.actualizar(insertado);

        Cliente actualizado = clienteDAO.buscarPorId(insertado.getIdCliente());

        assertEquals("Quilmes", actualizado.getCiudad());

    }

    @Test
    void deberiaEliminarCliente() {

        clienteDAO.insertar(clienteDePrueba);

        int id = clienteDAO.buscarPorNombre(clienteDePrueba.getNombre()).get(0).getIdCliente();

        clienteDAO.eliminar(id);

        Cliente eliminado = clienteDAO.buscarPorId(id);

        assertNull(eliminado);

    }

    @Test
    void deberiaListarClientes() {

        clienteDAO.insertar(clienteDePrueba);

        List<Cliente> clientes = clienteDAO.listarClientes();

        assertNotNull(clientes);

        assertFalse(clientes.isEmpty());

    }

}
