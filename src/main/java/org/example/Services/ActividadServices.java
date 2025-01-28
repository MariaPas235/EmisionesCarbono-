package org.example.Services;

import org.example.DAO.ActividadDAO;
import org.example.Model.Actividad;
import org.example.View.RegistrarHuellasController;

import java.util.ArrayList;
import java.util.List;

public class ActividadServices {
    ActividadDAO actividadDAO = new ActividadDAO();

    public List<Actividad> todasActividades(){
        List<Actividad> actividades = actividadDAO.traerTodasActividades();
        return actividades;
    }

    public Actividad ActividadCompletaPorNombre(String nombre){
        Actividad actividad = null;
        if (actividadDAO.traerActividadPorNombre(nombre) != null) {
            actividad = actividadDAO.traerActividadPorNombre(nombre);
        }else {
            System.out.println("No existe el actividad con el nombre " + nombre);
            //mensaje error
        }

        return actividad;
    }
}
