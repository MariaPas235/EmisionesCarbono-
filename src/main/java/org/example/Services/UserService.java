package org.example.Services;

import org.example.DAO.UserDAO;
import org.example.Model.Usuario;
import org.example.Utils.Session;
import java.util.Collections;
import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean register(Usuario user) {
        try {
            if (user == null || user.getEmail() == null || user.getContraseña() == null) {
                throw new IllegalArgumentException("El usuario, el email o la contraseña no pueden ser nulos.");
            }

            Usuario usuarioExistente = userDAO.findByEmail(user);
            if (usuarioExistente != null) {
                System.err.println("El email ya está en uso.");
                return false;
            }

            userDAO.insertUser(user);
            System.out.println("Usuario registrado con éxito.");
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al registrar usuario: " + e.getMessage());
        }
        return false;
    }

    public boolean login(Usuario user) {
        try {
            if (user == null || user.getEmail() == null || user.getContraseña() == null) {
                throw new IllegalArgumentException("El usuario, el email o la contraseña no pueden ser nulos.");
            }

            Usuario usuario = userDAO.findByEmail(user);
            if (usuario != null && user.getContraseña().equals(usuario.getContraseña())) {
                Session.getInstancia().logIn(usuario);
                System.out.println("Inicio de sesión exitoso.");
                return true;
            }

            System.err.println("Credenciales incorrectas.");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al iniciar sesión: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizar(Usuario user) {
        try {
            if (user == null || user.getEmail() == null || user.getNombre() == null || user.getContraseña() == null) {
                throw new IllegalArgumentException("Los datos del usuario no pueden ser nulos.");
            }

            Usuario usuarioActual = userDAO.findByEmail(Session.getInstancia().getUsuarioIniciado());
            if (usuarioActual == null) {
                System.err.println("El usuario no fue encontrado en la base de datos.");
                return false;
            }

            // Verificar si el email ha cambiado
            if (!user.getEmail().equals(usuarioActual.getEmail())) {
                Usuario usuarioConNuevoEmail = userDAO.findByEmail(user);
                if (usuarioConNuevoEmail != null) {
                    System.err.println("El nuevo email ya está en uso por otro usuario.");
                    return false;
                }
            }

            // Verificar si hay cambios reales
            if (user.getNombre().equals(usuarioActual.getNombre()) &&
                    user.getEmail().equals(usuarioActual.getEmail()) &&
                    user.getContraseña().equals(usuarioActual.getContraseña())) {
                System.err.println("No hay cambios en los datos del usuario.");
                return false;
            }

            userDAO.actualizarUser(user);
            System.out.println("Usuario actualizado con éxito.");
            return true;

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al actualizar usuario: " + e.getMessage());
        }
        return false;
    }

    public List<Usuario> traerUsuarios() {
        try {
            List<Usuario> usuarios = userDAO.traerUsuarios();
            if (usuarios.isEmpty()) {
                System.err.println("No se encontraron usuarios.");
                return Collections.emptyList();
            }
            return usuarios;

        } catch (Exception e) {
            System.err.println("Error inesperado al traer usuarios: " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
