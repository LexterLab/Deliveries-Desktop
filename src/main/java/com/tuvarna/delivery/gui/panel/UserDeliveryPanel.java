package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.gui.model.DeliveryTableModel;
import com.tuvarna.delivery.gui.service.UserService;
import com.tuvarna.delivery.gui.utils.DateLabelFormatter;
import com.tuvarna.delivery.utils.ErrorFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class UserDeliveryPanel  extends JPanel {
    private final JTable deliveryTable;
    private LocalDate afterDate = null;
    private final UserService userService = new UserService();
    private JDatePickerImpl datePicker;
    public UserDeliveryPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 64, 64));
        setPreferredSize(new Dimension(900, 600));

        JPanel topPanel = getTopPanel();

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(70, 64, 64));

        List<DeliveryDTO> deliveries = new ArrayList<>();

        DeliveryTableModel tableModel = new DeliveryTableModel(deliveries);
        deliveryTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(deliveryTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = getBottomPanel();

        add(bottomPanel, BorderLayout.SOUTH);

        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fetchDeliveries();
            }
        }, 0, 3000);
    }

    private JPanel getTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(70, 64, 64));

        JLabel filterTextLabel = new JLabel(" Filter By:");
        filterTextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        filterTextLabel.setForeground(Color.WHITE);

        JRadioButton fiveDaysRadioButton = new JRadioButton("Past five days");
        JRadioButton afterDateRadioButton = new JRadioButton("After Date");
        fiveDaysRadioButton.setBackground(new Color(70, 64, 64));
        fiveDaysRadioButton.setForeground(Color.WHITE);
        afterDateRadioButton.setBackground(new Color(70, 64, 64));
        afterDateRadioButton.setForeground(Color.WHITE);

        fiveDaysRadioButton.addActionListener(e -> fetchDeliveriesMadeRecentFiveDays(fiveDaysRadioButton));
        afterDateRadioButton.addActionListener(e -> datePicker.setVisible(true));

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(fiveDaysRadioButton);
        radioButtonGroup.add(afterDateRadioButton);

        JButton clearButton = new JButton("Clear filters");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(232, 17, 17));
        clearButton.setForeground(Color.WHITE);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);
        clearButton.addActionListener(e -> clearFilters(radioButtonGroup));

        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setVisible(false);
        model.addChangeListener(e -> fetchDeliveriesPastDate(model));



        topPanel.add(filterTextLabel);
        topPanel.add(fiveDaysRadioButton);
        topPanel.add(afterDateRadioButton);
        topPanel.add(datePicker);
        topPanel.add(clearButton);
        return topPanel;
    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(70, 64, 64));


        JButton statsButton = new JButton("Annual Statistics");
        statsButton.setFont(new Font("Arial", Font.BOLD, 14));
        statsButton.setBackground(new Color(113, 217, 179));
        statsButton.setForeground(Color.WHITE);
        statsButton.setOpaque(true);
        statsButton.setBorderPainted(false);
        statsButton.addActionListener(e -> openUserDeliveryStatsPanel());
        bottomPanel.add(statsButton);

        JButton requestButton = new JButton("Request Delivery");
        requestButton.setFont(new Font("Arial", Font.BOLD, 14));
        requestButton.setBackground(new Color(137, 193, 255));
        requestButton.setForeground(Color.WHITE);
        requestButton.setOpaque(true);
        requestButton.setBorderPainted(false);
        requestButton.addActionListener(e -> openRequestDeliveryPanel());
        bottomPanel.add(requestButton);
        return bottomPanel;
    }

    private void clearFilters(ButtonGroup group) {
        group.clearSelection();
        afterDate = null;
        datePicker.setVisible(false);
        fetchDeliveries();
    }

    private void fetchDeliveries() {
        try {
            ResponseEntity<DeliveryResponse> response = userService.fetchFilteredUserDeliveries(afterDate);
            setTableData(Objects.requireNonNull(response.getBody()).deliveries());
        } catch (HttpClientErrorException e) {
            String errorMessage = e.getResponseBodyAsString();
            JOptionPane.showMessageDialog(this, ErrorFormatter
                    .formatError(errorMessage), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setTableData(List<DeliveryDTO> deliveries) {
        DeliveryTableModel tableModel = new DeliveryTableModel(deliveries);
        deliveryTable.setModel(tableModel);
    }

    private void fetchDeliveriesMadeRecentFiveDays(JRadioButton fiveDaysRadioButton) {
        if (fiveDaysRadioButton.isSelected()) {
            datePicker.setVisible(false);
            afterDate = LocalDate.now().minusDays(5);
            fetchDeliveries();
        }
    }

    private void fetchDeliveriesPastDate(UtilDateModel model) {
       afterDate =  LocalDate.of(model.getYear(), model.getMonth() + 1, model.getDay());
       fetchDeliveries();
    }

    private void openUserDeliveryStatsPanel() {
        UserDeliveryStatsPanel userDeliveryStatsPanel = new UserDeliveryStatsPanel();


        JDialog dialog = new JDialog();
        dialog.setTitle("Delivery Annual Analytics");
        dialog.setModal(true);

        dialog.setContentPane(userDeliveryStatsPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openRequestDeliveryPanel() {
        RequestDeliveryPanel requestDeliveryPanel = new RequestDeliveryPanel();


        JDialog dialog = new JDialog();
        dialog.setTitle("Request Delivery");
        dialog.setModal(true);

        dialog.setContentPane(requestDeliveryPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
