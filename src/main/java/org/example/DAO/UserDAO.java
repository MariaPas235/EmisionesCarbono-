package org.example.DAO;

import org.example.Connection.Connection;
import org.example.Model.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class UserDAO {

    private final static String FINDBYEMAIL = "FROM Usuario WHERE email = :email";

    public void insertUser(Usuario user) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }
    //query unique result si no encuentra devuelve null
    public Usuario findByEmail(Usuario user) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        Query query = session.createQuery(FINDBYEMAIL);
        query.setParameter("email", user.getEmail());
        Usuario usuario = (Usuario) query.uniqueResult();
        session.close();
        return usuario;
    }
    public void actualizarUser(Usuario user) {
        Connection conn = Connection.getInstance();
        Session session = conn.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }
}
