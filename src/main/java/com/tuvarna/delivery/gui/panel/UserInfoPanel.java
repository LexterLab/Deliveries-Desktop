package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.gui.service.UserService;
import com.tuvarna.delivery.user.payload.request.UserRequestDTO;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;

public class UserInfoPanel extends JPanel {
    private final UserDTO userDTO;
    private final JTextField usernameField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField phoneNumberField;
    private final JTextField addressField;
    private final UserService userService = new UserService();
    public UserInfoPanel(UserDTO userDTO) {
        this.userDTO = userDTO;

        setLayout(new GridLayout(0, 2, 5, 5));
        setBackground(new Color(70, 64, 64));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.WHITE);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.WHITE);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setForeground(Color.WHITE);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.WHITE);


        usernameField = new JTextField(userDTO.username(), 10);
        firstNameField = new JTextField(userDTO.firstName(),10);
        lastNameField = new JTextField(userDTO.lastName(),20);
        phoneNumberField = new JTextField(userDTO.phoneNumber(),20);
        addressField = new JTextField(userDTO.address(),20);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateUser());
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(224, 143, 23));
        updateButton.setForeground(Color.WHITE);
        updateButton.setOpaque(true);
        updateButton.setBorderPainted(false);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteUser());
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(227, 14, 38));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);

        add(usernameLabel);
        add(usernameField);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(addressLabel);
        add(addressField);
        add(updateButton);
        add(deleteButton);
    }

    private void updateUser() {
        try {
            String username = usernameField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();

            ResponseEntity<UserDTO> response = userService.fetchUpdateUser(userDTO.id(),
                    new UserRequestDTO(firstName, lastName, username, phoneNumber, address));
            JOptionPane.showMessageDialog(this, "Updated successfully",
                    "Updated successfully", JOptionPane.INFORMATION_MESSAGE);
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();

        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteUser() {
        try {
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you wish to delete user?", "DELETION WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation == JOptionPane.OK_OPTION) {
                ResponseEntity<Void> response = userService.fetchDeleteUser(userDTO.id());
                JOptionPane.showMessageDialog(this, "Deleted successfully", "Deleted successfully", JOptionPane.INFORMATION_MESSAGE);
                Window window = SwingUtilities.getWindowAncestor(this);
                window.dispose();
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter.formatError(errorMessage), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
