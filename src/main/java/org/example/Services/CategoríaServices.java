package org.example.Services;

import org.example.DAO.CategoriaDAO;
import org.example.Model.Categoria;

public class CategoríaServices {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public Categoria obtenerCategoria(int id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("El ID de la categoría debe ser mayor que cero.");
            }

            Categoria categoria = categoriaDAO.BuscarCategoria(id);
            if (categoria == null) {
                System.err.println("No se ha encontrado la categoría con el ID: " + id);
            }
            return categoria;
        } catch (IllegalArgumentException e) {
            System.err.println("Argumento inválido: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error al obtener la categoría: " + e.getMessage());
            throw new RuntimeException("Error inesperado al obtener la categoría", e);
        }
    }
}
