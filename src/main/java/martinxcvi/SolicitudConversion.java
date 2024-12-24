package martinxcvi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.IOException;

public class SolicitudConversion {
    public String ejecutarConversion(String monedaBase, Double cantidad, String monedaFinal) {
        if(cantidad <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0.");
        }
        try {
            URI solicitudDireccion = URI.create("https://v6.exchangerate-api.com/v6/56ca92dcd0d2df7ece3fe217/pair/"+monedaBase+"/"+monedaFinal+"/"+cantidad);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(solicitudDireccion).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var dataJSON = response.body();

            JsonElement elementoJSON = JsonParser.parseString(dataJSON);
            JsonObject objetoJSON = elementoJSON.getAsJsonObject();

            if (!objetoJSON.get("result").getAsString().equals("success")) {
                throw new RuntimeException("Error en la API: " + objetoJSON.get("error-type").getAsString());
            }

            String resultado = objetoJSON.get("conversion_result").getAsString();
            System.out.println("\nEl monto base de " + cantidad + " " + monedaBase + " convertido a " + monedaFinal + " equivale a: " + resultado + " " + monedaFinal + "\n");
            return resultado;

        } catch(NumberFormatException | IOException | InterruptedException event) {
            System.out.println("Ha surgido un error: ");
            throw new RuntimeException("Error" + event.getMessage());
        }
    }
}
