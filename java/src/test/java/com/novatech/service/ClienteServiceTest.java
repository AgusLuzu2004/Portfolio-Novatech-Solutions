package com.novatech.service;

import com.novatech.dao.ClienteDAO;
import com.novatech.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    private ClienteDAO clienteDAO;
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteDAO = Mockito.mock(ClienteDAO.class);
        clienteService = new ClienteService(clienteDAO);
    }

    private Cliente crearClienteValido() {

        Cliente cliente = new Cliente();

        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setEdad(30);
        cliente.setSexo("Masculino");
        cliente.setProvincia("Buenos Aires");
        cliente.setCiudad("La Plata");
        cliente.setFechaAlta(LocalDate.now());

        return cliente;
    }

    @Test
    void deberiaGuardarClienteValido() {

        Cliente cliente = crearClienteValido();

        assertDoesNotThrow(() -> clienteService.guardarCliente(cliente));

        verify(clienteDAO, times(1)).insertar(cliente);
    }

    @Test
    void deberiaLanzarExcepcionSiNombreEstaVacio() {

        Cliente cliente = crearClienteValido();

        cliente.setNombre("");

        assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.guardarCliente(cliente)
        );

        verify(clienteDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiApellidoEstaVacio() {

        Cliente cliente = crearClienteValido();

        cliente.setApellido("");

        assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.guardarCliente(cliente)
        );

        verify(clienteDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiEdadEsMenorA18() {

        Cliente cliente = crearClienteValido();

        cliente.setEdad(15);

        assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.guardarCliente(cliente)
        );

        verify(clienteDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiEdadEsMayorA75() {

        Cliente cliente = crearClienteValido();

        cliente.setEdad(90);

        assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.guardarCliente(cliente)
        );

        verify(clienteDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiProvinciaEstaVacia() {

        Cliente cliente = crearClienteValido();

        cliente.setProvincia("");

        assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.guardarCliente(cliente)
        );

        verify(clienteDAO, never()).insertar(any());
    }

    @Test
    void deberiaLanzarExcepcionSiCiudadEstaVacia() {

        Cliente cliente = crearClienteValido();

        cliente.setCiudad("");

        assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.guardarCliente(cliente)
        );

        verify(clienteDAO, never()).insertar(any());
    }

}
