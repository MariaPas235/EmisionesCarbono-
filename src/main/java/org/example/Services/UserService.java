package org.example.Services;

import org.example.DAO.UserDAO;
import org.example.Model.Usuario;
import org.example.Utils.Session;

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

    public boolean login (Usuario user){
        Usuario usuario = userDAO.findByEmail(user);
        if (usuario != null && user.getContraseña().equals(usuario.getContraseña())){
            Session.getInstancia().logIn(usuario);
            return true;
        }else {
            return false;
        }
    }

    public boolean actualizar(Usuario user) {
        // Buscar el usuario actual en la base de datos por su ID
        Usuario usuarioActual = userDAO.findByEmail(Session.getInstancia().getUsuarioIniciado());
        if (usuarioActual == null) {
            System.out.println("El usuario no fue encontrado en la base de datos.");
            return false; // El usuario no existe en la base de datos
        }

        // Verificar si el email ha cambiado
        if (!user.getEmail().equals(usuarioActual.getEmail())) {
            // Comprobar si el nuevo email ya existe en otro usuario
            Usuario usuarioConNuevoEmail = userDAO.findByEmail(user);
            if (usuarioConNuevoEmail != null) {
                System.out.println("El nuevo email ya está en uso por otro usuario.");
                return false; // El email ya está registrado en otro usuario
            }
        }

        // Verificar si hay cambios reales en los datos del usuario
        if (user.getNombre().equals(usuarioActual.getNombre()) &&
                user.getEmail().equals(usuarioActual.getEmail()) &&
                user.getContraseña().equals(usuarioActual.getContraseña())) {
            System.out.println("No hay cambios en los datos del usuario.");
            return false; // No hay cambios
        }

        // Actualizar el usuario en la base de datos
        userDAO.actualizarUser(user);
        System.out.println("Usuario actualizado con éxito.");
        return true;
    }

}
