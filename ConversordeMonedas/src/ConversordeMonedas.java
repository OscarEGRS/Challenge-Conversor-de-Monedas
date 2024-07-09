import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Scanner;

public class ConversordeMonedas {

    private static final String API_KEY = "15fde76faf3c56cd0482e9c4"; // Reemplaza con tu clave API
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Obtener las tasas de cambio actuales
        ExchangeRates exchangeRates = getExchangeRates();

        if (exchangeRates == null || exchangeRates.conversionRates == null) {
            System.out.println("No se pudieron obtener las tasas de cambio. Saliendo del programa.");
            return;
        }

        // Resto del código para convertir monedas...
    }

    // Método para obtener las tasas de cambio actuales desde la API
    private static ExchangeRates getExchangeRates() {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(API_URL);

        try {
            org.apache.http.HttpResponse response = httpClient.execute(request);
            String json = EntityUtils.toString(response.getEntity());

            Gson gson = new Gson();
            ExchangeRates exchangeRates = gson.fromJson(json, ExchangeRates.class);

            // Verificar si la deserialización fue exitosa
            if (exchangeRates == null || exchangeRates.conversionRates == null) {
                System.out.println("Error: No se pudieron convertir los datos JSON en objetos Java correctamente.");
                return null;
            }

            return exchangeRates;

        } catch (IOException e) {
            System.err.println("Error al obtener las tasas de cambio desde la API: " + e.getMessage());
            return null;
        }
    }

    // Clase para deserializar la respuesta JSON
    static class ExchangeRates {
        ConversionRates conversionRates;
    }

    static class ConversionRates {
        double USD;
        double EUR;
        double GBP;
        double JPY;
        double CAD;
        // Puedes agregar más monedas según sea necesario
    }
}
