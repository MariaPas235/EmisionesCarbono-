package org.example.View;

import org.example.DAO.HuellaDAO;
import org.example.DAO.UserDAO;
import org.example.Model.Usuario;

import java.math.BigDecimal;
import java.util.List;

public class test {
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
       List <Usuario> usuarios= dao.traerUsuarios();

        HuellaDAO huellaDAO = new HuellaDAO();
        for (Usuario usuario : usuarios) {
            List<BigDecimal> impacto = huellaDAO.calcularImpactoPorIDUsuario(usuario);
            System.out.println(impacto + usuario.getNombre());
        }

    }
}
