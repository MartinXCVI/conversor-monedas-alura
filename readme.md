# Conversor de Monedas - Oracle Next Education - G7, 2024

## 📄 Introducción

Aplicación de consola desarrollada en Java que permite realizar conversiones de moneda pre-seleccionadas utilizando la ExchangeRate-API. Los resultados de las conversiones se guardan en un archivo de texto como historial de referencia.

Proyecto realizado para el curso 'Java Orientado a Objetos' del grupo "7" perteneciente a la formación Oracle Next Education de la alianza conjunta entre Oracle y Alura Latam.

---

### 📋 Requisitos

---

- Consumir una API de tipos de cambio. En este caso, ExchangeRate-API.
- Procesar los datos en formato JSON mediante la biblioteca Gson.
- Construcción de las solicitudes y respuestas mediante HttpClient, HttpRequest y HttpResponse.
- Interfaz para una interactividad cómoda del usuario.
---

## 🗂️ Estructura

El proyecto consta de las siguientes clases principales:

- Main
- GeneradorHistorial
- SolicitudConversion
- InterfazUsuario

### 1. Main

```java
package martinxcvi;

public class Main {
    public static void main(String[] args) {
        InterfazUsuario interfaz = new InterfazUsuario();
        interfaz.mostrarMenu();
    }
}
```

#### Descripción
Inicia la ejecución del programa. Invoca la clase `InterfazUsuario` para mostrar el menú interactivo al usuario. Depende de la clase `InterfazUsuario`.

---

### 2. GeneradorHistorial

```java
package martinxcvi;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneradorHistorial {
    private static final String RUTA_HISTORIAL = "solicitudes-historial.txt";
    private static final DateTimeFormatter FORMATEADOR_FECHA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void guardarConversion(List<String> lista) {
        try (FileWriter fileWriter = new FileWriter(RUTA_HISTORIAL, true)) {
            for (String resultado : lista) {
                String fechaHoraActual = LocalDateTime.now().format(FORMATEADOR_FECHA_HORA);
                fileWriter.write("[" + fechaHoraActual + "] " + resultado + System.lineSeparator());
            }
            System.out.println("Historial de solicitudes guardado en '" + RUTA_HISTORIAL + "'");
        } catch (IOException error) {
            System.err.println("Error al guardar el historial de solicitudes: " + error.getMessage());
            System.err.println("Lista de resultados: " + lista);
        }
    }
}
```

#### Descripción
Clase encargada de gestionar el almacenamiento del historial de conversiones. Guarda las conversiones realizadas en un archivo de texto incluyendo fecha y hora de las mismas.
El método `guardarConversion(List<String> lista)` recibe una lista de conversiones y las escribe en el archivo.

---

### 3. InterfazUsuario

```java
package martinxcvi;

import java.util.Scanner;

public class InterfazUsuario {
    private final Scanner inputUsuario;
    private final SolicitudConversion solicitud;

    public InterfazUsuario() {
        this.inputUsuario = new Scanner(System.in);
        this.solicitud = new SolicitudConversion();
    }

    public void mostrarMenu() {
        System.out.println("|| Bienvenido al Conversor de Monedas ||\n");
        String menu = "..."; // Menú principal definido en el código.

        int opcionElegida = 0;
        while(opcionElegida != 9) {
            try {
                System.out.println(menu);
                System.out.print("Ingrese la opción deseada: ");
                opcionElegida = Integer.parseInt(inputUsuario.nextLine());
                // Lógica para manejar cada opción del menú.
            } catch (NumberFormatException error) {
                System.out.println("La entrada no es valida. Por favor, ingrese un numero\n");
            }
        }
    }
}
```

#### Descripción
Clase que presenta el menú interactivo al usuario y gestiona las entradas. Provee la interfaz para la entrada y salida de datos.
El método `mostrarMenu()` presenta el menú y maneja la lógica de selección. Depende de la clase `SolicitudConversion`.

---

### 4. SolicitudConversion

```java
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
            URI solicitudDireccion = URI.create("https://v6.exchangerate-api.com/..." + monedaBase + "/" + monedaFinal + "/" + cantidad);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(solicitudDireccion).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonElement elementoJSON = JsonParser.parseString(response.body());
            JsonObject objetoJSON = elementoJSON.getAsJsonObject();

            if (!objetoJSON.get("result").getAsString().equals("success")) {
                throw new RuntimeException("Error en la API: " + objetoJSON.get("error-type").getAsString());
            }

            String resultado = objetoJSON.get("conversion_result").getAsString();
            String respuesta = "\nEl monto base de " + cantidad + " " + monedaBase + " convertido a " + monedaFinal + " equivale a: " + resultado + " " + monedaFinal + "\n";
            System.out.println(respuesta);

            List<String> listaRespuestas = new ArrayList<>();
            listaRespuestas.add(respuesta);
            generadorHistorial.guardarConversion(listaRespuestas);

            return respuesta;
        } catch (NumberFormatException | IOException | InterruptedException event) {
            throw new RuntimeException("Error" + event.getMessage());
        }
    }
}
```

#### Descripción

Clase que interactúa con la ExchangeRate-API para realizar conversiones.
El método `ejecutarConversion(String monedaBase, Double cantidad, String monedaFinal)` realiza la llamada a la API y devuelve el resultado.
Depende de `GeneradorHistorial` y bibliotecas externas como `HttpClient` y `Gson`.

---

## 🔊 Consideraciones

1. **Errores comunes:**
   - Entradas inválidas en el menú.
   - Respuestas de la API con errores o no disponibles.
2. **Extensibilidad:**
   - Fácil de agregar nuevas monedas o métodos de conversión.
3. **Dependencias externas:**
   - API de ExchangeRate.
   - Biblioteca Gson.

---

## 📚 Aprender Más

- [Java Documentation](https://docs.oracle.com/en/java/)
- [IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/getting-started.html)
- [Gson Repository](https://github.com/google/gson)
- [ExchangeRate-API Documentation](https://www.exchangerate-api.com/docs/overview)

## 🧑‍💻 Desarrollador:

- [**MartinXCVI**](https://github.com/MartinXCVI)