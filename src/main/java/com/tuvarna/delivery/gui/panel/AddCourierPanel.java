package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.authentication.payload.request.SignUpRequestDTO;
import com.tuvarna.delivery.gui.service.AuthService;
import com.tuvarna.delivery.gui.service.CourierService;
import com.tuvarna.delivery.gui.service.OfficeService;
import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.payload.response.OfficeDTO;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddCourierPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField matchingPasswordField;
    private final JTextField personalPhoneNumberField;
    private final JTextField addressField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField phoneNumberField;
    private final JSpinner experienceSpinner;
    private final JComboBox<String> officeComboBox;
    public AddCourierPanel() {
        setLayout(new GridLayout(0, 2, 5, 5));
        setBackground(new Color(70, 64, 64));

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.WHITE);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.WHITE);

        JLabel officeLabel = new JLabel("Office:");
        officeLabel.setForeground(Color.WHITE);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setForeground(Color.WHITE);

        JLabel experienceLabel = new JLabel("Experience");
        experienceLabel.setForeground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);

        JLabel matchingPasswordLabel = new JLabel("Confirm Password:");
        matchingPasswordLabel.setForeground(Color.WHITE);

        JLabel personalPhoneNumberLabel = new JLabel("Personal Phone number:");
        personalPhoneNumberLabel.setForeground(Color.WHITE);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setForeground(Color.WHITE);

        firstNameField = new JTextField(10);
        lastNameField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 55, 1);
        experienceSpinner = new JSpinner(spinnerModel);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        matchingPasswordField = new JPasswordField(20);
        personalPhoneNumberField = new JTextField(20);
        addressField = new JTextField(20);


        officeComboBox = new JComboBox<>();
        fetchOffices();

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> createCourier());
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBackground(new Color(43, 140, 3));
        createButton.setForeground(Color.WHITE);
        createButton.setOpaque(true);
        createButton.setBorderPainted(false);

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(matchingPasswordLabel);
        add(matchingPasswordField);
        add(officeLabel);
        add(officeComboBox);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(addressLabel);
        add(addressField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(personalPhoneNumberLabel);
        add(personalPhoneNumberField);
        add(experienceLabel);
        add(experienceSpinner);
        add(createButton);
    }

    private void fetchOffices() {
        try {
            OfficeService officeService = new OfficeService();
            ResponseEntity<java.util.List<OfficeDTO>> response = officeService.fetchAllOffices();
            List<OfficeDTO> officeDTOS = response.getBody();
            assert officeDTOS != null;
            for (OfficeDTO officeDTO : officeDTOS) {
                officeComboBox.addItem(officeDTO.name());
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createCourier() {
        var username = usernameField.getText();
        var passwordChars = passwordField.getPassword();
        var password = new String(passwordChars);
        char[] matchingPasswordChars = matchingPasswordField.getPassword();
        String matchingPassword = new String(matchingPasswordChars);
        String address = addressField.getText();
        String personalPhoneNumber = personalPhoneNumberField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        int experience = (int) experienceSpinner.getValue();

        try{
            AuthService authService = new AuthService();
            authService.fetchSignUp(new SignUpRequestDTO(firstName, lastName, username, password, matchingPassword,
                  personalPhoneNumber, address));

            CourierService courierService = new CourierService();
            courierService.fetchEnlistCourier((long) (officeComboBox.getSelectedIndex() + 1),
                    new CourierRequestDTO(phoneNumber, experience, username));
            JOptionPane.showMessageDialog(this, "Created successfully",
                    "Created successfully", JOptionPane.INFORMATION_MESSAGE);
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();

        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
