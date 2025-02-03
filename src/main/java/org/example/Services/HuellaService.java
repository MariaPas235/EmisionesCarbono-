package org.example.Services;

import org.example.DAO.HuellaDAO;
import org.example.Model.Habito;
import org.example.Model.Huella;
import org.example.Model.Usuario;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

public class HuellaService {
    HuellaDAO huellaDAO = new HuellaDAO();

    public static boolean esSoloNumeros(BigDecimal numero) {
        if (numero == null) {
            return false;
        }
        String regex = "\\d+"; // Solo dígitos del 0 al 9
        String numeroStr = numero.stripTrailingZeros().toPlainString(); // Elimina ceros innecesarios
        return Pattern.matches(regex, numeroStr);
    }

    public boolean addHuella(Huella huella) {
        if (huella.getIdActividad() == null || huella.getFecha() == null || huella.getValor() == null || huella.getUnidad() == null) {
            System.out.println("Faltan datos por rellenar");
            return false;

        }else {
            if (esSoloNumeros(huella.getValor())){
                huellaDAO.insertHuella(huella);
                System.out.println("Huella agregada con exito");
                return true;
            }else {
                System.out.println("el valor no tiene un número");
            }
        }
        return false;

    }


    public List<Huella> listarHuellas(Usuario usuario) {
           List<Huella> huellas = huellaDAO.traerTodasHuellasPorIDUsuario(usuario);
           if (huellas.isEmpty()) {
               System.out.println("No se han encontrado las huellas");
               return null;
           }
           return huellas;
    }

    public static void actualizarHuella(Huella huella) {
        HuellaDAO huellaDAO = new HuellaDAO();
         huellaDAO.actualizarHuella(huella);
    }

    public boolean eliminarHuella(Huella huella) {
        if (huella == null ) {
            System.out.println("Huella inválida para eliminar.");
            return false;
        }else {
            huellaDAO.eliminarHuella(huella);
            System.out.println("Huella eliminada exitosamente");
            return true;
        }
    }


}
