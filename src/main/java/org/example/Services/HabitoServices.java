package org.example.Services;

import org.example.DAO.HabitoDAO;
import org.example.Model.Habito;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;

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
            if (habito == null || habito.getIdActividad() == null || habito.getFrecuencia() == null ||
                    habito.getTipo() == null || habito.getUltimaFecha() == null) {
                throw new IllegalArgumentException("Error: hay campos vacíos en el hábito.");
            }

            if (!esSoloNumeros(habito.getFrecuencia())) {
                throw new IllegalArgumentException("Error: la frecuencia debe contener solo números.");
            }

            habitoDAO.insertHabito(habito);
            System.out.println("Hábito insertado exitosamente.");
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error inesperado al insertar el hábito: " + e.getMessage());
            return false;
        }
    }

    public List<Habito> listarHabitos(Usuario usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            List<Habito> habitos = habitoDAO.traerHabitoPorIDUsuario(usuario);
            if (habitos.isEmpty()) {
                System.err.println("No se encontraron hábitos para el usuario con ID: " + usuario.getId());
                return new ArrayList<>();
            }

            return habitos;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error inesperado al listar hábitos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean eliminarHabito(Habito habito) {
        try {
            if (habito == null) {
                throw new IllegalArgumentException("Error: hábito inválido para eliminar.");
            }

            habitoDAO.eliminarHabito(habito);
            System.out.println("Hábito eliminado exitosamente.");
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar el hábito: " + e.getMessage());
            return false;
        }
    }

    public List<Recomendacion> traerRecomendacionesPorHabito(Habito habito) {
        try {
            if (habito == null) {
                throw new IllegalArgumentException("El hábito no puede ser nulo.");
            }

            List<Recomendacion> recomendaciones = habitoDAO.traerRecomendacionesPorHabito(habito);
            if (recomendaciones.isEmpty()) {
                System.err.println("No se encontraron recomendaciones para el hábito.");
            }
            return recomendaciones;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error inesperado al obtener recomendaciones: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
