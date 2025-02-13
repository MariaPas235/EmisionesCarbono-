package org.example.Services;

import org.example.DAO.ActividadDAO;
import org.example.Model.Actividad;
import org.example.Model.Habito;
import org.example.Model.Huella;
import org.example.View.AppController;

import java.util.List;

public class ActividadServices {
    private final ActividadDAO actividadDAO = new ActividadDAO();

    public List<Actividad> todasActividades() {
        try {
            List<Actividad> actividades = actividadDAO.traerTodasActividades();
            if (actividades == null || actividades.isEmpty()) {
                AppController.showError("No se encontraron actividades disponibles.");
                System.err.println("No se encontraron actividades disponibles.");
            }
            return actividades;
        } catch (Exception e) {
            AppController.showError("Error al traer las actividades: "+e.getMessage());
            System.err.println("Error al traer todas las actividades: " + e.getMessage());
            throw new RuntimeException("Error inesperado al obtener actividades", e);
        }
    }

    public Actividad actividadCompletaPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                AppController.showError("El nombre del actividad es obligatorio.");
                throw new IllegalArgumentException("El nombre de la actividad no puede ser nulo o vacío.");
            }
            Actividad actividad = actividadDAO.traerActividadPorNombre(nombre);
            if (actividad == null) {
                AppController.showError("El nombre de la actividad no existe");
                System.err.println("No existe ninguna actividad con el nombre: " + nombre);
            }
            return actividad;
        } catch (Exception e) {
            AppController.showError("Error al traer las actividades: "+e.getMessage());
            System.err.println("Error al buscar la actividad por nombre: " + e.getMessage());
            throw new RuntimeException("Error al obtener actividad por nombre", e);
        }
    }

    public Actividad traerActividadPorIdHuella(Huella huella) {
        try {
            if (huella == null) {
                AppController.showError("El huella es obligatorio.");
                throw new IllegalArgumentException("La huella no puede ser nula.");
            }
            Actividad actividad = actividadDAO.traerActividadPorID(huella);
            if (actividad == null) {
                AppController.showError("No se encontró una actividad asociada a la huella con ID: " + huella.getId());
                System.err.println("No se encontró una actividad asociada a la huella con ID: " + huella.getId());
            }
            return actividad;
        } catch (Exception e) {
            AppController.showError("Error al traer las actividades: "+e.getMessage());
            System.err.println("Error al obtener actividad por ID de huella: " + e.getMessage());
            throw new RuntimeException("Error al obtener actividad por huella", e);
        }
    }

    public Actividad traerActividadPorIdHabito(Habito habito) {
        try {
            if (habito == null) {
                AppController.showError("El habito es obligatorio.");
                throw new IllegalArgumentException("El hábito no puede ser nulo.");
            }
            Actividad actividad = actividadDAO.traerActividadPorIDHabito(habito);
            if (actividad == null) {
                AppController.showError("No se encontró una actividad asociada al hábito con ID: " + habito.getId());
                System.err.println("No se encontró una actividad asociada al hábito con ID: " + habito.getId());
            }
            return actividad;
        } catch (Exception e) {
            AppController.showError("Error al traer las actividades: "+e.getMessage());
            System.err.println("Error al obtener actividad por ID de hábito: " + e.getMessage());
            throw new RuntimeException("Error al obtener actividad por hábito", e);
        }
    }
}
