package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.Model.Usuario;
import org.example.Services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IniciarSesionController extends Controller implements Initializable {
    @FXML
    private TextField emailUsuario;
    @FXML
    private TextField contraseñaUsuario;
    @FXML
    Button botonIniciarSesion;

    public Usuario recogerDatos(){
        Usuario usuario = new Usuario();
        usuario.setEmail(emailUsuario.getText());
        usuario.setContraseña(contraseñaUsuario.getText());
        return usuario;
    }

    @FXML
    public void IniciarSesion() throws IOException {
        Usuario usuario = recogerDatos();
        UserService userService = new UserService();
        if (userService.login(usuario)){
            App.currentController.changeScene(Scenes.PAGINAPRINCIPAL,null);
        }else {
            //error los datos no son validos
        System.out.println("los datos no son validos" + usuario);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
}
