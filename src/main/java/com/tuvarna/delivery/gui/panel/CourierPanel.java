package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.gui.model.CourierTableModel;
import com.tuvarna.delivery.gui.service.CourierService;
import com.tuvarna.delivery.office.payload.response.CourierDTO;
import com.tuvarna.delivery.office.payload.response.CourierResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(70, 64, 64));

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> openAddCourierPanel());

        bottomPanel.add(addButton);

        add(bottomPanel, BorderLayout.SOUTH);

        courierTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        CourierDTO courierDTO = ((CourierTableModel) courierTable.getModel()).getCourierAtRow(row);
                        if (courierDTO != null) {
                            openCourierInfoPanel(courierDTO);
                        }
                    }
                }
            }
        });

        Timer timer = new Timer();
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

    private void openAddCourierPanel() {
        AddCourierPanel addCourierPanel = new AddCourierPanel();

        JDialog dialog = new JDialog();
        dialog.setTitle("Courier Creation");
        dialog.setModal(true);
        dialog.setContentPane(addCourierPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
