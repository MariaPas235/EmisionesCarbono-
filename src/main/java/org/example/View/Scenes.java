package org.example.View;

public enum Scenes {
    ROOT("/org/example/view/layout.fxml"),
    REGISTRO("/org/example/view/registro.fxml");




    private String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}