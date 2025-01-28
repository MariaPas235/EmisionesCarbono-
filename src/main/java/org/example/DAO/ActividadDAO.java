package org.example.DAO;

import org.example.Connection.Connection;
import org.example.Model.Actividad;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ActividadDAO {
    public List<Actividad> traerTodasActividades() {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        List<Actividad> actividades = null;
        String hql = "FROM Actividad";
        Query <Actividad> query = session.createQuery(hql, Actividad.class);
        actividades = query.list();
        return actividades;
    }

    public Actividad traerActividadPorNombre(String nombre) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        Actividad actividad = null;
        String hql = "FROM Actividad WHERE nombre = :nombre";
        Query <Actividad> query = session.createQuery(hql, Actividad.class);
        query.setParameter("nombre", nombre);
        actividad = query.uniqueResult();
        return actividad;
    }
}
