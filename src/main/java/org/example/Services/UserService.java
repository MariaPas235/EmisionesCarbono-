package org.example.Services;

import org.example.DAO.UserDAO;
import org.example.Model.Usuario;

public class UserService {

    UserDAO userDAO = new UserDAO();

    public boolean register (Usuario user){
        Usuario usuario = userDAO.findByEmail(user);
        if (usuario != null){
            return false;

        }else{
            userDAO.insertUser(user);
            return true;
        }
    }
}
