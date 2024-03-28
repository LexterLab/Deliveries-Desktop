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
    private final JTextField officeField;
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

        officeField = new JTextField(courierDTO.officeName(), 10);
        firstNameField = new JTextField(courierDTO.firstName(),10);
        lastNameField = new JTextField(courierDTO.lastName(),20);
        phoneNumberField = new JTextField(courierDTO.workPhoneNumber(),20);
        experienceField = new JTextField(courierDTO.yearsOfExperience() + "",20);

        officeComboBox = new JComboBox<>();
        fetchOffices();
        setSelectedItem(courierDTO.officeName(), officeComboBox);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateCourier());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCourier());

        add(officeLabel);
        add(officeField);
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
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteCourier() {

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

    private void setSelectedItem(String name, JComboBox<String> comboBox) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equalsIgnoreCase(name)) {
                comboBox.setSelectedIndex(i);
            }
        }
    }
}
