package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.example.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaginaPrincipalController extends Controller implements Initializable {
    @FXML
    private Button RegistrarHuellas;
    @FXML
    private ImageView perfilUsuario;

    @FXML
    public void irARegistrarHuellas() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHUELLAS,null);
    }

    @FXML
    public void irAPerfilUsuario() throws IOException {
        App.currentController.changeScene(Scenes.PERFILUSUARIO,null);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
}
