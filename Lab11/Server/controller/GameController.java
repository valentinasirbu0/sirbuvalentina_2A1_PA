package net.javaguides.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.javaguides.demo.exception.ResourceNotFound;
import net.javaguides.demo.model.Game;
import net.javaguides.demo.repository.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@Tag(name = "Games", description = "API endpoints for managing games")
//http://localhost:8080/swagger-ui.html
public class GameController {

    @Autowired
    private GameRepo gameRepo;

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepo.findAll();
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameRepo.save(game);
    }
}
