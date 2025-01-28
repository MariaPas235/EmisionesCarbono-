package org.example.DAO;

import org.example.Connection.Connection;
import org.example.Model.Actividad;
import org.example.Model.Categoria;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CategoriaDAO {
    public Categoria BuscarCategoria(int id) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        Categoria categoria = null;
        String hql = "FROM Categoria WHERE id = :id";
        Query<Categoria> query = session.createQuery(hql, Categoria.class);
        query.setParameter("id", id);
        categoria = query.uniqueResult();
        return categoria;
    }
}
