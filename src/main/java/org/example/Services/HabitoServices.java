package org.example.Services;

import org.example.DAO.HabitoDAO;
import org.example.Model.Habito;
import org.example.Model.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HabitoServices {
    HabitoDAO habitoDAO = new HabitoDAO();


    public static boolean esSoloNumeros(Integer numero) {
        if (numero == null) {
            return false;
        }
        String regex = "\\d+"; // Solo dígitos del 0 al 9
        String numeroStr = String.valueOf(numero); // Convertir Integer a String
        return Pattern.matches(regex, numeroStr);
    }

    public boolean insertarHabito(Habito habito) {
        if (habito.getIdActividad()==null|| habito.getFrecuencia()==null ||habito.getTipo()==null||habito.getUltimaFecha()==null){
            System.out.println("Error hay campos vacíos");
            return false;
        }else{
            if (esSoloNumeros(habito.getFrecuencia())){
                habitoDAO.insertHabito(habito);
                System.out.println("Habito insertado exitosamente");
                return true;
            }else {
                System.out.println("No se puede insertar el habito");
            }
        }
        return false;
    }

    public List<Habito> listarHabitos(Usuario usuario) {
        List <Habito> habitos = habitoDAO.traerHabitoPorIDUsuario(usuario);
        if (habitos.size()==0){
            System.out.println("No se encontraron los habitos");
            return null;
        }
        return habitos;
    }
    public boolean eliminarHabito(Habito habito) {
        if (habito == null ) {
            System.out.println("Hábito inválido para eliminar.");
            return false;
        }else {
            habitoDAO.eliminarHabito(habito);
            System.out.println("Habito eliminado exitosamente");
            return true;
        }
    }
}
