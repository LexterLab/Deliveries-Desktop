package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.city.payload.CityDTO;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.StatusDTO;
import com.tuvarna.delivery.gui.service.CityService;
import com.tuvarna.delivery.gui.service.DeliveryService;
import com.tuvarna.delivery.gui.service.StatusService;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeliveryInfoPanel extends JPanel {
    private final JTextField weightField;
    private final JTextField totalPriceField;
    private final JTextField detailsField;
    private final JTextField productNameField;
    private final JComboBox<String> fromCityComboBox;
    private final JComboBox<String> toCityComboBox;
    private final JComboBox<String> statusComboBox;

    public DeliveryInfoPanel(DeliveryDTO deliveryDTO) {
        setLayout(new GridLayout(0, 2, 5, 5));
        setBackground(new Color(70, 64, 64));

        JLabel weightLabel = new JLabel("Weight (KG):");
        weightLabel.setForeground(Color.WHITE);

        JLabel totalPriceLabel = new JLabel("Total Price:");
        totalPriceLabel.setForeground(Color.WHITE);

        JLabel detailsLabel = new JLabel("Details:");
        detailsLabel.setForeground(Color.WHITE);

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setForeground(Color.WHITE);

        JLabel fromCityLabel = new JLabel("From City:");
        fromCityLabel.setForeground(Color.WHITE);

        JLabel toCityLabel = new JLabel("To City:");
        toCityLabel.setForeground(Color.WHITE);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setForeground(Color.WHITE);


        weightField = new JTextField(deliveryDTO.weightKG() + "", 10);
        totalPriceField = new JTextField(deliveryDTO.totalPrice() + "",10);
        detailsField = new JTextField(deliveryDTO.details(),20);
        productNameField = new JTextField(deliveryDTO.productName(),20);


        fromCityComboBox = new JComboBox<>();
        fetchCities(fromCityComboBox);


        toCityComboBox = new JComboBox<>();
        fetchCities(toCityComboBox);


        statusComboBox = new JComboBox<>();
        fetchStatuses(statusComboBox);


        setSelectedItem(deliveryDTO.statusName(), statusComboBox);
        setSelectedItem(deliveryDTO.fromCityName(), fromCityComboBox);
        setSelectedItem(deliveryDTO.toCityName(), toCityComboBox);


        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            updateDelivery(deliveryDTO);
        });

        add(weightLabel);
        add(weightField);
        add(totalPriceLabel);
        add(totalPriceField);
        add(detailsLabel);
        add(detailsField);
        add(productNameLabel);
        add(productNameField);
        add(fromCityLabel);
        add(fromCityComboBox);
        add(toCityLabel);
        add(toCityComboBox);
        add(statusLabel);
        add(statusComboBox);
        add(updateButton);
    }


    public Double getWeightKG() {
        return Double.parseDouble(weightField.getText());
    }

    public Double getTotalPrice() {
        return Double.parseDouble(totalPriceField.getText());
    }

    public String getDetails() {
        return detailsField.getText();
    }

    public String getProductName() {
        return productNameField.getText();
    }

    public String getFromCity() {
        return (String) fromCityComboBox.getSelectedItem();
    }

    public String getToCity() {
        return (String) toCityComboBox.getSelectedItem();
    }

    public int getStatus() {
        return  statusComboBox.getSelectedIndex();
    }

    private void setSelectedItem(String name, JComboBox<String> comboBox) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equalsIgnoreCase(name)) {
                comboBox.setSelectedIndex(i);
            }
        }
    }

    private void fetchStatuses(JComboBox<String> statusComboBox) {
        try {
            StatusService statusService = new StatusService();
            ResponseEntity<List<StatusDTO>> response = statusService.fetchAllStatuses();
            List<StatusDTO> statusDTOs = response.getBody();
            assert statusDTOs != null;
            for (StatusDTO statusDTO : statusDTOs) {
                statusComboBox.addItem(statusDTO.type());
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void fetchCities(JComboBox<String> cityBox) {
       try {
           CityService cityService = new CityService();
           ResponseEntity<List<CityDTO>> response = cityService.fetchAllCities();
           List<CityDTO> cityDTOS = response.getBody();
           assert cityDTOS != null;
           for (CityDTO cityDTO : cityDTOS) {
               cityBox.addItem(cityDTO.name());
           }
       } catch (HttpClientErrorException e) {
           String errorMessage = e.getResponseBodyAsString();
           JOptionPane.showMessageDialog(this, ErrorFormatter
                   .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
       }
   }

    private void updateDelivery(DeliveryDTO deliveryDTO) {
        DeliveryService deliveryService = new DeliveryService();
        try {
            Double weight = getWeightKG();
            Double totalPrice = getTotalPrice();
            String details = getDetails();
            String productName = getProductName();
            String fromCity = getFromCity();
            String toCity = getToCity();
            ResponseEntity<DeliveryDTO> response = deliveryService.fetchUpdateDelivery(deliveryDTO.id(),
                    new DeliveryRequestDTO(fromCity, toCity, weight, totalPrice, details, productName));
            JOptionPane.showMessageDialog(this, "Updated successfully",
                    "Updated successfully", JOptionPane.INFORMATION_MESSAGE);
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            int statusId = getStatus() + 1;
            ResponseEntity<DeliveryDTO> response = deliveryService.fetchUpdateDeliveryStatus(deliveryDTO.id(), statusId);
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }


    }
}