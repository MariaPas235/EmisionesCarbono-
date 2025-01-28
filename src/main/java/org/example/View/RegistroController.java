package org.example.View;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.example.App;
import org.example.Model.Usuario;
import org.example.Services.UserService;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class RegistroController extends Controller implements Initializable {

    @FXML
    private ImageView pajaro; // Enlaza el ImageView del pájaro
    @FXML
    private TextField nombre;
    @FXML
    private TextField email;
    @FXML
    private TextField contraseña;
    @FXML
    private Button registrar;
    @FXML
    private Button IrAInicioSesion;

    public Usuario recogerDatos (){
        String nombreUser = nombre.getText();
        String emailUser = email.getText();
        String contraseñaUser = contraseña.getText();
        Usuario usuario = new Usuario(nombreUser,emailUser,contraseñaUser, Instant.now());
        return usuario;
    }

    @FXML
    public void regitrarUsuario () throws IOException {
        Usuario usuario = recogerDatos();
        UserService userService = new UserService();
        if (userService.register(usuario)){
            App.currentController.changeScene(Scenes.INICIOSESION,null);
            System.out.println("usuario registrado" + usuario);
        }else {
            System.out.println("El usuario ya existe");
            //imprimir mensaje de usuario ya registrado
        }



    }

    @FXML
    public void irAInicioSesion () throws IOException {
        App.currentController.changeScene(Scenes.INICIOSESION,null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 200)); // Posición inicial
        path.getElements().add(new CubicCurveTo(300, 50, 500, 350, 800, 200)); // Trayectoria curva

        // Configurar la animación
        PathTransition birdAnimation = new PathTransition(Duration.seconds(5), path, pajaro);
        birdAnimation.setCycleCount(PathTransition.INDEFINITE); // Animación infinita
        birdAnimation.setAutoReverse(true); // Moverse de ida y vuelta
        birdAnimation.play(); // Iniciar animación
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
}
