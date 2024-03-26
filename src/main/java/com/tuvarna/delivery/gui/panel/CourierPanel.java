package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.delivery.payload.response.StatusDTO;
import com.tuvarna.delivery.gui.AccessTokenStorage;
import com.tuvarna.delivery.gui.model.CourierDeliveryTableModel;
import com.tuvarna.delivery.gui.service.DeliveryService;
import com.tuvarna.delivery.gui.service.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class CourierPanel extends JPanel {
    private final JComboBox<String> deliveryStatusComboBox;
    private final JTextField searchTextField;
    private final JTable deliveryTable;
    private long selectedStatusId;
    private String selectedUsername;


    public CourierPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(70, 64, 64));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(70, 64, 64));

        searchTextField = new JTextField(20);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSelectedUsername();
                fetchDeliveries(selectedUsername, selectedStatusId);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSelectedUsername();
                fetchDeliveries(selectedUsername, selectedStatusId);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        JLabel searchTextLabel = new JLabel("Search:");
        searchTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchTextLabel.setForeground(Color.WHITE);
        topPanel.add(searchTextLabel);
        topPanel.add(searchTextField);

        String[] statusOptions = {"All"};

        try {
            StatusService statusService = new StatusService();
            ResponseEntity<List<StatusDTO>> response = statusService.fetchAllStatuses();
            List<StatusDTO> statusDTOs = response.getBody();
            assert statusDTOs != null;
            statusOptions = new String[statusDTOs.size() + 1];
            statusOptions[0] = "All";
            for (int i = 0; i < statusDTOs.size(); i++) {
                statusOptions[i + 1] = statusDTOs.get(i).type();
            }
        } catch (HttpClientErrorException e) {
            System.out.println("error");
        }

        deliveryStatusComboBox = new JComboBox<>(statusOptions);
        deliveryStatusComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println(deliveryStatusComboBox.getSelectedIndex());
                if (getSelectedDeliveryStatus() > 0) {
                    selectedStatusId = deliveryStatusComboBox.getSelectedIndex();

                    fetchDeliveries(selectedUsername, selectedStatusId);
                } else {
                    fetchDeliveries(selectedUsername, null);
                }
            }
        });
        JLabel statusTextLabel = new JLabel("Status:");
        statusTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusTextLabel.setForeground(Color.WHITE);
        topPanel.add(statusTextLabel);
        topPanel.add(deliveryStatusComboBox);


        add(topPanel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(70, 64, 64));



        List<DeliveryDTO> deliveries = new ArrayList<>();


        CourierDeliveryTableModel tableModel = new CourierDeliveryTableModel(deliveries);
        deliveryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(deliveryTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);


        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = getBottomPanel();

        add(bottomPanel, BorderLayout.SOUTH);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fetchDeliveries(selectedUsername, selectedStatusId);
            }
        }, 0, 60000);

    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(70, 64, 64));

        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setFont(new Font("Arial", Font.BOLD, 14));
        signOutButton.setBackground(new Color(153, 0, 23));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.addActionListener(e -> {
            AccessTokenStorage.removeAccessToken();
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
        });
        bottomPanel.add(signOutButton);
        return bottomPanel;
    }

    private void fetchDeliveries(String username, Long statusId) {
        try {
            DeliveryService service = new DeliveryService();
            ResponseEntity<DeliveryResponse> response = service.fetchFilterDeliveries(username, statusId);
            setTableData(Objects.requireNonNull(response.getBody()).deliveries());
        } catch (HttpClientErrorException e) {
            System.out.println("Failed to fetch deliveries: " + e.getMessage());
        }
    }
    public int getSelectedDeliveryStatus() {
        return deliveryStatusComboBox.getSelectedIndex();
    }

    private void updateSelectedUsername() {
        selectedUsername = searchTextField.getText();
    }

    public void setTableData(List<DeliveryDTO> deliveries) {
        CourierDeliveryTableModel tableModel = new CourierDeliveryTableModel(deliveries);
        deliveryTable.setModel(tableModel);
    }

}