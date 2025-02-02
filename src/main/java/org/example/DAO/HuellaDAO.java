package org.example.DAO;

import org.example.Connection.Connection;
import org.example.Model.Actividad;
import org.example.Model.Huella;
import org.example.Model.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class HuellaDAO {
    public void insertHuella(Huella huella) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        session.beginTransaction();
        session.save(huella);
        session.getTransaction().commit();
        session.close();
    }

    public List<Huella> traerTodasHuellasPorIDUsuario(Usuario usuario) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        List<Huella> huellas = null;
        String hql = "FROM Huella WHERE idUsuario.id = :idUsuario";
        Query<Huella> query = session.createQuery(hql, Huella.class);
        query.setParameter("idUsuario", usuario.getId());
        huellas= query.list();
        return huellas;
    }

    public void actualizarHuella(Huella huella) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        session.beginTransaction();
        session.merge(huella);
        session.getTransaction().commit();
        session.close();
    }
}
