package org.example.swingSetUp;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class StartSwing {
    private Connection connection;

    public StartSwing() {
        try {
            // Connect to the database
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lab8java", "postgres", "valea");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage(connection);
            loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginPage.setVisible(true);
        });
    }
}
