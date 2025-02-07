package org.example.Utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.DAO.HabitoDAO;
import org.example.DAO.HuellaDAO;
import org.example.Model.Habito;
import org.example.Model.Huella;
import org.example.Model.Recomendacion;
import org.example.Model.Usuario;
import org.example.Services.HabitoServices;
import org.example.Services.HuellaService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class InformeUtils {
    private HabitoDAO habitoDAO = new HabitoDAO();
    private HuellaDAO huellaDAO = new HuellaDAO();

    public void generarInforme(Usuario usuario, Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Informe");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            String extension = getFileExtension(file);
            if ("pdf".equalsIgnoreCase(extension)) {
                generarInformePDF(usuario, file);
            } else if ("csv".equalsIgnoreCase(extension)) {
                generarInformeCSV(usuario, file);
            } else {
                throw new IOException("Formato de archivo no soportado");
            }
        }
    }

    public void generarInformePDF(Usuario usuario, File file) throws IOException {
        List<Huella> huellas = huellaDAO.traerTodasHuellasPorIDUsuario(usuario);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Informe del Usuario"));
        document.add(new Paragraph("===================\n"));
        document.add(new Paragraph("Nombre de Usuario: " + usuario.getNombre()));
        document.add(new Paragraph("Correo Electrónico: " + usuario.getEmail() + "\n"));

        document.add(new Paragraph("Huellas del Usuario:\n"));

        for (Huella huella : huellas) {
            List<Recomendacion> recomendaciones = huellaDAO.traerRecomendacionesPorHuella(huella);

            document.add(new Paragraph("Actividad: " + huella.getIdActividad().getNombre()));
            document.add(new Paragraph("Valor: " + huella.getValor() + " " + huella.getUnidad()));
            document.add(new Paragraph("Fecha: " + huella.getFecha().toString()));

            document.add(new Paragraph("Recomendaciones:"));
            if (recomendaciones.isEmpty()) {
                document.add(new Paragraph("- No hay recomendaciones disponibles."));
            } else {
                for (Recomendacion recomendacion : recomendaciones) {
                    document.add(new Paragraph("- " + recomendacion.getDescripcion()));
                }
            }
            document.add(new Paragraph("\n"));
        }

        document.close();
    }

    private void generarInformeCSV(Usuario usuario, File file) throws IOException {
        List<Huella> huellas = huellaDAO.traerTodasHuellasPorIDUsuario(usuario);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Nombre de Usuario," + usuario.getNombre() + "\n");
            writer.write("Correo Electrónico," + usuario.getEmail() + "\n\n");

            writer.write("Huellas y Recomendaciones:\n");
            for (Huella huella : huellas) {
                List<Recomendacion> recomendaciones = huellaDAO.traerRecomendacionesPorHuella(huella);
                BigDecimal impacto = huella.getValor().multiply(huella.getIdActividad().getIdCategoria().getFactorEmision());
                writer.write(huella.getIdActividad().getNombre() + "," + huella.getValor() + "," + huella.getUnidad() + "," + huella.getFecha());
                if (!recomendaciones.isEmpty()) {
                    for (Recomendacion recomendacion : recomendaciones) {
                        writer.write("," + recomendacion.getDescripcion());
                    }
                }
                writer.write("\n");
            }
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
