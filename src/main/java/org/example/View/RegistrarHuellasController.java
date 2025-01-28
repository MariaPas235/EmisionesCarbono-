package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.Model.Actividad;
import org.example.Services.ActividadServices;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrarHuellasController extends Controller implements Initializable {
    @FXML
    private ChoiceBox<String> actividades;
    @FXML
    private TextField valor;
    @FXML
    private TextField unidad;
    @FXML
    private TextField fecha;


    @FXML
    public void setearActividades(){
        ActividadServices actividadesService = new ActividadServices();
        List<Actividad> actividads =  actividadesService.todasActividades();
        List<String> nombreActividad = new ArrayList<String>();
        for(Actividad actividad : actividads){
            nombreActividad.add(actividad.getNombre());
        }
        actividades.getItems().addAll( nombreActividad);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setearActividades();
    }



    @Override
    public void onOpen(Object input) throws IOException {

    }
}
