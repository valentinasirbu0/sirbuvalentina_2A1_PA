package net.javaguides.demo;

import net.javaguides.demo.model.Game;
import net.javaguides.demo.model.Player;
import net.javaguides.demo.repository.GameRepo;
import net.javaguides.demo.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private GameRepo gameRepo;

    @Override
    public void run(String... args) throws Exception {
        /*
		Player p1 = new Player();
		p1.setName("Alex");
		p1.setColor("Red");
		playerRepo.save(p1);

		Player p2 = new Player();
		p2.setName("John");
		p2.setColor("Blue");
		playerRepo.save(p2);
		*/

        /*
        Game g1 = new Game();
        Instant timestamp = Instant.now();
        g1.setTime(timestamp);
        g1.setWinner("Valea");
        gameRepo.save(g1);
        */

    }
}
