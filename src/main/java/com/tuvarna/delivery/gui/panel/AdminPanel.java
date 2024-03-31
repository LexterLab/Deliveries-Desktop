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
        manageCouriersButton.setFont(new Font("Arial", Font.BOLD, 14));
        manageCouriersButton.setBackground(new Color(112, 103, 255));
        manageCouriersButton.setForeground(Color.WHITE);
        manageCouriersButton.setOpaque(true);
        manageCouriersButton.setBorderPainted(false);

        JButton manageUsersButton = new JButton("Manage Users");
        manageUsersButton.addActionListener(e -> openUserPanel());
        manageUsersButton.setFont(new Font("Arial", Font.BOLD, 14));
        manageUsersButton.setBackground(new Color(255, 173, 106));
        manageUsersButton.setForeground(Color.WHITE);
        manageUsersButton.setOpaque(true);
        manageUsersButton.setBorderPainted(false);

        JButton manageDeliveriesButton = new JButton("Manage Deliveries");
        manageDeliveriesButton.addActionListener(e -> openDeliveryPanel());
        manageDeliveriesButton.setFont(new Font("Arial", Font.BOLD, 14));
        manageDeliveriesButton.setBackground(new Color(43, 140, 3));
        manageDeliveriesButton.setForeground(Color.WHITE);
        manageDeliveriesButton.setOpaque(true);
        manageDeliveriesButton.setBorderPainted(false);

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
        DeliveryPanel deliveryPanel = new DeliveryPanel(true);

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
