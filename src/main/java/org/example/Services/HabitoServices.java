package org.example.Services;

import org.example.App;
import org.example.DAO.HabitoDAO;
import org.example.Model.Habito;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;
import org.example.View.AppController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HabitoServices {
    private final HabitoDAO habitoDAO = new HabitoDAO();

    public static boolean esSoloNumeros(Integer numero) {
        if (numero == null) {
            return false;
        }
        String regex = "\\d+";
        return Pattern.matches(regex, String.valueOf(numero));
    }

    public boolean insertarHabito(Habito habito) {
        try {
            // Validar que todos los campos obligatorios estén completos
            if (habito == null || habito.getIdActividad() == null || habito.getFrecuencia() == null ||
                    habito.getTipo() == null || habito.getUltimaFecha() == null) {
                AppController.showError("Error: hay campos vacíos en el hábito.");
                throw new IllegalArgumentException("Error: hay campos vacíos en el hábito.");
            }

            // Validar que la frecuencia sea solo números
            if (!esSoloNumeros(habito.getFrecuencia())) {
                AppController.showError("Error: La frecuencia debe contener solo números.");
                throw new IllegalArgumentException("Error: la frecuencia debe contener solo números.");
            }

            // Validar que la ultimaFecha no sea posterior a la fecha actual
            LocalDate fechaActual = LocalDate.now();
            if (habito.getUltimaFecha().isAfter(fechaActual)) {
                AppController.showError(" La última fecha no puede ser posterior al día actual.");
                AppController.showError("Error: La última fecha no puede ser posterior al día actual.");
                throw new IllegalArgumentException("Error: La última fecha no puede ser posterior al día actual.");
            }

            // Si todo está bien, se inserta el hábito
            habitoDAO.insertHabito(habito);
            AppController.showInfo("Hábito insertado exitosamente.");
            System.out.println("Hábito insertado exitosamente.");
            return true;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            AppController.showError("Error inesperado al insertar el hábito: " + e.getMessage());
            System.err.println("Error inesperado al insertar el hábito: " + e.getMessage());
            return false;
        }
    }


    public List<Habito> listarHabitos(Usuario usuario) {
        try {
            if (usuario == null) {
                AppController.showError("Error: el usuario no puede ser nulo.");
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            List<Habito> habitos = habitoDAO.traerHabitoPorIDUsuario(usuario);
            if (habitos.isEmpty()) {
                AppController.showError("No se encontraron hábitos para el usuario con ID: " + usuario.getId());
                System.err.println("No se encontraron hábitos para el usuario con ID: " + usuario.getId());
                return new ArrayList<>();
            }

            return habitos;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            AppController.showError("Error inesperado al listar habitos: " +e.getMessage());
            System.err.println("Error inesperado al listar hábitos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean eliminarHabito(Habito habito) {
        try {
            if (habito == null) {
                AppController.showError("Error:hábito inválido para eliminar.");
                throw new IllegalArgumentException("Error: hábito inválido para eliminar.");
            }

            habitoDAO.eliminarHabito(habito);
            AppController.showInfo("Habito eliminado exitosamente.");
            System.out.println("Hábito eliminado exitosamente.");
            return true;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            AppController.showError("Error inesperado al eliminar: " +e.getMessage());
            System.err.println("Error inesperado al eliminar el hábito: " + e.getMessage());
            return false;
        }
    }

    public List<Recomendacion> traerRecomendacionesPorHabito(Habito habito) {
        try {
            if (habito == null) {
                AppController.showError("El hábito no puede ser nulo.");
                throw new IllegalArgumentException("El hábito no puede ser nulo.");
            }

            List<Recomendacion> recomendaciones = habitoDAO.traerRecomendacionesPorHabito(habito);
            if (recomendaciones.isEmpty()) {
                AppController.showError("No se encontraron recomendaciones para el hábito.");
                System.err.println("No se encontraron recomendaciones para el hábito.");
            }
            return recomendaciones;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            AppController.showError("Error inesperado al obtener recomendaciones: " +e.getMessage());
            System.err.println("Error inesperado al obtener recomendaciones: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean actualizarFrecuenciaHabito(Habito habito) {
        try {
            // Validar que la frecuencia del hábito sea un número positivo
            if (habito.getFrecuencia() <= 0) {
                AppController.showError("La frecuencia del hábito debe ser un número positivo.");
                System.out.println("Error: La frecuencia del hábito debe ser un número positivo.");
                return false;  // Retornar false si la frecuencia no es válida
            }

            // Guardar la actualización de la frecuencia en la base de datos
            habitoDAO.actualizarHabito(habito);
            AppController.showInfo("Hábito actualizado exitosamente.");
            System.out.println("Frecuencia del hábito actualizada correctamente a: " + habito.getFrecuencia());
            return true;  // Retornar true si la actualización fue exitosa
        } catch (Exception e) {
            // Capturar cualquier excepción inesperada
            AppController.showError("Error inesperado al actualizar frecuencia: " + e.getMessage());
            System.out.println("Error al actualizar la frecuencia del hábito: " + e.getMessage());
            e.printStackTrace();
            return false;  // Retornar false si hubo un error inesperado
        }
    }



}
