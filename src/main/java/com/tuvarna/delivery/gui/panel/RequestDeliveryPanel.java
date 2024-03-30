package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.city.payload.CityDTO;
import com.tuvarna.delivery.delivery.payload.request.DeliveryRequestDTO;
import com.tuvarna.delivery.gui.service.CityService;
import com.tuvarna.delivery.gui.service.DeliveryService;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class RequestDeliveryPanel extends JPanel {

    private final JTextField productNameField;
    private final JTextArea detailsField;
    private final JSpinner priceSpinner;
    private final JSpinner weightSpinner;
    private final JComboBox<String> fromCityComboBox;
    private final JComboBox<String> toCityComboBox;


    public RequestDeliveryPanel() {
        setLayout(new GridLayout(0, 2, 5, 5));
        setBackground(new Color(70, 64, 64));
        setPreferredSize(new Dimension(600, 200));

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setForeground(Color.WHITE);

        JLabel priceLabel = new JLabel("Total Price:");
        priceLabel.setForeground(Color.WHITE);

        JLabel weightLabel = new JLabel("Weight(KG):");
        weightLabel.setForeground(Color.WHITE);

        JLabel fromCityLabel = new JLabel("From City:");
        fromCityLabel.setForeground(Color.WHITE);

        JLabel toCityLabel = new JLabel("To City:");
        toCityLabel.setForeground(Color.WHITE);

        JLabel detailsLabel = new JLabel("Description:");
        detailsLabel.setForeground(Color.WHITE);

        productNameField = new JTextField(20);
        detailsField = new JTextArea(5, 100);
        SpinnerModel priceModel = new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1);
        SpinnerModel weightModel = new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1);
        priceSpinner = new JSpinner(priceModel);
        weightSpinner = new JSpinner(weightModel);
        fromCityComboBox = new JComboBox<>();
        toCityComboBox = new JComboBox<>();

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor)weightSpinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(1);
        format.setMaximumFractionDigits(2);

        fetchCities(fromCityComboBox, toCityComboBox);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> fetchCreateDelivery());

        add(productNameLabel);
        add(productNameField);
        add(priceLabel);
        add(priceSpinner);
        add(weightLabel);
        add(weightSpinner);
        add(fromCityLabel);
        add(fromCityComboBox);
        add(toCityLabel);
        add(toCityComboBox);
        add(detailsLabel);
        add(detailsField);
        add(createButton);


    }

    private void fetchCities(JComboBox<String> cityBox, JComboBox<String> cityBox2) {
        try {
            CityService cityService = new CityService();
            ResponseEntity<java.util.List<CityDTO>> response = cityService.fetchAllCities();
            List<CityDTO> cityDTOS = response.getBody();
            assert cityDTOS != null;
            for (CityDTO cityDTO : cityDTOS) {
                cityBox.addItem(cityDTO.name());
                cityBox2.addItem(cityDTO.name());
            }
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchCreateDelivery() {
        String productName = productNameField.getText();
        String details = detailsField.getText();
        String fromCity = fromCityComboBox.getItemAt(fromCityComboBox.getSelectedIndex());
        String toCity = toCityComboBox.getItemAt(toCityComboBox.getSelectedIndex());
        double weight = (double) weightSpinner.getValue();
        double totalPrice = (double) priceSpinner.getValue();

        try {
            DeliveryService service = new DeliveryService();
            ResponseEntity<DeliveryRequestDTO> response = service.requestDelivery(new DeliveryRequestDTO(
                    fromCity,
                    toCity,
                    weight,
                    totalPrice,
                    details,
                    productName
            ));
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
