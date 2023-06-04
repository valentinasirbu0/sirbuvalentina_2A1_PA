package org.example.API;

import org.example.JPA.DAOTests.*;
import org.example.JPA.model.Album;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class SpotifyDataSet {
    private String accessToken;

    public SpotifyDataSet() {
        accessToken = getToken();
        //getArtists();
        //getGenres();
        //getAlbums1();
        //getAlbums2();
        //AlbumGenreTest.AlbumGenreTest();
        //AlbumArtistTest.AlbumGenreTest();
        //getSongs();
        //AlbumSongTest.AlbumSongTest();
    }

    public void getSongs() {
        String SEARCH_API_URL = "https://api.spotify.com/v1/search?q=tr&type=track&limit=50";
        String OUTPUT_DIRECTORY = "C:\\Users\\Valea\\Desktop\\java\\songs";

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SEARCH_API_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newBuilder().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                String responseBody = response.body();
                JSONObject json = new JSONObject(responseBody);
                JSONArray tracksArray = json.getJSONObject("tracks").getJSONArray("items");

                for (int i = 0; i < tracksArray.length(); i++) {
                    JSONObject trackJson = tracksArray.getJSONObject(i);
                    String previewUrl = trackJson.getString("preview_url");
                    String name = trackJson.getString("name");

                    if (previewUrl != null) {
                        String outputFileName = "track_sample_" + i + ".mp3";
                        Path outputPath = Path.of(OUTPUT_DIRECTORY, outputFileName);

                        try (InputStream inputStream = new URL(previewUrl).openStream()) {
                            Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
                            byte[] songData = Files.readAllBytes(outputPath);
                            var song = new SongTest().SongTest(name, songData, outputPath);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Track sample downloaded successfully! Saved as: " + outputFileName);
                    }
                }
            } else {
                System.out.println("Failed to retrieve track samples. Response code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void getAlbums2() {
        String SEARCH_API_URL = "https://api.spotify.com/v1/search?q=al&type=album&limit=50";
        try {
            // Create the HttpRequest with the Authorization header
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SEARCH_API_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            // Send the HttpRequest and retrieve the response
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response body to extract the albums
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray albumsArray = jsonResponse.getJSONObject("albums").getJSONArray("items");

            // Process each album
            for (int i = 0; i < albumsArray.length(); i++) {
                JSONObject albumObject = albumsArray.getJSONObject(i);
                String albumName = albumObject.getString("name");

                // Retrieve artists
                JSONArray artistsArray = albumObject.getJSONArray("artists");
                Album album;

                if (artistsArray.length() > 0) {
                    String artistName = artistsArray.getJSONObject(0).getString("name");
                    var artist = ArtistsDAOTest.findArtistByName(artistName);

                    if (artist != null) {
                        album = AlbumDAOTest.AlbumTest(albumObject.getString("release_date"), albumName, artist);
                    } else {
                        var artist1 = ArtistsDAOTest.ArtistTest(artistName);
                        album = AlbumDAOTest.AlbumTest(albumObject.getString("release_date"), albumName, artist1);
                    }

                    // Print artist's name
                    System.out.println("Artist Name: " + artistName);
                } else {
                    album = AlbumDAOTest.AlbumTest(albumObject.getString("release_date"), albumName);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAlbums1() {
        String SEARCH_API_URL = "https://api.spotify.com/v1/albums?ids=382ObEPsp2rxGrnsizN5TX%2C1A2GTWGtFfWp7KSQTwWOyo%2C2noRn2Aes5aoNVsU6iWThc";
        try {
            // Create the HttpRequest with the Authorization header
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SEARCH_API_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            // Send the HttpRequest and retrieve the response
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response body to extract the albums
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray albumsArray = jsonResponse.getJSONArray("albums");

            // Process each album
            for (int i = 0; i < albumsArray.length(); i++) {
                JSONObject albumObject = albumsArray.getJSONObject(i);
                String albumName = albumObject.getString("name");

                // Retrieve artists
                JSONArray artistsArray = albumObject.getJSONArray("artists");
                Album album;

                if (artistsArray.length() > 0) {
                    String artistName = artistsArray.getJSONObject(0).getString("name");
                    var artist = ArtistsDAOTest.findArtistByName(artistName);

                    if (artist != null) {
                        album = AlbumDAOTest.AlbumTest(albumObject.getString("release_date"), albumName, artist);
                    } else {
                        var artist1 = ArtistsDAOTest.ArtistTest(artistName);
                        album = AlbumDAOTest.AlbumTest(albumObject.getString("release_date"), albumName, artist1);
                    }
                } else {
                    album = AlbumDAOTest.AlbumTest(albumObject.getString("release_date"), albumName);
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private void getGenres() {
        String AVAILABLE_GENRE_SEEDS_API_URL = "https://api.spotify.com/v1/recommendations/available-genre-seeds";
        try {
            // Create the HttpRequest with the Authorization header
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(AVAILABLE_GENRE_SEEDS_API_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            // Send the HttpRequest and retrieve the response
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response body to extract the genre seeds
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONArray genreSeedsArray = jsonResponse.getJSONArray("genres");

            // Print the available genre seeds
            for (int i = 0; i < genreSeedsArray.length(); i++) {
                String genreSeed = genreSeedsArray.getString(i);
                var genre = GenreDAOTest.GenreTest(genreSeed);
                //System.out.println(genreSeed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getArtists() {
        String SEARCH_API_URL = "https://api.spotify.com/v1/search?q=ar&type=artist";
        try {
            // Create the HttpRequest with the Authorization header
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(SEARCH_API_URL))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            // Send the HttpRequest and retrieve the response
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the response body to extract the artists
            JSONObject jsonResponse = new JSONObject(response.body());
            JSONObject artistsObject = jsonResponse.getJSONObject("artists");
            JSONArray itemsArray = artistsObject.getJSONArray("items");

            // Print the names of the artists
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject artistObject = itemsArray.getJSONObject(i);
                String artistName = artistObject.getString("name");
                var artist = ArtistsDAOTest.ArtistTest(artistName);
                //System.out.println(artistName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getToken() {
        String tokenUrl = "https://accounts.spotify.com/api/token";
        String clientId = "c7f8931b0fdb40259a6ec73d686290bc";
        String clientSecret = "07f33552384e482ba0cb93994aa7edc2";

        try {
            String requestBody = "grant_type=" + URLEncoder.encode("client_credentials", StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(tokenUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonResponse = new JSONObject(response.body());
            String accessToken = jsonResponse.getString("access_token");

            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
