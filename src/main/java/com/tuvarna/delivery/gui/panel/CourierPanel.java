package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.gui.model.CourierTableModel;
import com.tuvarna.delivery.office.payload.response.CourierDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CourierPanel extends JPanel {
    private final JTable courierTable;

    public CourierPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 64, 64));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(70, 64, 64));

        List<CourierDTO> courier = new ArrayList<>();

        CourierTableModel tableModel = new CourierTableModel(courier);
        courierTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(courierTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fetchCouriers();
            }
        }, 0, 3000);
    }

    private void fetchCouriers() {

    }
}
