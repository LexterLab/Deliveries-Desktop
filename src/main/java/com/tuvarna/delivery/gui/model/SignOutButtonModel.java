package com.tuvarna.delivery.gui.model;

import javax.swing.*;
import java.awt.*;

public class SignOutButtonModel {
    public JButton getSignOutButton() {
        JButton signOutButton = new JButton("Sign Out");
        signOutButton.setFont(new Font("Arial", Font.BOLD, 14));
        signOutButton.setBackground(new Color(252, 3, 32));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setOpaque(true);
        signOutButton.setBorderPainted(false);
        return signOutButton;
    }
}
