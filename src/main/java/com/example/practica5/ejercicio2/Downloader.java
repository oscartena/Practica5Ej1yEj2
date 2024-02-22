package com.example.practica5.ejercicio2;

import javafx.collections.ListChangeListener;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Implementación de ListChangeListener para observar cambios en la lista de URLs
public class Downloader implements ListChangeListener<String> {

    // Método invocado cuando hay cambios en la lista de URLs
    @Override
    public void onChanged(Change<? extends String> change) {
        while (change.next()) {
            // Verifica si se han añadido elementos a la lista
            if (change.wasAdded()) {
                for (String addedUrl : change.getAddedSubList()) {
                    // Inicia un proceso asíncrono para descargar y guardar la página web
                    CompletableFuture.supplyAsync(() -> downloadWebPage(addedUrl))
                            .thenAccept(content -> saveToFile(addedUrl, content));
                }
            }
        }
    }

    // Método para descargar el contenido de una página web dada una URL
    private static String downloadWebPage(String urlString) {
        try {
            // Crea un cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Construye la solicitud GET para la URL proporcionada
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .GET()
                    .build();

            // Envía la solicitud y obtiene la respuesta como una cadena de texto
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body(); // Retorna el contenido de la página web
        } catch (IOException | InterruptedException e) {
            // Maneja posibles errores de la operación de descarga
            System.err.println("Error al descargar la página: " + e.getMessage());
            return null;
        }
    }

    // Método para guardar el contenido en un archivo dado el nombre de la URL y el contenido
    private static void saveToFile(String url, String content) {
        try {
            // Define la ruta del directorio donde se guardarán los archivos
            Path directoryPath = Path.of("src", "main", "resources", "com", "example", "practica5", "paginasweb");

            // Construye el nombre del archivo reemplazando caracteres no permitidos
            String fileName = url.replaceAll("[^a-zA-Z0-9.-]", "_") + ".html";

            // Resuelve la ruta completa del archivo dentro del directorio especificado
            Path filePath = directoryPath.resolve(fileName);

            // Crea los directorios si no existen
            Files.createDirectories(directoryPath);

            // Utiliza un BufferedWriter para escribir el contenido en el archivo
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE)) {
                writer.write(content);
            }

        } catch (IOException e) {
            // Maneja posibles errores de la operación de guardado
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}

