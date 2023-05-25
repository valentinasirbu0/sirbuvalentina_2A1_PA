package com.example.demo;

import com.example.demo.model.Game;
import com.example.demo.model.Player;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class CRUD {
    public static void createPlayer() {
        String url = "http://localhost:8080/api/v1/players";
        Player player = new Player("John Doe");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Player> requestEntity = new HttpEntity<>(player, headers);

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.additionalMessageConverters(new MappingJackson2HttpMessageConverter(
                Jackson2ObjectMapperBuilder.json().build()
        ));
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<Player> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Player.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Player createdPlayer = responseEntity.getBody();
            System.out.println("Created player: " + createdPlayer);
        } else {
            System.out.println("Failed to create player");
        }
    }

    public static void modifyPlayerName() {
        String url = "http://localhost:8080/api/v1/players/{id}";
        String playerId = "10"; // ID of the player to be modified
        String newName = "Jane Smith";

        // Construct the request payload
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", Long.parseLong(playerId));
        requestBody.put("name", newName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class, playerId);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Player name modified successfully");
        } else {
            System.out.println("Failed to modify player name");
        }
    }

    public static void deletePlayer() {
        String url = "http://localhost:8080/api/v1/players/{id}";
        String playerId = "12"; // ID of the player to be deleted

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class, playerId);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Player deleted successfully");
        } else {
            System.out.println("Failed to delete player");
        }
    }

    public static void getPlayers() {
        String url = "http://localhost:8080/api/v1/players";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Player[]> responseEntity = restTemplate.getForEntity(url, Player[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Player[] players = responseEntity.getBody();
            System.out.println("Registered players:");
            assert players != null;
            for (Player player : players) {
                System.out.println(player);
            }
        } else {
            System.out.println("Failed to get players");
        }
    }

    public static void getRecordedGames() {
        String url = "http://localhost:8080/api/v1/games";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Game[]> responseEntity = restTemplate.getForEntity(url, Game[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Game[] games = responseEntity.getBody();
            System.out.println("Recorded games:");
            assert games != null;
            for (Game game : games) {
                System.out.println(game);
            }
        } else {
            System.out.println("Failed to get recorded games");
        }
    }


}
