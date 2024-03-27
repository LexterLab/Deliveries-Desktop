package com.tuvarna.delivery.gui.model;

import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DeliveryTableModel extends AbstractTableModel {
    private final List<DeliveryDTO> deliveries;
    private final String[] columnNames = {"Number", "Username", "Status", "From", "To", "Weight", "Product", "Total Price", "Ordered At"};

    public DeliveryTableModel(List<DeliveryDTO> deliveries) {
        this.deliveries = deliveries;
    }

    @Override
    public int getRowCount() {
        return deliveries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DeliveryDTO delivery = deliveries.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> delivery.id();
            case 1 -> delivery.username();
            case 2 -> delivery.statusName();
            case 3 -> delivery.fromCityName();
            case 4 -> delivery.toCityName();
            case 5 -> delivery.weightKG();
            case 6 -> delivery.productName();
            case 7 -> delivery.totalPrice();
            case 8 -> delivery.orderedAt();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public DeliveryDTO getDeliveryAtRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < deliveries.size()) {
            return deliveries.get(rowIndex);
        }
        return null;
    }
}