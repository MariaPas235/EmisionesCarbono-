package org.example.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.DAO.ActividadDAO;
import org.example.DAO.HabitoDAO;
import org.example.Model.Actividad;
import org.example.Model.Habito;
import org.example.Model.Huella;
import org.example.Services.HabitoServices;
import org.example.Utils.Session;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;

public class MostrarHabitosController extends Controller implements Initializable{

    @FXML
    private TableView<Habito> habitoView;
    @FXML
    private TableColumn<Habito, String> ActividadNombre;
    @FXML
    private TableColumn<Habito, Integer> frecuencia ;
    @FXML
    private TableColumn<Habito, String> tipo;
        @FXML
        private TableColumn<Habito, LocalDate> fecha;

        ActividadDAO actividadDAO = new ActividadDAO();
        HabitoServices habitoServices = new HabitoServices();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            frecuencia.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
            tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            fecha.setCellValueFactory(new PropertyValueFactory<>("ultimaFecha"));

            fecha.setCellFactory(column -> new TableCell<Habito, LocalDate>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.format(formatter));
                }
            });
            ActividadNombre.setCellValueFactory(cellData -> {
                Habito habito = cellData.getValue();
                Actividad actividad = actividadDAO.traerActividadPorIDHabito(habito);
                return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "Actividad no disponible");
            });

        List<Habito> habito = habitoServices.listarHabitos(Session.getInstancia().getUsuarioIniciado());
        habitoView.getItems().setAll(habito);
        for (Habito habitos : habito){
            System.out.printf(habitos.toString());
        }

    }



    @Override
    public void onOpen(Object input) throws IOException {

    }
}
