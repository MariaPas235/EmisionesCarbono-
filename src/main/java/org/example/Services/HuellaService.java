package org.example.Services;

import org.example.DAO.HuellaDAO;
import org.example.Model.Habito;
import org.example.Model.Huella;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;
import org.example.View.AppController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class HuellaService {
    private final HuellaDAO huellaDAO = new HuellaDAO();

    public static boolean esSoloNumeros(BigDecimal numero) {
        if (numero == null) {
            return false;
        }
        String regex = "\\d+";
        return numero.stripTrailingZeros().toPlainString().matches(regex);
    }



    public boolean addHuella(Huella huella) {
        try {
            // Validar que todos los campos obligatorios estén completos
            if (huella == null || huella.getIdActividad() == null || huella.getFecha() == null ||
                    huella.getValor() == null || huella.getUnidad() == null) {
                AppController.showError("Faltan datos por rellenar");
                throw new IllegalArgumentException("Faltan datos por rellenar.");
            }

            // Validar que el valor sea un número válido
            if (!esSoloNumeros(huella.getValor())) {
                AppController.showError("El valor no contiene un número válido");
                throw new IllegalArgumentException("El valor no contiene un número válido.");
            }

            // Validar que la fecha no sea superior al día actual
            LocalDate fechaActual = LocalDate.now();
            if (huella.getFecha().isAfter(fechaActual)) {
                AppController.showError("La fecha no puede ser posterior al día actual.");
                throw new IllegalArgumentException("La fecha no puede ser posterior al día actual.");
            }

            // Si todo está bien, se inserta la huella
            huellaDAO.insertHuella(huella);
            AppController.showInfo("Huella agregada con éxito");
            System.out.println("Huella agregada con éxito.");
            return true;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al agregar la huella: " + e.getMessage());
            System.err.println("Error inesperado al agregar la huella: " + e.getMessage());
        }
        return false;
    }


    public List<Huella> listarHuellas(Usuario usuario) {
        try {
            if (usuario == null) {
                AppController.showError("El usuario no puede ser nulo ");
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            List<Huella> huellas = huellaDAO.traerTodasHuellasPorIDUsuario(usuario);
            if (huellas.isEmpty()) {
                AppController.showError("No se han encontrado huellas para el usuario con ID:" + usuario.getId());
                System.err.println("No se han encontrado huellas para el usuario con ID: " + usuario.getId());
                return Collections.emptyList();
            }
            return huellas;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al listar huellas: " + e.getMessage());
            System.err.println("Error inesperado al listar huellas: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public static void actualizarHuella(Huella huella) {
        try {
            if (huella == null) {
                AppController.showError("La huella no puede ser nula ");
                throw new IllegalArgumentException("La huella no puede ser nula.");
            }

            HuellaDAO huellaDAO = new HuellaDAO();
            huellaDAO.actualizarHuella(huella);
            AppController.showInfo("Huella actualizada con exito");
            System.out.println("Huella actualizada con éxito.");

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al actualizar huella: " + e.getMessage());
            System.err.println("Error inesperado al actualizar la huella: " + e.getMessage());
        }
    }

    public boolean eliminarHuella(Huella huella) {
        try {
            if (huella == null) {
                AppController.showError("La huella no se pudo eliminar ");
                throw new IllegalArgumentException("Huella inválida para eliminar.");
            }

            huellaDAO.eliminarHuella(huella);
            AppController.showInfo("Huella eliminada con exito");
            System.out.println("Huella eliminada exitosamente.");
            return true;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al eliminar huella: " + e.getMessage());
            System.err.println("Error inesperado al eliminar la huella: " + e.getMessage());
        }
        return false;
    }

    public List<BigDecimal> listarImpactoSegunUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                AppController.showError("El usuario no puede ser nula ");
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            List<BigDecimal> impacto = huellaDAO.calcularImpactoPorIDUsuario(usuario);
            if (impacto.isEmpty()) {
                AppController.showError("No se ha encontrado impacto para el usuario con ID: " + usuario.getId());
                System.err.println("No se ha encontrado impacto para el usuario con ID: " + usuario.getId());
                return Collections.emptyList();
            }
            return impacto;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al listar impactos: " + e.getMessage());
            System.err.println("Error inesperado al listar impactos: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Recomendacion> listarRecomendaciones(Huella huella) {
        try {
            if (huella == null) {
                AppController.showError("La huella no puede ser nula ");
                throw new IllegalArgumentException("La huella no puede ser nula.");
            }

            List<Recomendacion> recomendaciones = huellaDAO.traerRecomendacionesPorHuella(huella);
            if (recomendaciones.isEmpty()) {
                AppController.showError("No se han encontrado recomendaciones para la huella.");
                System.err.println("No se han encontrado recomendaciones para la huella.");
                return Collections.emptyList();
            }
            return recomendaciones;

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al listar recomendaciones: " + e.getMessage());
            System.err.println("Error inesperado al listar recomendaciones: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Object[]> calcularImpactoAgrupadoPorActividad(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            if (usuario == null || fechaInicio == null || fechaFin == null) {
                AppController.showError("Usuario y fechas no pueden ser nulos. ");
                throw new IllegalArgumentException("Usuario y fechas no pueden ser nulos.");
            }

            return huellaDAO.calcularImpactoAgrupadoPorActividad(usuario, fechaInicio, fechaFin);

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al calcular impactos: " + e.getMessage());
            System.err.println("Error inesperado al calcular impacto agrupado: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public Map<String, BigDecimal> calcularImpactoPorCategoriaPorIDUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                AppController.showError("El usuario no puede ser nula ");
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            return huellaDAO.calcularImpactoPorCategoriaPorIDUsuario(usuario);

        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println(e.getMessage());
        } catch (Exception e) {
            AppController.showError("Error inesperado al calcular impactos: " + e.getMessage());
            System.err.println("Error inesperado al calcular impacto por categoría: " + e.getMessage());
        }
        return Collections.emptyMap();
    }
}
