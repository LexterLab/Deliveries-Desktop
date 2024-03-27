package com.tuvarna.delivery.gui.model;

import com.tuvarna.delivery.user.payload.response.UserDTO;
import lombok.RequiredArgsConstructor;

import javax.swing.table.AbstractTableModel;
import java.util.List;
@RequiredArgsConstructor
public class UserTableModel extends AbstractTableModel {
    private final List<UserDTO> users;
    private final String[] columnNames = {"Username", "firstName", "lastName", "phone", "address"};
    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserDTO user = users.get(rowIndex);
        return switch (columnIndex){
            case 0 -> user.username();
            case 1 -> user.firstName();
            case 2 -> user.lastName();
            case 3 -> user.phoneNumber();
            case 4 -> user.address();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public UserDTO getUserAtRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < users.size()) {
            return users.get(rowIndex);
        }
        return null;
    }
}
