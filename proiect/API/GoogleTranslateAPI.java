package org.example.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GoogleTranslateAPI {
    static String sourceLanguage = "en";
    public static String filePath = "C:\\Users\\Valea\\Desktop\\java\\response.json";

    public static String translate(String text, String targetLanguage) {
        try {
            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "application/gzip")
                    .header("X-RapidAPI-Key", "fe3a85cb68msh8fdf287c2934f85p14405bjsn5eac4f65a52a")
                    .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString("source=" + sourceLanguage + "&target=" + targetLanguage + "&q=" + text))
                    .build();

            // Send the HTTP request and retrieve the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response body
            System.out.println(response.body());
            String translatedText = parseTranslatedText(response.body());
            System.out.println(translatedText);

            return translatedText;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static String parseTranslatedText(String response) {
        // Parse the response body
        JSONObject json = new JSONObject(response);
        JSONObject data = json.optJSONObject("data");
        if (data != null) {
            JSONArray translations = data.optJSONArray("translations");

            // Extract the translated text
            if (translations != null && translations.length() > 0) {
                JSONObject translation = translations.getJSONObject(0);
                return translation.getString("translatedText");
            }
        }

        return null; // Return null if the data or translated text couldn't be extracted
    }

    public static void getAvailableLanguges() {
        String host = "google-translate1.p.rapidapi.com";
        List<String> languages = null;

        try {
            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2/languages"))
                    .header("Accept-Encoding", "application/gzip")
                    .header("X-RapidAPI-Key", "3d0b99adb7msh6766cd0dccf4a52p1d98d6jsnd97763195110")
                    .header("X-RapidAPI-Host", host)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            saveResponseAsJson(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveResponseAsJson(String body) {
        try {
            // Create a JSON object from the response body
            JSONObject json = new JSONObject(body);

            // Write the JSON object to a file
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(json.toString(4)); // Use 4 spaces for indentation
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Response saved successfully as JSON file: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save response as JSON file.");
            e.printStackTrace();
        }
    }

    public static List<String> parseLanguages(String responseBody) {
        List<String> languages = new ArrayList<>();

        // Extract the languages from the JSON response
        JSONObject json = new JSONObject(responseBody);
        JSONObject data = json.getJSONObject("data");
        JSONArray languagesArray = data.getJSONArray("languages");

        for (int i = 0; i < languagesArray.length(); i++) {
            JSONObject languageObject = languagesArray.getJSONObject(i);
            String language = languageObject.getString("language");
            languages.add(language);
        }
        return languages;
    }
}
