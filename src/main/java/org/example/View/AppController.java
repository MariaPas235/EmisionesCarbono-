package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.Model.Recomendacion;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;
    @FXML
    static Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    static Alert alertError = new Alert(Alert.AlertType.ERROR);

    public static void showRecomendacion(List<Recomendacion> recomendaciones) {
        alertInfo.setTitle("Recomendacion");
        alertInfo.getDialogPane().setPrefWidth(700);
        alertInfo.getDialogPane().setPrefHeight(160);
        alertInfo.setHeaderText(null);
        alertInfo.setContentText(recomendaciones.toString());
        alertInfo.showAndWait();
    }

    public static void showError(String error) {
        alertError.setTitle("Error");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(error);
        alertError.showAndWait();
    }

    public static void showInfo(String info) {
        alertInfo.setTitle("Recomendacion");
        alertInfo.getDialogPane().setPrefWidth(700);
        alertInfo.getDialogPane().setPrefHeight(160);
        alertInfo.setHeaderText(null);
        alertInfo.setContentText(info);
        alertInfo.showAndWait();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        changeScene(Scenes.REGISTRO, null);
    }

    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        System.out.println(url);
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene = p;
        view.controller = c;
        return view;
    }
    public void changeScene(Scenes scene, Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }
    public void openModalv(Scenes scenes, String tilte, Controller parent, Object data) throws Exception {
        View view = loadFXML(scenes);
        Stage stage = new Stage();
        stage.setTitle(tilte);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(data);
        stage.showAndWait();
    }

}
