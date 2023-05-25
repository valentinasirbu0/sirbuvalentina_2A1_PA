package net.javaguides.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.javaguides.demo.exception.ResourceNotFound;
import net.javaguides.demo.model.Player;
import net.javaguides.demo.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/players")
@Tag(name = "Players", description = "API endpoints for managing players")
public class PlayerController {

    @Autowired
    private PlayerRepo playerRepo;

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }

    @PostMapping
    public Player createPlayer(@RequestBody Player player) {
        return playerRepo.save(player);
    }

    @GetMapping("{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable long id) {
        Player player = playerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Player does not exist with id: " + id));
        return ResponseEntity.ok(player);
    }

    @PutMapping("{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable long id, @RequestBody Player player) {
        Player updatePlayer = playerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Player does not exist with id: " + id));
        updatePlayer.setName(player.getName());
        updatePlayer.setColor(player.getColor());
        playerRepo.save(updatePlayer);
        return ResponseEntity.ok(updatePlayer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable long id) {
        Player player = playerRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Player does not exist with id: " + id));
        playerRepo.delete(player);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
