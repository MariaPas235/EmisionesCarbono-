package org.example.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

            TableColumn<Habito, Void> colEliminar = new TableColumn<>("Eliminar");
            colEliminar.setCellFactory(param -> new TableCell<>() {
                private final Button btnEliminar = new Button();
                private final ImageView iconoPapelera;

                {
                    iconoPapelera = new ImageView(new Image(getClass().getResource("/org/example/view/papelera.png").toExternalForm()));
                    iconoPapelera.setFitWidth(16);
                    iconoPapelera.setFitHeight(16);
                    btnEliminar.setGraphic(iconoPapelera);
                    btnEliminar.setStyle("-fx-background-color: red; -fx-text-fill: white;");

                    btnEliminar.setOnAction(event -> {
                        Habito habitoSeleccionado = getTableView().getItems().get(getIndex());
                        if (habitoSeleccionado != null) {
                            // Elimina el hábito de la base de datos
                            boolean eliminado = habitoServices.eliminarHabito(habitoSeleccionado);
                            if (eliminado) {
                                getTableView().getItems().remove(habitoSeleccionado);
                                System.out.println("Hábito eliminado correctamente de la base de datos.");
                            } else {
                                System.out.println("No se pudo eliminar el hábito.");
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : btnEliminar);
                }
            });

            habitoView.getColumns().add(colEliminar);

            List<Habito> habitos = habitoServices.listarHabitos(Session.getInstancia().getUsuarioIniciado());
            habitoView.getItems().setAll(habitos);

            for (Habito habito : habitos) {
                System.out.printf(habito.toString());
            }

        }

    @Override
    public void onOpen(Object input) throws IOException {

    }
}
