package org.example.Services;

import org.example.DAO.CategoriaDAO;
import org.example.Model.Categoria;
import org.example.View.AppController;

public class CategoríaServices {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public Categoria obtenerCategoria(int id) {
        try {
            if (id <= 0) {
                AppController.showError("El ID de la categoría debe ser mayor que cero.");
                throw new IllegalArgumentException("El ID de la categoría debe ser mayor que cero.");
            }

            Categoria categoria = categoriaDAO.BuscarCategoria(id);
            if (categoria == null) {
                AppController.showError("No se ha encontrado la categoría con el ID: " + id);
                System.err.println("No se ha encontrado la categoría con el ID: " + id);
            }
            return categoria;
        } catch (IllegalArgumentException e) {
            AppController.showError(e.getMessage());
            System.err.println("Argumento inválido: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            AppController.showError("Error al obtener la categoría: " + e.getMessage());
            throw new RuntimeException("Error inesperado al obtener la categoría", e);
        }
    }
}
