package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.delivery.payload.response.StatusDTO;
import com.tuvarna.delivery.gui.model.DeliveryTableModel;
import com.tuvarna.delivery.gui.service.DeliveryService;
import com.tuvarna.delivery.gui.service.StatusService;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class DeliveryPanel extends JPanel {
    private final JComboBox<String> deliveryStatusComboBox;
    private final JTextField searchTextField;
    private final JTable deliveryTable;
    private long selectedStatusId;
    private String selectedUsername;
    private final boolean isAdmin;


    public DeliveryPanel(boolean isAdmin) {
        this.isAdmin = isAdmin;
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
                if (getSelectedDeliveryStatus() > 0) {
                    selectedStatusId = deliveryStatusComboBox.getSelectedIndex();

                    fetchDeliveries(selectedUsername, selectedStatusId);
                } else {
                    selectedStatusId = 0;
                    fetchDeliveries(selectedUsername, selectedStatusId);
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


        DeliveryTableModel tableModel = new DeliveryTableModel(deliveries);
        deliveryTable = new JTable(tableModel);

        deliveryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        DeliveryDTO deliveryDTO = ((DeliveryTableModel) deliveryTable.getModel()).getDeliveryAtRow(row);
                        if (deliveryDTO != null) {
                            openDeliveryInfoPanel(deliveryDTO);
                        }
                    }
                }
            }
        });
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
        }, 0, 3000);


        fetchDeliveries(null, null);
    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(70, 64, 64));


        JButton statsButton = new JButton("Statistics");
        statsButton.setFont(new Font("Arial", Font.BOLD, 14));
        statsButton.setBackground(new Color(113, 217, 179));
        statsButton.setForeground(Color.WHITE);
        statsButton.setOpaque(true);
        statsButton.setBorderPainted(false);
        statsButton.addActionListener(e -> openDeliveryStatsPanel());
        bottomPanel.add(statsButton);
        return bottomPanel;
    }

    private void fetchDeliveries(String username, Long statusId) {
        try {
            DeliveryService service = new DeliveryService();
            ResponseEntity<DeliveryResponse> response = service.fetchFilterDeliveries(username, statusId);
            setTableData(Objects.requireNonNull(response.getBody()).deliveries());
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public int getSelectedDeliveryStatus() {
        return deliveryStatusComboBox.getSelectedIndex();
    }

    private void updateSelectedUsername() {
        selectedUsername = searchTextField.getText();
    }

    public void setTableData(List<DeliveryDTO> deliveries) {
        DeliveryTableModel tableModel = new DeliveryTableModel(deliveries);
        deliveryTable.setModel(tableModel);
    }

    private void openDeliveryInfoPanel(DeliveryDTO deliveryDTO) {
        DeliveryInfoPanel deliveryInfoPanel = new DeliveryInfoPanel(deliveryDTO, isAdmin);

        JDialog dialog = new JDialog();
        dialog.setTitle("Delivery Information");
        dialog.setModal(true);
        dialog.setContentPane(deliveryInfoPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openDeliveryStatsPanel() {
        DeliveryStatsPanel deliveryStatsPanel = new DeliveryStatsPanel();

        JDialog dialog = new JDialog();
        dialog.setTitle("Delivery Analytics");
        dialog.setModal(true);
        dialog.setContentPane(deliveryStatsPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

}