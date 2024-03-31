package com.tuvarna.delivery.gui.utils;

import com.tuvarna.delivery.gui.panel.AdminPanel;
import com.tuvarna.delivery.gui.panel.DeliveryPanel;
import com.tuvarna.delivery.gui.panel.UserDeliveryPanel;
import com.tuvarna.delivery.gui.service.UserService;
import com.tuvarna.delivery.user.model.Role;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.util.Objects;
import java.util.Set;

public class PanelLoader {

    private Set<Role> getUserRoles() {
        try {
            UserService service = new UserService();
            ResponseEntity<UserDTO> response = service.fetchUserInfo();
            return Objects.requireNonNull(response.getBody()).roles();
        } catch (HttpClientErrorException e) {
            System.out.println("Server error:" + e.getResponseBodyAsString());
        }
        return null;
    }

    public void loadPanel() {
        Set<Role> userRoles = getUserRoles();
        assert userRoles != null;
        boolean isAdmin = userRoles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("ROLE_ADMIN"));
        boolean isCourier = userRoles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("ROLE_COURIER"));


        if (isAdmin) {
            AdminPanel adminPanel = new AdminPanel();
            JFrame frame = new JFrame("Admin Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(adminPanel);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
        else if (isCourier) {
            DeliveryPanel deliveryPanel = new DeliveryPanel(false);
            JFrame frame = new JFrame("Manage Deliveries");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(deliveryPanel);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
        else {
            UserDeliveryPanel userDeliveryPanel = new UserDeliveryPanel();
            JFrame frame = new JFrame("My Deliveries");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(userDeliveryPanel);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

}
