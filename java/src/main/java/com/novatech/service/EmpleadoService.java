package com.novatech.service;

import java.util.List;

import com.novatech.dao.EmpleadoDAO;
import com.novatech.model.Empleado;

public class EmpleadoService {
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public void guardarEmpleado(Empleado empleado) {

        validarEmpleado(empleado);

        empleadoDAO.insertar(empleado);

    }

    public void actualizarEmpleado(Empleado empleado) {

        validarEmpleado(empleado);

        empleadoDAO.actualizar(empleado);

    }

    public void eliminarEmpleado(int id) {

        empleadoDAO.eliminar(id);

    }

    private void validarEmpleado(Empleado empleado) {

        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (empleado.getApellido() == null || empleado.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }

        if (empleado.getIdSucursal() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar una sucursal válida.");
        }

        if (empleado.getFechaIngreso() == null) {
            throw new IllegalArgumentException("La fecha de ingreso es obligatoria.");
        }

        if (empleado.getFechaIngreso().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser futura.");
        }
    }

    public List<Empleado> obtenerTodos() {
        return empleadoDAO.listarEmpleados();
    }

    public List<Empleado> buscar(String texto) {
        return empleadoDAO.buscarPorNombre(texto);
    }

    public List<Empleado> buscarPorSucursal(String nombreSucursal) {
        return empleadoDAO.buscarPorSucursalNombre(nombreSucursal);
    }

    public List<String> obtenerSucursales() {
        return empleadoDAO.obtenerNombresSucursales();
    }

    public List<Empleado> listarEmpleados() {
        return empleadoDAO.listarEmpleados();
    }
}
