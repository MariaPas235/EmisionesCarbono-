package org.example.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.example.App;
import org.example.Model.Actividad;
import org.example.Model.Habito;
import org.example.Model.Recomendacion;
import org.example.Services.ActividadServices;
import org.example.Services.HabitoServices;
import org.example.Utils.Session;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MostrarHabitosController extends Controller implements Initializable{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView flechaAtras;
    @FXML
    private TilePane contenedorTarjetas;

    @FXML
    public void irAPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PAGINAPRINCIPAL,null);
    }
        @FXML
        private TableColumn<Habito, LocalDate> fecha;
        ActividadServices actividadServices = new ActividadServices();
        HabitoServices habitoServices = new HabitoServices();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Este método es invocado automáticamente cuando la escena es cargada

        // Obtener la lista de hábitos del usuario actual
        List<Habito> habitos = habitoServices.listarHabitos(Session.getInstancia().getUsuarioIniciado());

        for (Habito habito : habitos) {
            Actividad actividad = actividadServices.traerActividadPorIdHabito(habito);
            if (actividad == null) continue;

            // Crear contenedor de tarjeta
            VBox tarjeta = new VBox(8); // Espaciado vertical entre elementos
            tarjeta.setAlignment(Pos.CENTER);
            tarjeta.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ddd; -fx-border-radius: 8; -fx-padding: 10; -fx-background-radius: 8;");
            tarjeta.setMaxWidth(200); // Limitar ancho de cada tarjeta

            // Imagen de la actividad
            ImageView imagenActividad = new ImageView();
            imagenActividad.setFitWidth(80);
            imagenActividad.setFitHeight(80);
            String imagePath = obtenerRutaImagenActividad(actividad.getNombre());
            if (imagePath != null) {
                imagenActividad.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
            }

            // Información del hábito
            Label nombreActividad = new Label(actividad.getNombre());
            nombreActividad.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            Label frecuenciaLabel = new Label("Frecuencia: " + habito.getFrecuencia());
            Label tipoLabel = new Label("Tipo: " + habito.getTipo());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Label fechaLabel = new Label("Última fecha: " + (habito.getUltimaFecha() != null ? habito.getUltimaFecha().format(formatter) : "Sin fecha"));

            // Botón de eliminar
            Button btnEliminar = new Button();
            ImageView iconoPapelera = new ImageView(new Image(getClass().getResource("/org/example/view/papelera.png").toExternalForm()));
            iconoPapelera.setFitWidth(16);
            iconoPapelera.setFitHeight(16);
            btnEliminar.setGraphic(iconoPapelera);
            btnEliminar.setStyle("-fx-background-color: red; -fx-text-fill: white;");

            btnEliminar.setOnAction(event -> {
                if (habitoServices.eliminarHabito(habito)) {
                    contenedorTarjetas.getChildren().remove(tarjeta);
                    System.out.println("Hábito eliminado correctamente de la base de datos.");
                } else {
                    System.out.println("No se pudo eliminar el hábito.");
                }
            });

            // Botón de recomendación
            Button btnInfo = new Button();
            ImageView iconoInfo = new ImageView(new Image(getClass().getResource("/org/example/view/info.png").toExternalForm()));
            iconoInfo.setFitWidth(16);
            iconoInfo.setFitHeight(16);
            btnInfo.setGraphic(iconoInfo);
            btnInfo.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 3;");

            btnInfo.setOnAction(event -> {
                // Obtener la lista de recomendaciones para el hábito
                List<Recomendacion> recomendaciones = habitoServices.traerRecomendacionesPorHabito(habito);

                if (recomendaciones == null || recomendaciones.isEmpty()) {
                    // Mostrar alerta si no hay recomendaciones
                    Alert alertaNoInfo = new Alert(Alert.AlertType.INFORMATION);
                    alertaNoInfo.setTitle("Sin Recomendaciones");
                    alertaNoInfo.setHeaderText(null);
                    alertaNoInfo.setContentText("No hay recomendaciones disponibles para este hábito.");
                    alertaNoInfo.showAndWait();
                } else {
                    // Crear un mensaje con las recomendaciones
                    StringBuilder mensajeRecomendaciones = new StringBuilder("Recomendaciones para el hábito " + habito.getIdActividad().getNombre() + ":\n\n");
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
            });

            // Agregar elementos a la tarjeta
            tarjeta.getChildren().addAll(imagenActividad, nombreActividad, frecuenciaLabel, tipoLabel, fechaLabel, btnEliminar, btnInfo);

            // Agregar tarjeta al contenedor de tarjetas
            contenedorTarjetas.getChildren().add(tarjeta);
        }
    }

    private String obtenerRutaImagenActividad(String nombreActividad) {
        switch (nombreActividad) {
            case "Conducir coche": return "/org/example/view/actividad1Coche.png";
            case "Usar transporte público": return "/org/example/view/actividad2Autobus.png";
            case "Viajar en avión": return "/org/example/view/actividad3Avion.png";
            case "Consumo eléctrico": return "/org/example/view/actividad4Electrico.png";
            case "Consumo de gas": return "/org/example/view/actividad5Gas.png";
            case "Comer carne de res": return "/org/example/view/actividad6Res.png";
            case "Comer alimentos vegetarianos": return "/org/example/view/actividad7Vegetariano.png";
            case "Generar residuos domésticos": return "/org/example/view/actividad8Basura.png";
            case "Consumo de agua potable": return "/org/example/view/actividad9Agua.png";
            default: return null;
        }
    }







    @Override
    public void onOpen(Object input) throws IOException {

    }


}
