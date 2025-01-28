package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.App;
import org.example.Model.Usuario;
import org.example.Services.UserService;
import org.example.Utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PerfilUsuarioController extends Controller implements Initializable {
    @FXML
    private TextField nombreUsuario;
    @FXML
    private TextField emailUsuario;
    @FXML
    private TextField contraseñaUsuario;
    @FXML
    private ImageView flechaAtras;
    @FXML
    private Button modificar ;

    @FXML
    public void atras() throws IOException {
        App.currentController.changeScene(Scenes.PAGINAPRINCIPAL,null);
    }


    public Usuario recogerDatos() {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombreUsuario.getText());
        usuario.setEmail(emailUsuario.getText());
        usuario.setContraseña(contraseñaUsuario.getText());
        usuario.setId(Session.getInstancia().getUsuarioIniciado().getId());
        usuario.setFechaRegistro(Session.getInstancia().getUsuarioIniciado().getFechaRegistro());
    return usuario;
    }

    @FXML
    public void actualizar() throws IOException {
        Usuario usuario = recogerDatos();
        UserService userService = new UserService();
        if (userService.actualizar(usuario)) {
            // Actualiza el usuario en la instancia de sesión
            Session.getInstancia().setUsuarioIniciado(usuario);

            // Cambia a la siguiente escena
            App.currentController.changeScene(Scenes.PAGINAPRINCIPAL, null);

            // Puedes agregar una alerta indicando que la modificación fue exitosa
            System.out.println("Usuario actualizado correctamente.");
        } else {
            // Muestra una alerta o mensaje indicando que la actualización falló
            System.out.println("El usuario no se ha podido modificar.");
        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        nombreUsuario.setText( Session.getInstancia().getUsuarioIniciado().getNombre());
        emailUsuario.setText( Session.getInstancia().getUsuarioIniciado().getEmail());
        contraseñaUsuario.setText(Session.getInstancia().getUsuarioIniciado().getContraseña());
    }
}
