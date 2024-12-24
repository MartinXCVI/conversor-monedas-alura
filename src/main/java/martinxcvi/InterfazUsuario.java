package martinxcvi;

import java.util.Scanner;

public class InterfazUsuario {
    // Inicializaciones
    private final Scanner inputUsuario;
    private final SolicitudConversion solicitud;

    public InterfazUsuario() {
        this.inputUsuario = new Scanner(System.in);
        this.solicitud = new SolicitudConversion();
    }

    public void mostrarMenu() {
        System.out.println("|| Bienvenido al Conversor de Monedas ||\n");

        String menu = """
                **************************************************
                ----- Seleccione una opción -----\n
                1) Dólar estadounidense => Peso argentino
                2) Peso argentino => Dólar estadounidense
                3) Dólar estadounidense => Peso chileno
                4) Peso chileno => Dólar estadounidense
                5) Dólar estadounidense => Real brasileño
                6) Real brasileño => Dólar estadounidense
                7) Dólar estadounidense => Peso uruguayo
                8) Peso uruguayo => Dólar estadounidense
                
                9) Salir
                ****************************************************
                """;

        int opcionElegida = 0;
        while(opcionElegida != 9) {
            try {
                System.out.println(menu);
                System.out.print("Ingrese la opción deseada: ");
                opcionElegida = Integer.parseInt(inputUsuario.nextLine());

                switch (opcionElegida) {
                    case 1:
                        System.out.println("Por favor ingrese el monto en dólares a convertir en pesos argentinos:");
                        double dolarEnPesoArg = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("USD", dolarEnPesoArg, "ARS");
                        break;
                    case 2:
                        System.out.println("Por favor ingrese el monto en pesos argentinos en convertir en dólares:");
                        double pesoArgEnDolar = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("ARS", pesoArgEnDolar, "USD");
                        break;
                    case 3:
                        System.out.println("Por favor ingrese el monto en dólares a convertir en pesos chilenos:");
                        double dolarEnPesoChi = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("USD", dolarEnPesoChi, "CLP");
                        break;
                    case 4:
                        System.out.println("Por favor ingrese el monto en pesos chilenos a convertir en dolares");
                        double pesoChiEnDolar = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("CLP", pesoChiEnDolar, "USD");
                        break;
                    case 5:
                        System.out.println("Por favor ingrese el monto en dolares a convertir en reales brasileños ");
                        double dolarEnRealBra = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("USD", dolarEnRealBra, "BRL");
                        break;
                    case 6:
                        System.out.println("Por favor ingrese el monto en reales brasileños a convertir en dolares");
                        double realBraEnDolar = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("BRL", realBraEnDolar, "USD");
                        break;
                    case 7:
                        System.out.println("Por favor ingrese el monto en dolares a convertir en pesos uruguayos");
                        double dolarEnPesoUru = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("USD", dolarEnPesoUru, "UYU");
                        break;
                    case 8:
                        System.out.println("Por favor ingrese el monto en pesos uruguayos a convertir en dolares");
                        double pesoUruEnDolar = Double.parseDouble(inputUsuario.nextLine());
                        solicitud.ejecutarConversion("UYU", pesoUruEnDolar, "USD");
                        break;
                    case 9:
                        System.out.println("Finalizando el programa");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente de nuevo\n");
                        break;
                }
            } catch (NumberFormatException error) {
                System.out.println("La entrada no es valida. Por favor, ingrese un numero\n");
            } catch (RuntimeException error) {
                System.out.println(error.getMessage());
                System.out.println("Finalizando el programa.");
            }
        }
    }
}
