package com.novatech.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novatech.dao.EmpleadoDAO;
import com.novatech.model.Empleado;

public class EmpleadoService {
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    private static final Logger logger =
        LoggerFactory.getLogger(EmpleadoService.class);

    public void guardarEmpleado(Empleado empleado) {

        try {

            validarEmpleado(empleado);

            logger.info(
                    "Intentando guardar empleado: {} {}",
                    empleado.getNombre(),
                    empleado.getApellido());

            empleadoDAO.insertar(empleado);

            logger.info(
                    "Empleado guardado correctamente. ID: {}",
                    empleado.getIdEmpleado());

        } catch (IllegalArgumentException e) {

            logger.warn(
                    "Error de validación al guardar empleado: {}",
                    e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error inesperado al guardar empleado.", e);

            throw e;
        }
    }

    public void actualizarEmpleado(Empleado empleado) {

        try {

            validarEmpleado(empleado);

            logger.info(
                    "Intentando actualizar empleado ID {}: {} {}",
                    empleado.getIdEmpleado(),
                    empleado.getNombre(),
                    empleado.getApellido());

            empleadoDAO.actualizar(empleado);

            logger.info(
                    "Empleado actualizado correctamente. ID: {}",
                    empleado.getIdEmpleado());

        } catch (IllegalArgumentException e) {

            logger.warn(
                    "Error de validación al actualizar empleado: {}",
                    e.getMessage());

            throw e;

        } catch (Exception e) {

            logger.error("Error inesperado al actualizar empleado.", e);

            throw e;
        }
    }

    public void eliminarEmpleado(int id) {

        try {

            logger.info("Intentando eliminar empleado con ID: {}", id);

            empleadoDAO.eliminar(id);

            logger.info("Empleado eliminado correctamente. ID: {}", id);

        } catch (Exception e) {

            logger.error(
                    "Error al eliminar empleado con ID: {}",
                    id,
                    e);

            throw e;
        }
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
