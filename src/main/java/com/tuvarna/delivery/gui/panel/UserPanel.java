package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.gui.model.UserTableModel;
import com.tuvarna.delivery.gui.service.UserService;
import com.tuvarna.delivery.user.payload.response.UserDTO;
import com.tuvarna.delivery.user.payload.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class UserPanel extends JPanel {
    private final JTable userTable;
    public UserPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 64, 64));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(70, 64, 64));

        List<UserDTO> users = new ArrayList<>();

        UserTableModel tableModel = new UserTableModel(users);
        userTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(userTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);



        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        UserDTO userDTO = ((UserTableModel) userTable.getModel()).getUserAtRow(row);
                        if (userDTO != null) {
                            openUserInfoPanel(userDTO);
                        }
                    }
                }
            }
        });

        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fetchUsers();
            }
        }, 0, 3000);

    }



    private void fetchUsers() {
        try {
            UserService service = new UserService();
            ResponseEntity<UserResponseDTO> response = service.fetAllUsers();
            setTableData(Objects.requireNonNull(Objects.requireNonNull(response.getBody()).users()));
        } catch (HttpClientErrorException e) {
            JOptionPane.showMessageDialog(this, "Internal Server Error", "Server Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setTableData(List<UserDTO> users) {
        UserTableModel tableModel = new UserTableModel(users);
        userTable.setModel(tableModel);
    }

    private void openUserInfoPanel(UserDTO userDTO) {
        UserInfoPanel userInfoPanel = new UserInfoPanel(userDTO);

        JDialog dialog = new JDialog();
        dialog.setTitle("User Information");
        dialog.setModal(true);
        dialog.setContentPane(userInfoPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
