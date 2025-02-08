package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.Utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaginaPrincipalController extends Controller implements Initializable {
    @FXML
    private Button RegistrarHuellas;
    @FXML
    private ImageView perfilUsuario;
    @FXML
    private Button misHuellas;
    @FXML
    private Button RegistrarHabito;
    @FXML
    private Button misHabitos;
    @FXML
    private ImageView cerrar;

    @FXML
    public void CerrarSesión() throws IOException {
        Session.getInstancia().logOut();
        App.currentController.changeScene(Scenes.REGISTRO,null);
    }

    @FXML
    public void irARegistrarHabitos() throws IOException {
        App.currentController.changeScene(Scenes.RESGISTRARHABITOS,null);
    }

    @FXML
    public void irAMisHabitos() throws IOException {
        App.currentController.changeScene(Scenes.MISHABITOS,null);
    }

    @FXML
    public void irARegistrarHuellas() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRARHUELLAS,null);
    }

    @FXML
    public void irAPerfilUsuario() throws IOException {
        App.currentController.changeScene(Scenes.PERFILUSUARIO,null);
    }

    @FXML
    public void irAMisHuellas() throws IOException {
        App.currentController.changeScene(Scenes.MISHUELLAS,null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
}
