package com.tuvarna.delivery.gui.panel;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {
    public AdminPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 64, 64));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        centerPanel.setBackground(new Color(70, 64, 64));

        JButton manageCouriersButton = new JButton("Manage Couriers");
        manageCouriersButton.addActionListener(e -> openCourierPanel());

        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.addActionListener(e -> openUserPanel());

        JButton manageDeliveriesButton = new JButton("Manage Deliveries");
        manageDeliveriesButton.addActionListener(e -> openDeliveryPanel());

        centerPanel.add(manageCouriersButton);
        centerPanel.add(manageUsersButton);
        centerPanel.add(manageDeliveriesButton);

        add(centerPanel, BorderLayout.CENTER);
    }


    private void openCourierPanel() {
        CourierPanel courierPanel = new CourierPanel();

        showDialog(courierPanel, "Courier Information");
    }

    private void openUserPanel() {
        UserPanel userPanel = new UserPanel();

        showDialog(userPanel, "User Information");
    }

    private void openDeliveryPanel() {
        DeliveryPanel deliveryPanel = new DeliveryPanel();

        showDialog(deliveryPanel, "Delivery Information");
    }

    private void showDialog(JPanel panel, String title) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
