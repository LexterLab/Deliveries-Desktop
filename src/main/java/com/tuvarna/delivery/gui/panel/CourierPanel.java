package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.gui.model.CourierTableModel;
import com.tuvarna.delivery.gui.service.CourierService;
import com.tuvarna.delivery.office.payload.response.CourierDTO;
import com.tuvarna.delivery.office.payload.response.CourierResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.*;

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
        try {
            CourierService service = new CourierService();
            ResponseEntity<CourierResponseDTO> response = service.fetchAllCouriers();
            setTableData(Objects.requireNonNull(response.getBody()).couriers());
        } catch (HttpClientErrorException e) {
            JOptionPane.showMessageDialog(this, "Internal Server Error", "Server Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setTableData(List<CourierDTO> couriers) {
        CourierTableModel tableModel = new CourierTableModel(couriers);
        courierTable.setModel(tableModel);
    }

    private void openCourierInfoPanel(CourierDTO courierDTO) {
        CourierInfoPanel courierInfoPanel = new CourierInfoPanel(courierDTO);

        JDialog dialog = new JDialog();
        dialog.setTitle("Courier Information");
        dialog.setModal(true);
        dialog.setContentPane(courierInfoPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
