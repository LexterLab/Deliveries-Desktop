package com.tuvarna.delivery.gui.model;

import com.tuvarna.delivery.office.payload.response.CourierDTO;
import lombok.RequiredArgsConstructor;

import javax.swing.table.AbstractTableModel;
import java.util.List;

@RequiredArgsConstructor
public class CourierTableModel extends AbstractTableModel {
    private final List<CourierDTO> couriers;
    private final String[] columnNames = {"First Name", "Last Name", "Office", "phone", "Experience"};
    @Override
    public int getRowCount() {
        return couriers.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CourierDTO courier = couriers.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> courier.firstName();
            case 1 -> courier.lastName();
            case 2 -> courier.officeName();
            case 3 -> courier.workPhoneNumber();
            case 4 -> courier.yearsOfExperience();
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public CourierDTO getCourierAtRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < couriers.size()) {
            return couriers.get(rowIndex);
        }
        return null;
    }
}
