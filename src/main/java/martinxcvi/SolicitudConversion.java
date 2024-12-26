package martinxcvi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudConversion {
    GeneradorHistorial generadorHistorial = new GeneradorHistorial();

    public String ejecutarConversion(String monedaBase, Double cantidad, String monedaFinal) {
        if(cantidad <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0.");
        }
        try {
            /* Solicitud a la API junto con los parámetros necesarios */
            // >Reemplazar "AQUI_TU_API_KEY" con tu propia key de ExchangeRate
            URI solicitudDireccion = URI.create("https://v6.exchangerate-api.com/v6/AQUI_TU_API_KEY/pair/"+monedaBase+"/"+monedaFinal+"/"+cantidad);

            HttpClient client = HttpClient.newHttpClient();
            // Almacenando la solicitud
            HttpRequest request = HttpRequest.newBuilder().uri(solicitudDireccion).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            /* Procesamiento de los datos */
            var dataJSON = response.body();
            JsonElement elementoJSON = JsonParser.parseString(dataJSON);
            JsonObject objetoJSON = elementoJSON.getAsJsonObject();

            if (!objetoJSON.get("result").getAsString().equals("success")) {
                throw new RuntimeException("Error en la API: " + objetoJSON.get("error-type").getAsString());
            }

            String resultado = objetoJSON.get("conversion_result").getAsString();
            String respuesta = "\nEl monto base de " + cantidad + " " + monedaBase + " convertido a " + monedaFinal + " equivale a: " + resultado + " " + monedaFinal + "\n";
            System.out.println(respuesta);
            List<String> listaRespuestas = new ArrayList<>();
            // Agregando la respuesta a la lista de respuestas para el generador del historial
            listaRespuestas.add(respuesta);
            generadorHistorial.guardarConversion(listaRespuestas);
            return respuesta;
        } catch(NumberFormatException | IOException | InterruptedException event) {
            System.out.println("Ha surgido un error: ");
            throw new RuntimeException("Error" + event.getMessage());
        }
    }
}
