package org.example.View;

public enum Scenes {
    ROOT("/org/example/view/layout.fxml"),
    REGISTRO("/org/example/view/registro.fxml"),
    INICIOSESION("/org/example/view/IniciarSesion.fxml"),
    PAGINAPRINCIPAL("/org/example/view/PaginaPrincipal.fxml"),
    PERFILUSUARIO("/org/example/view/PerfilUsuario.fxml"),
    REGISTRARHUELLAS("/org/example/view/RegistrarHuellas.fxml"),
    MISHUELLAS("/org/example/view/MostrarHuellas.fxml"),
    RESGISTRARHABITOS("/org/example/view/RegistrarHabitos.fxml"),
    MISHABITOS("/org/example/view/MostrarHabitos.fxml");


    private String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}