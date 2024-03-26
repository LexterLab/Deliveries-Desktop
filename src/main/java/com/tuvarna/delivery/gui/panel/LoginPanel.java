package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.authentication.payload.request.LoginRequestDTO;
import com.tuvarna.delivery.gui.AccessTokenStorage;
import com.tuvarna.delivery.gui.service.AuthService;
import com.tuvarna.delivery.jwt.JWTAuthenticationResponse;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(70, 64, 64));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);


        JButton loginButton = setLoginBtn();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        JButton signupButton = setSignUpButton();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(signupButton, gbc);
    }

    private JButton setSignUpButton() {
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 14));
        signupButton.setBackground(new Color(51, 102, 255));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(e -> {
            SignUpPanel signUpPanel = new SignUpPanel();


            JDialog dialog = new JDialog();
            dialog.setTitle("Sign Up");
            dialog.setModal(true);
            dialog.setContentPane(signUpPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        });
        return signupButton;
    }

    private JButton setLoginBtn() {
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 153, 51));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            AuthService service = new AuthService();
            try {
                ResponseEntity<JWTAuthenticationResponse> response = service.fetchSignIn(new LoginRequestDTO(username, password));
                AccessTokenStorage.storeAccessToken(Objects.requireNonNull(response.getBody()).getAccessToken());
            } catch (HttpClientErrorException ex) {
                String errorMessage = ex.getResponseBodyAsString();

                JOptionPane.showMessageDialog(LoginPanel.this, ErrorFormatter
                        .formatError(errorMessage), "Login Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(LoginPanel.this, "An error occurred: " + exception
                        .getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        return loginButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new LoginPanel());
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}