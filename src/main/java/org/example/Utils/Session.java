package org.example.Utils;

import org.example.Model.Usuario;

public class Session {
    // Instancia única de la sesión
    private static Session _instance;

    // Usuario actualmente logueado
    private Usuario userLoged;

    // Constructor privado para evitar instancias externas
    private Session() {}

    // Método para obtener la instancia única
    public static Session getInstancia() {
        if (_instance == null) {
            _instance = new Session();
        }
        return _instance;
    }

    // Método para iniciar sesión
    public void logIn(Usuario user) {
        this.userLoged = user;
    }

    // Método para obtener el usuario logueado
    public Usuario getUsuarioIniciado() {
        return userLoged;
    }

    // Método para actualizar el usuario logueado
    public void setUsuarioIniciado(Usuario usuarioIniciado) {
        this.userLoged = usuarioIniciado;
    }

    // Método para cerrar sesión
    public void logOut() {
        this.userLoged = null;
    }
}
