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

        try {
            URI solicitudDireccion = URI.create("https://v6.exchangerate-api.com/v6/YOUR_API_KEY_HERE/pair/"+monedaBase+"/"+monedaFinal+"/"+cantidad);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(solicitudDireccion).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var dataJSON = response.body();

            JsonElement elementoJSON = JsonParser.parseString(dataJSON);
            JsonObject objetoJSON = elementoJSON.getAsJsonObject();

            String resultado = objetoJSON.get("conversion_result").getAsString();
            System.out.println("\nEl monto base de " + cantidad + " " + monedaBase + " convertido a " + monedaFinal + " equivale a: " + resultado + " " + monedaFinal + "\n");
            return resultado;

        } catch(NumberFormatException | IOException | InterruptedException event) {
            System.out.println("Ha surgido un error: ");
            throw new RuntimeException("Error" + event.getMessage());
        }
    }
}
