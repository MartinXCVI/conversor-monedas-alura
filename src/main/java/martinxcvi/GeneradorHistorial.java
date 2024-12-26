package martinxcvi;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneradorHistorial {
    // Enrutamiento del archivo del historial de solicitudes
    private static final String RUTA_HISTORIAL = "solicitudes-historial.txt";
    // Formateador para la fecha y hora
    private static final DateTimeFormatter FORMATEADOR_FECHA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void guardarConversion(List<String> lista) {
        try (FileWriter fileWriter = new FileWriter(RUTA_HISTORIAL, true)) { // Modo "append" activado
            for (String resultado : lista) {
                // Obtener fecha y hora actual
                String fechaHoraActual = LocalDateTime.now().format(FORMATEADOR_FECHA_HORA);
                // Escribir en el archivo con fecha y hora
                fileWriter.write("[" + fechaHoraActual + "] " + resultado + System.lineSeparator());
            }
            System.out.println("Historial de solicitudes guardado en '" + RUTA_HISTORIAL + "'");
        } catch (IOException error) {
            System.err.println("Error al guardar el historial de solicitudes: " + error.getMessage());
            System.err.println("Lista de resultados: " + lista);
        }
    }
}