package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.demo.CRUD.*;

@SpringBootApplication
public class RestClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestClientApplication.class, args);

		// Create a new player
		createPlayer();

		// Modify the name of a player
		modifyPlayerName();

		// Delete a player
		deletePlayer();

		// Get the list of registered players
		getPlayers();

		// Get the recorded games
		getRecordedGames();
	}


}
