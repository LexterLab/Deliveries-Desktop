package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.gui.service.CourierService;
import com.tuvarna.delivery.gui.service.OfficeService;
import com.tuvarna.delivery.office.payload.request.UpdateCourierRequestDTO;
import com.tuvarna.delivery.office.payload.response.CourierDTO;
import com.tuvarna.delivery.office.payload.response.OfficeDTO;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CourierInfoPanel extends JPanel {
    private final CourierService courierService = new CourierService();
    private final CourierDTO courierDTO;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField phoneNumberField;
    private final JTextField experienceField;
    private final JComboBox<String> officeComboBox;
    public CourierInfoPanel(CourierDTO courierDTO) {
        this.courierDTO = courierDTO;

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

        firstNameField = new JTextField(courierDTO.firstName(),10);
        lastNameField = new JTextField(courierDTO.lastName(),20);
        phoneNumberField = new JTextField(courierDTO.workPhoneNumber(),20);
        experienceField = new JTextField(courierDTO.yearsOfExperience() + "",20);

        officeComboBox = new JComboBox<>();
        fetchOffices();
        setSelectedItem(officeComboBox);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateCourier());
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(224, 143, 23));
        updateButton.setForeground(Color.WHITE);
        updateButton.setOpaque(true);
        updateButton.setBorderPainted(false);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCourier());
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(227, 14, 38));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);

        add(officeLabel);
        add(officeComboBox);
        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(experienceLabel);
        add(experienceField);
        add(updateButton);
        add(deleteButton);
    }

    public void updateCourier() {
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            long officeId  = officeComboBox.getSelectedIndex() + 1;
            int experience = Integer.parseInt(experienceField.getText());

            ResponseEntity<UpdateCourierRequestDTO> response = courierService
                    .fetchUpdateCourier(courierDTO.officeId(), courierDTO.id(),
                            new UpdateCourierRequestDTO(firstName, lastName, phoneNumber, experience, officeId));
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

    public void deleteCourier() {
        try {
            int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure?", "DELETION WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation == JOptionPane.OK_OPTION) {
                ResponseEntity<Void> response = courierService.fetchDeleteCourier(courierDTO.officeId(), courierDTO.id());
                JOptionPane.showMessageDialog(this, "Deleted successfully", "Deleted successfully", JOptionPane.INFORMATION_MESSAGE);
                Window window = SwingUtilities.getWindowAncestor(this);
                window.dispose();
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter.formatError(errorMessage), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchOffices() {
        try {
            OfficeService officeService = new OfficeService();
            ResponseEntity<List<OfficeDTO>> response = officeService.fetchAllOffices();
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

    private void setSelectedItem(JComboBox<String> comboBox) {
            comboBox.setSelectedIndex((int) (courierDTO.id() - 1));
        }
    }

