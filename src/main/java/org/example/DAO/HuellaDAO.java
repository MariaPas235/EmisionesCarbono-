package org.example.DAO;

import org.example.Connection.Connection;
import org.example.Model.Actividad;
import org.example.Model.Huella;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;


public class HuellaDAO {

    private final static String hql ="SELECT h.valor * c.factorEmision FROM Huella h JOIN Actividad a ON h.idActividad.id = a.id JOIN Categoria c ON a.idCategoria.id = c.id WHERE h.idUsuario.id = :idUsuario";
    private final static String hql2 = "SELECT r FROM Huella h JOIN Actividad a ON h.idActividad.id = a.id JOIN Recomendacion r ON a.idCategoria.id = r.idCategoria.id WHERE h.id = :id";

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
        session.close();
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

    public void eliminarHuella(Huella huella) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        session.beginTransaction();
        session.delete(huella);
        session.getTransaction().commit();
        session.close();
    }

    public List <BigDecimal> calcularImpactoPorIDUsuario(Usuario usuario) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        List<BigDecimal> impactos = null;
        Query <BigDecimal> query = session.createQuery(hql);
        query.setParameter("idUsuario", usuario.getId());
        impactos = query.getResultList();
        session.close();
        return impactos;
    }

    public List<Recomendacion> traerRecomendacionesPorHuella(Huella huella ){
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        List<Recomendacion> recomendaciones = null;
        Query <Recomendacion> query = session.createQuery(hql2);
        query.setParameter("id", huella.getId());
        recomendaciones = query.getResultList();
        session.close();
        return recomendaciones;
    }
}
