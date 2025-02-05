package org.example.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.BigDecimalStringConverter;
import org.example.App;
import org.example.DAO.ActividadDAO;
import org.example.DAO.HuellaDAO;
import org.example.Model.Actividad;
import org.example.Model.Huella;
import org.example.Model.Recomendacion;
import org.example.Services.HuellaService;
import org.example.Utils.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MostrarHuellasController extends Controller  implements Initializable {
    @FXML
    private TableView<Huella> huellaTable;
    @FXML
    private TableColumn<Huella, String> ActividadNombre;
    @FXML
    private TableColumn<Huella, BigDecimal> valor ;
    @FXML
    private TableColumn<Huella, String> unidad;
    @FXML
    private TableColumn<Huella, LocalDate> fecha;

    @FXML
    private ImageView flechaAtras;

    @FXML
    public void irAPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PAGINAPRINCIPAL,null);
    }


    HuellaService huellaService = new HuellaService();
    ActividadDAO actividadDAO = new ActividadDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        unidad.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        ActividadNombre.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            Actividad actividad = actividadDAO.traerActividadPorID(huella);
            return new SimpleStringProperty(actividad != null ? actividad.getNombre() : "Actividad no disponible");
        });

        TableColumn<Huella, Void> eliminarColumna = new TableColumn<>("Eliminar");
        eliminarColumna.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminar = new Button();

            {
                btnEliminar.setStyle("-fx-background-color: #FF4C4C; -fx-padding: 5;");
                ImageView imageView = new ImageView(new Image(getClass().getResource("/org/example/view/papelera.png").toExternalForm()));
                imageView.setFitHeight(16);
                imageView.setFitWidth(16);
                btnEliminar.setGraphic(imageView);

                btnEliminar.setOnAction(event -> {
                    Huella huellaSeleccionada = getTableView().getItems().get(getIndex());

                    if (huellaSeleccionada != null) {
                        boolean eliminado = huellaService.eliminarHuella(huellaSeleccionada);
                        if (eliminado) {
                            getTableView().getItems().remove(huellaSeleccionada);
                            getTableView().refresh();
                            System.out.println("Huella eliminada con éxito.");
                        } else {
                            System.out.println("Error al eliminar la huella.");
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                }
            }
        });

        huellaTable.getColumns().add(eliminarColumna);

        TableColumn<Huella, String> impactoCarbonoColumna = new TableColumn<>("Impacto de Carbono");
        impactoCarbonoColumna.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            List<BigDecimal> impactoCarbono = huellaService.listarImpactoSegunUsuario(Session.getInstancia().getUsuarioIniciado());
            int index = huellaTable.getItems().indexOf(huella);
            if (index < impactoCarbono.size()) {
                return new SimpleStringProperty(impactoCarbono.get(index).toString());
            } else {
                return new SimpleStringProperty("");
            }
        });

        huellaTable.getColumns().add(impactoCarbonoColumna);

        // Nueva columna con botón de información
        TableColumn<Huella, Void> infoColumna = new TableColumn<>("Info");
        infoColumna.setCellFactory(param -> new TableCell<>() {
            private final Button btnInfo = new Button("i");

            {
                btnInfo.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 3;");
                btnInfo.setOnAction(event -> {
                    Huella huellaSeleccionada = getTableView().getItems().get(getIndex());
                    System.out.println(huellaSeleccionada);
                    HuellaDAO huellaDAO = new HuellaDAO();
                    List<Recomendacion> recomendacions = huellaDAO.traerRecomendacionesPorHuella(huellaSeleccionada);
                    System.out.println(recomendacions);
                    if (huellaSeleccionada != null) {
                        AppController.showRecomendacion(recomendacions);

                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnInfo);
                }
            }
        });

        huellaTable.getColumns().add(infoColumna);

        List<Huella> huellas = huellaService.listarHuellas(Session.getInstancia().getUsuarioIniciado());
        huellaTable.getItems().setAll(huellas);

        configurarTableView();
    }


    private void configurarTableView() {
        valor.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        valor.setOnEditCommit(event -> {
            Huella huella = event.getRowValue();
            try {
                BigDecimal nuevoValor = new BigDecimal(event.getNewValue().toString());
                huella.setValor(nuevoValor);
                HuellaService.actualizarHuella(huella);
                System.out.println("Valor actualizado a: " + nuevoValor);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el valor a BigDecimal: " + event.getNewValue());
            }
        });
    }

    public Huella recogerDatosTableView(){
        Huella huella = huellaTable.getSelectionModel().getSelectedItem();
        ActividadDAO actividadDAO = new ActividadDAO();
        Actividad actividad = actividadDAO.traerActividadPorID(huella);
        huella.setIdActividad(actividad);
        System.out.println(actividad);
        System.out.println(huella);
        return huella;

    }


    @Override
    public void onOpen(Object input) throws IOException {

    }
}
