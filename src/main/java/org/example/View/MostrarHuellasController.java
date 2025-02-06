package org.example.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.BigDecimalStringConverter;
import org.example.App;
import org.example.DAO.ActividadDAO;
import org.example.DAO.HuellaDAO;
import org.example.DAO.UserDAO;
import org.example.Model.Actividad;
import org.example.Model.Huella;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;
import org.example.Services.HuellaService;
import org.example.Utils.Session;

import javax.swing.text.Document;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
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
    private Button exportarPDF;
    @FXML
    private TextArea outputTextArea;

    @FXML
    private ImageView flechaAtras;
    @FXML
    private ComboBox<String> filtroImpacto;
    @FXML
    private TextArea mostrarImpacto;

    @FXML
    public void irAPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PAGINAPRINCIPAL,null);
    }

    HuellaDAO huellaDAO = new HuellaDAO();
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

        TableColumn<Huella, String> impactoCarbonoColumna = new TableColumn<>("Impacto de Carbono");
        impactoCarbonoColumna.setCellValueFactory(cellData -> {
            Huella huella = cellData.getValue();
            List<BigDecimal> impactoCarbono = huellaService.listarImpactoSegunUsuario(Session.getInstancia().getUsuarioIniciado());
            int index = huellaTable.getItems().indexOf(huella);
            if (index < impactoCarbono.size()) {
                return new SimpleStringProperty(impactoCarbono.get(index).toString() + "kg/CO2");
            } else {
                return new SimpleStringProperty("");
            }
        });

        huellaTable.getColumns().add(impactoCarbonoColumna);


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


        // Nueva columna con botón de información
        TableColumn<Huella, Void> infoColumna = new TableColumn<>("Recomendaciones");
        infoColumna.setCellFactory(param -> new TableCell<>() {
            private final Button btnInfo = new Button();

            {
                // Configurar icono del botón
                ImageView iconoInfo = new ImageView(new Image(getClass().getResource("/org/example/view/info.png").toExternalForm()));
                iconoInfo.setFitWidth(16);
                iconoInfo.setFitHeight(16);
                btnInfo.setGraphic(iconoInfo);
                btnInfo.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 3;");

                btnInfo.setOnAction(event -> {
                    Huella huellaSeleccionada = getTableView().getItems().get(getIndex());
                    if (huellaSeleccionada != null) {
                        HuellaDAO huellaDAO = new HuellaDAO();
                        List<Recomendacion> recomendaciones = huellaDAO.traerRecomendacionesPorHuella(huellaSeleccionada);

                        if (recomendaciones == null || recomendaciones.isEmpty()) {
                            // Mostrar alerta si no hay recomendaciones
                            Alert alertaNoInfo = new Alert(Alert.AlertType.INFORMATION);
                            alertaNoInfo.setTitle("Sin Recomendaciones");
                            alertaNoInfo.setHeaderText(null);
                            alertaNoInfo.setContentText("No hay recomendaciones disponibles para esta huella.");
                            alertaNoInfo.showAndWait();
                        } else {
                            // Crear un mensaje con las recomendaciones
                            StringBuilder mensajeRecomendaciones = new StringBuilder("Recomendaciones para la huella " + huellaSeleccionada.getIdActividad().getNombre()+ ":\n\n");
                            for (Recomendacion recomendacion : recomendaciones) {
                                mensajeRecomendaciones.append("- ").append(recomendacion.getDescripcion()).append("\n");
                            }

                            // Mostrar alerta con las recomendaciones
                            Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
                            alertaInfo.setTitle("Recomendaciones");
                            alertaInfo.setHeaderText("Información Relevante");
                            alertaInfo.setContentText(mensajeRecomendaciones.toString());
                            alertaInfo.showAndWait();
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
                    setGraphic(btnInfo);
                }
            }
        });

        huellaTable.getColumns().add(infoColumna);

        List<Huella> huellas = huellaService.listarHuellas(Session.getInstancia().getUsuarioIniciado());
        huellaTable.getItems().setAll(huellas);

        configurarTableView();

        filtroImpacto.getItems().addAll("Semanal","Mensual","Anual");

        mostrarImpacto();
    }

    private void mostrarImpacto() {
        StringBuilder sb = new StringBuilder();
        UserDAO userDAO = new UserDAO();
        List<Usuario> usuarios = userDAO.traerUsuarios();

        for (Usuario usuario : usuarios) {
            Map<String, BigDecimal> impactos = huellaDAO.calcularImpactoPorCategoriaPorIDUsuario(usuario);

            sb.append("Usuario: ").append(usuario.getNombre()).append("\n");
            for (String categoria : List.of("Transporte", "Energía", "Alimentación", "Residuos", "Agua")) {
                BigDecimal impacto = impactos.getOrDefault(categoria, BigDecimal.ZERO);
                sb.append(" - ").append(categoria).append(": ").append(impacto).append("\n");
            }
            sb.append("\n");
        }

        outputTextArea.setText(sb.toString());
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

    private void mostrarImpactosAgrupados(List<Object[]> impactosFiltrados) {
        StringBuilder resultado = new StringBuilder();

        resultado.append("====================================\n");
        resultado.append("      Resumen de Impactos Agrupados      \n");
        resultado.append("====================================\n\n");

        for (Object[] fila : impactosFiltrados) {
            String actividad = (String) fila[0];
            BigDecimal impactoTotal = (BigDecimal) fila[1];
            resultado.append("• Actividad: ")
                    .append(actividad)
                    .append("\n   ↳ Impacto Total: ")
                    .append(impactoTotal).append(" kg/CO2\n\n");
        }

        if (impactosFiltrados.isEmpty()) {
            resultado.append("No se encontraron impactos para mostrar.\n");
        }

        resultado.append("====================================");

        mostrarImpacto.setText(resultado.toString());
    }

    @FXML
    public void manejarSeleccionPeriodo(){
        String periodoSeleccionado = filtroImpacto.getValue();
        LocalDate inicio;
        LocalDate fin;

        switch (periodoSeleccionado) {
            case "Semanal":
                inicio = LocalDate.now().with(DayOfWeek.MONDAY);
                fin = inicio.plusDays(6);
                break;
            case "Mensual":
                YearMonth mesActual = YearMonth.now();
                inicio = mesActual.atDay(1);
                fin = mesActual.atEndOfMonth();
                break;
            case "Anual":
                int year = LocalDate.now().getYear();
                inicio = LocalDate.of(year, 1, 1);
                fin = LocalDate.of(year, 12, 31);
                break;
            default:
                return;
        }
        HuellaDAO huellaDAO = new HuellaDAO();
        List<Object[]> impactos = huellaDAO.calcularImpactoAgrupadoPorActividad(Session.getInstancia().getUsuarioIniciado(), inicio, fin);
        mostrarImpactosAgrupados(impactos);
    }






    @Override
    public void onOpen(Object input) throws IOException {

    }
}
