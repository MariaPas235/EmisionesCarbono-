package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.Model.Actividad;
import org.example.Model.Categoria;
import org.example.Services.ActividadServices;
import org.example.Services.CategoríaServices;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrarHuellasController extends Controller implements Initializable {
    @FXML
    private  ChoiceBox<String> actividades;
    @FXML
    private TextField valor;
    @FXML
    private TextField unidad;
    @FXML
    private TextField fecha;
    @FXML
    private Button registrar;


    @FXML
    public void setearActividades(){
        ActividadServices actividadesService = new ActividadServices();
        List<Actividad> actividads =  actividadesService.todasActividades();
        List<String> nombreActividad = new ArrayList<String>();
        for(Actividad actividad : actividads){
            nombreActividad.add(actividad.getNombre());
        }
        actividades.getItems().addAll(nombreActividad);
    }


    public String recogerNombreActividad(){
        String nombreActividad = actividades.getValue();
        return nombreActividad;
    }
    @FXML
    public Actividad ActividadCompleta(){
        ActividadServices actividadesService = new ActividadServices();
        Actividad actividadCompleta = actividadesService.ActividadCompletaPorNombre(recogerNombreActividad());
        System.out.println(actividadCompleta);
        return actividadCompleta;

    }

    public int cogerIDCategoría (){
        int id = ActividadCompleta().getIdCategoria().getId();
        return id;
    }

    @FXML
    public void CategoríaCompleta(){
        CategoríaServices categoríaServices = new CategoríaServices();
        Categoria categoriaCompleta = categoríaServices.obtenerCategoria(cogerIDCategoría());
        System.out.println(categoriaCompleta);
        unidad.setText(categoriaCompleta.getUnidad());
    }

    @FXML
    public void ImprimirFechaHoy(){
        fecha.setText(LocalDate.now().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setearActividades();
        ImprimirFechaHoy();
    }



    @Override
    public void onOpen(Object input) throws IOException {

    }
}
