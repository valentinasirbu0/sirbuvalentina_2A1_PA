package org.example.swingSetUp;

import org.example.JPA.DAOTests.UserTest;
import org.example.JPA.model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginPage extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private static Connection connection;
    public static User user;

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
            this.user = UserTest.findUser(username);
            openMusicPlayer();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
        passwordField.setText("");
    }


    private boolean isValidLogin(String username, char[] password) {
        String hashedPassword = UserTest.findUserPassword(username);
        if (!hashedPassword.equals(null)) {
            return BCrypt.checkpw(String.valueOf(password), hashedPassword);
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

        if (UserTest.checkUsername(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Hash the password using bcrypt
        String hashedPassword = BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt());
        var user = UserTest.UserTest(username, hashedPassword);
        JOptionPane.showMessageDialog(this, "Registration successful. You can now log in with your new account.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);

        usernameField.setText("");
        passwordField.setText("");
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

