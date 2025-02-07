package org.example.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.Model.Actividad;
import org.example.Model.Categoria;
import org.example.Model.Huella;
import org.example.Services.ActividadServices;
import org.example.Services.CategoríaServices;
import org.example.Services.HuellaService;
import org.example.Utils.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
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
    private DatePicker fecha;
    @FXML
    private Button registrar;
    @FXML
    private ImageView fechaAtras;

    @FXML
    public void irAPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PAGINAPRINCIPAL,null);
    }


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
        Actividad actividadCompleta = actividadesService.actividadCompletaPorNombre(recogerNombreActividad());
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

    public Huella recogerTodosDatos() {
        Huella huella = new Huella();
        HuellaService huellaService = new HuellaService();
        huella.setIdUsuario(Session.getInstancia().getUsuarioIniciado());
        huella.setIdActividad(ActividadCompleta());
        huella.setUnidad(unidad.getText());
        huella.setFecha(fecha.getValue());

        try {
            // Intentamos verificar si el valor es un número y asignarlo a la huella
            if (HuellaService.esSoloNumeros(new BigDecimal(valor.getText()))) {
                huella.setValor(new BigDecimal(valor.getText()));
                System.out.println(huella);
                return huella;
            } else {
                // Si no es un número válido, manejar el caso
                System.out.println("El valor ingresado no es un número válido.");
                return null;  // O devolver algo que indique error, como un objeto nulo
            }
        } catch (NumberFormatException e) {
            // Capturamos la excepción si ocurre un error al convertir el valor
            System.out.println("Error al convertir el valor: " + e.getMessage());
            return null;  // O devolver algo que indique error, como un objeto nulo
        }
    }


    @FXML
    public void añadirHuella() throws IOException {
        HuellaService huellaService = new HuellaService();
        Huella huellaAGuardar = recogerTodosDatos();

        // Verificamos si recogerTodosDatos devolvió null, lo que indica un error
        if (huellaAGuardar == null) {
            // Mostrar mensaje de error o notificación al usuario
            System.out.println("No se pudo guardar la huella, los datos son inválidos.");
            return; // Salimos del método si los datos son inválidos
        }

        // Intentamos guardar la huella
        if (huellaService.addHuella(huellaAGuardar)) {
            // Si la huella se guarda correctamente, cambiamos a la página principal
            App.currentController.changeScene(Scenes.PAGINAPRINCIPAL, null);
        } else {
            // Si ocurre un error al guardar la huella, mostramos un mensaje de error
            System.out.println("Error al guardar la huella.");
        }
    }

    @FXML
    public void limpiarUnidad(){
        unidad.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setearActividades();
    }





    @Override
    public void onOpen(Object input) throws IOException {

    }
}
