package org.example.Services;

import org.example.DAO.ActividadDAO;
import org.example.Model.Actividad;

import java.util.ArrayList;
import java.util.List;

public class ActividadServices {
    ActividadDAO actividadDAO = new ActividadDAO();

    public List<Actividad> todasActividades(){
        List<Actividad> actividades = actividadDAO.traerTodasActividades();
        return actividades;
    }
}
