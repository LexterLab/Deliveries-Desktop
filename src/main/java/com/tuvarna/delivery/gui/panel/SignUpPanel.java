package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.authentication.payload.request.SignUpRequestDTO;
import com.tuvarna.delivery.gui.service.AuthService;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;

public class SignUpPanel extends JPanel {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField matchingPasswordField;
    private final JTextField phoneNumberField;
    private final JTextField addressField;

    public SignUpPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(70, 64, 64));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        firstNameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(firstNameLabel, gbc);

        firstNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(firstNameField, gbc);


        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lastNameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lastNameLabel, gbc);

        lastNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lastNameField, gbc);


        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(passwordField, gbc);

        JLabel matchingPasswordLabel = new JLabel("Confirm Password:");
        matchingPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        matchingPasswordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(matchingPasswordLabel, gbc);

        matchingPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(matchingPasswordField, gbc);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        phoneNumberLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(phoneNumberLabel, gbc);

        phoneNumberField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(phoneNumberField, gbc);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        addressLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(addressLabel, gbc);

        addressField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(addressField, gbc);

        JButton signUpButton = getSignUpButton();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);
    }

    private JButton getSignUpButton() {
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setBackground(new Color(51, 102, 255));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setOpaque(true);
        signUpButton.setBorderPainted(false);
        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            char[] matchingPasswordChars = matchingPasswordField.getPassword();
            String matchingPassword = new String(matchingPasswordChars);
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            AuthService service = new AuthService();
            try {
                ResponseEntity<String> response = service.fetchSignUp(new SignUpRequestDTO(firstName, lastName, username, password, matchingPassword,
                       phoneNumber, address));
                JOptionPane.showMessageDialog(SignUpPanel.this, response.getBody(),
                        "Successful sign up!", JOptionPane.INFORMATION_MESSAGE);
                Window window = SwingUtilities.getWindowAncestor(SignUpPanel.this);
                window.dispose();
            } catch (HttpClientErrorException ex) {
                String errorMessage = ex.getResponseBodyAsString();

                JOptionPane.showMessageDialog(SignUpPanel.this, ErrorFormatter
                        .formatError(errorMessage), "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return signUpButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sign Up Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new SignUpPanel());
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
