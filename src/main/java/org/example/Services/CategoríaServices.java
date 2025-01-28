package org.example.Services;

import org.example.DAO.CategoriaDAO;
import org.example.Model.Categoria;

public class CategoríaServices {
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    public Categoria obtenerCategoria(int id) {
        Categoria categoria = null;

        if (categoriaDAO.BuscarCategoria(id)!=null) {
            categoria = categoriaDAO.BuscarCategoria(id);
        }else {
            System.out.println("no se ha encontrado la categoriaa por ese ID");
        }
        return categoria;
    }
}
