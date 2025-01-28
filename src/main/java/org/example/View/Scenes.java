package org.example.View;

public enum Scenes {
    ROOT("/org/example/view/layout.fxml"),
    REGISTRO("/org/example/view/registro.fxml"),
    INICIOSESION("/org/example/view/IniciarSesion.fxml"),
    PAGINAPRINCIPAL("/org/example/view/PaginaPrincipal.fxml"),
    PERFILUSUARIO("/org/example/view/PerfilUsuario.fxml"),
    REGISTRARHUELLAS("/org/example/view/RegistrarHuellas.fxml");





    private String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}