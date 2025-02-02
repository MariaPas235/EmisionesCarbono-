package org.example.View;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.DAO.HabitoDAO;
import org.example.Model.Actividad;
import org.example.Model.Habito;
import org.example.Model.HabitoId;
import org.example.Services.ActividadServices;
import org.example.Services.HabitoServices;
import org.example.Utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrarHabitosController extends Controller implements Initializable {

    @FXML
    private ChoiceBox<String> actividades;
    @FXML
    private TextField frecuencia;
    @FXML
    private ChoiceBox<String> tipo;
    @FXML
    private DatePicker fecha;
    @FXML
    private Button registrar;

    @FXML

    public void setearActividades() {
        ActividadServices actividadesService = new ActividadServices();
        List<Actividad> actividads = actividadesService.todasActividades();
        List<String> nombreActividad = new ArrayList<String>();
        for (Actividad actividad : actividads) {
            nombreActividad.add(actividad.getNombre());
        }
        actividades.getItems().addAll(nombreActividad);
    }

    public void setearTipos() {
        tipo.setItems(FXCollections.observableArrayList("Diario", "Semanal", "Mensual", "Anual"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setearActividades();
        setearTipos();
    }

    public String recogerNombreActividad() {
        String nombreActividad = actividades.getValue();
        return nombreActividad;
    }

    @FXML
    public Actividad ActividadCompleta() {
        ActividadServices actividadesService = new ActividadServices();
        Actividad actividadCompleta = actividadesService.ActividadCompletaPorNombre(recogerNombreActividad());
        System.out.println(actividadCompleta);
        return actividadCompleta;

    }

    public Habito recogerDatos() {
        Habito habitorecogido = new Habito();
        habitorecogido.setIdUsuario(Session.getInstancia().getUsuarioIniciado());
        habitorecogido.setIdActividad(ActividadCompleta());
        habitorecogido.setTipo(tipo.getValue());
        habitorecogido.setUltimaFecha(fecha.getValue());
        try {
            if (HabitoServices.esSoloNumeros(Integer.valueOf(frecuencia.getText()))) {
                habitorecogido.setFrecuencia(Integer.valueOf(frecuencia.getText()));
            }else{
                System.out.println("Eroor de conversion");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        HabitoId habitoId = new HabitoId();
        habitoId.setIdActividad(ActividadCompleta().getId());
        habitoId.setIdUsuario(Session.getInstancia().getUsuarioIniciado().getId());
        habitorecogido.setId(habitoId);




        return habitorecogido;

    }

    @FXML
    public void registrarHabito() {
        HabitoServices habitoServices = new HabitoServices();
        HabitoDAO habitoDAO = new HabitoDAO();
        if (habitoServices.insertarHabito(recogerDatos())){
            System.out.println("habito registrado correctamente");
        }else {
            System.out.println("habito no registrado");
        }
    }


    @Override
    public void onOpen(Object input) throws IOException {

    }
}
