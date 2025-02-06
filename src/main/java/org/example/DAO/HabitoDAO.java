package org.example.DAO;

import org.example.Connection.Connection;
import org.example.Model.Habito;
import org.example.Model.Huella;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;



public class HabitoDAO {

    private final static String hql2 = "SELECT r FROM Habito h JOIN Actividad a ON h.idActividad.id = a.id JOIN Recomendacion r ON a.idCategoria.id = r.idCategoria.id WHERE h.id = :id";


    public void insertHabito(Habito habito) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        session.beginTransaction();
        session.save(habito);
        session.getTransaction().commit();
        session.close();
    }

    public List<Habito> traerHabitoPorIDUsuario(Usuario user) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        String hql = "from Habito where idUsuario.id = :idUsuario";
        Query query = session.createQuery(hql);
        query.setParameter("idUsuario", user.getId());
        List<Habito> habitos = query.getResultList();
        session.close();
        return habitos;
    }

    public void eliminarHabito(Habito habito) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        session.beginTransaction();
        session.delete(habito);
        session.getTransaction().commit();
        session.close();
    }

    public List<Recomendacion> traerRecomendacionesPorHabito(Habito habito ){
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        List<Recomendacion> recomendaciones = null;
        Query <Recomendacion> query = session.createQuery(hql2);
        query.setParameter("id", habito.getId());
        recomendaciones = query.getResultList();
        session.close();
        return recomendaciones;
    }
}
