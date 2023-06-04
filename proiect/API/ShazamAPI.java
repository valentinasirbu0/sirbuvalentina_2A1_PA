package org.example.API;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ShazamAPI {
    public static void identifySong(String filePath) {
        HttpPost request = new HttpPost("https://shazam-api6.p.rapidapi.com/shazam/recognize/");
        request.addHeader("X-RapidAPI-Key", "6e81b290e9msh63821818134ad21p165652jsn4f6c703e196d");
        request.addHeader("X-RapidAPI-Host", "shazam-api6.p.rapidapi.com");

        // Load the MP3 file from the file path
        File file = new File(filePath);

        // Build the multipart request body
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("upload_file", file, ContentType.DEFAULT_BINARY, file.getName())
                .build();

        request.setEntity(entity);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            System.out.println(responseBody);
            LyricsExtractor.extractLinesFromURL(new JSONObject(responseBody));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
