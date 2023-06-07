package org.example.swingSetUp;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private static Connection connection;
    public static Integer userID;
    public static char[] passwd;

    public LoginPage(Connection connection) {
        super("Login");
        this.connection = connection;

        usernameField = new JTextField(20); // Set the preferred width of the text field
        passwordField = new JPasswordField(20); // Set the preferred width of the text field
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // Adjust the insets as needed

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(loginPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 400); // Set the size of the window
        setLocationRelativeTo(null);
    }


    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        if (isValidLogin(username, password)) {
            this.userID = getUserIdByUsername(username);
            this.passwd = password;
            openMusicPlayer();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
        passwordField.setText("");
    }


    private boolean isValidLogin(String username, char[] password) {
        try {
            String sql = "SELECT passwd FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("passwd");
                return BCrypt.checkpw(String.valueOf(password), hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void register() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter a password.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery);
            checkUsernameStatement.setString(1, username);
            ResultSet resultSet = checkUsernameStatement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Hash the password using bcrypt
            String hashedPassword = BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt());

            String insertUserQuery = "INSERT INTO users (username, passwd) VALUES (?, ?)";
            PreparedStatement insertUserStatement = connection.prepareStatement(insertUserQuery);
            insertUserStatement.setString(1, username);
            insertUserStatement.setString(2, hashedPassword);
            insertUserStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration successful. You can now log in with your new account.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred during registration. Please try again.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        }

        usernameField.setText("");
        passwordField.setText("");
    }

    public static int getUserIdByUsername(String username) {
        int userId = -1; // Default value if the user is not found or an error occurs
        try {
            String sql = "SELECT id FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    private void openMusicPlayer() {
        MusicPlayer musicPlayer = new MusicPlayer(connection);
        musicPlayer.setVisible(true);
        dispose(); // Close the login window
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            login();
        }
        if (e.getSource() == registerButton) {
            register();
        }
    }
}

