package org.example.Services;

import org.example.DAO.HuellaDAO;
import org.example.Model.Habito;
import org.example.Model.Huella;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;

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
            if (huella == null || huella.getIdActividad() == null || huella.getFecha() == null ||
                    huella.getValor() == null || huella.getUnidad() == null) {
                throw new IllegalArgumentException("Faltan datos por rellenar.");
            }

            if (!esSoloNumeros(huella.getValor())) {
                throw new IllegalArgumentException("El valor no contiene un número válido.");
            }

            huellaDAO.insertHuella(huella);
            System.out.println("Huella agregada con éxito.");
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al agregar la huella: " + e.getMessage());
        }
        return false;
    }

    public List<Huella> listarHuellas(Usuario usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            List<Huella> huellas = huellaDAO.traerTodasHuellasPorIDUsuario(usuario);
            if (huellas.isEmpty()) {
                System.err.println("No se han encontrado huellas para el usuario con ID: " + usuario.getId());
                return Collections.emptyList();
            }
            return huellas;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al listar huellas: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public static void actualizarHuella(Huella huella) {
        try {
            if (huella == null) {
                throw new IllegalArgumentException("La huella no puede ser nula.");
            }

            HuellaDAO huellaDAO = new HuellaDAO();
            huellaDAO.actualizarHuella(huella);
            System.out.println("Huella actualizada con éxito.");

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al actualizar la huella: " + e.getMessage());
        }
    }

    public boolean eliminarHuella(Huella huella) {
        try {
            if (huella == null) {
                throw new IllegalArgumentException("Huella inválida para eliminar.");
            }

            huellaDAO.eliminarHuella(huella);
            System.out.println("Huella eliminada exitosamente.");
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar la huella: " + e.getMessage());
        }
        return false;
    }

    public List<BigDecimal> listarImpactoSegunUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            List<BigDecimal> impacto = huellaDAO.calcularImpactoPorIDUsuario(usuario);
            if (impacto.isEmpty()) {
                System.err.println("No se ha encontrado impacto para el usuario con ID: " + usuario.getId());
                return Collections.emptyList();
            }
            return impacto;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al listar impactos: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Recomendacion> listarRecomendaciones(Huella huella) {
        try {
            if (huella == null) {
                throw new IllegalArgumentException("La huella no puede ser nula.");
            }

            List<Recomendacion> recomendaciones = huellaDAO.traerRecomendacionesPorHuella(huella);
            if (recomendaciones.isEmpty()) {
                System.err.println("No se han encontrado recomendaciones para la huella.");
                return Collections.emptyList();
            }
            return recomendaciones;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al listar recomendaciones: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Object[]> calcularImpactoAgrupadoPorActividad(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            if (usuario == null || fechaInicio == null || fechaFin == null) {
                throw new IllegalArgumentException("Usuario y fechas no pueden ser nulos.");
            }

            return huellaDAO.calcularImpactoAgrupadoPorActividad(usuario, fechaInicio, fechaFin);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al calcular impacto agrupado: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public Map<String, BigDecimal> calcularImpactoPorCategoriaPorIDUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser nulo.");
            }

            return huellaDAO.calcularImpactoPorCategoriaPorIDUsuario(usuario);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al calcular impacto por categoría: " + e.getMessage());
        }
        return Collections.emptyMap();
    }
}
