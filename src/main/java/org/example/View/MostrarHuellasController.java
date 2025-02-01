package org.example.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.DAO.ActividadDAO;
import org.example.Model.Actividad;
import org.example.Model.Huella;
import org.example.Model.Usuario;
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
        List<Huella> huellas = huellaService.listarHuellas(Session.getInstancia().getUsuarioIniciado());
        huellaTable.getItems().setAll(huellas);
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
}
