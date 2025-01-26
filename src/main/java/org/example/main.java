package org.example;

import org.example.Model.Recomendacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml") // Archivo de configuración
                .buildSessionFactory();

        // Crear una sesión
        Session session = factory.openSession();

        try {
            // Iniciar la transacción
            session.beginTransaction();

            // Obtener todas las recomendaciones con sus categorías asociadas
            List<Recomendacion> recomendaciones = session.createQuery(
                    "FROM Recomendacion r JOIN FETCH r.idCategoria", Recomendacion.class).getResultList();

            // Mostrar resultados
            for (Recomendacion r : recomendaciones) {
                System.out.println("Recomendación: " + r.getDescripcion());
                System.out.println("Categoría: " + r.getIdCategoria().getNombre());
                System.out.println("Impacto estimado: " + r.getImpactoEstimado() + " kg CO2");
                System.out.println("-----------");
            }

            // Finalizar la transacción
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar la sesión
            session.close();
            factory.close();
        }
    }



}