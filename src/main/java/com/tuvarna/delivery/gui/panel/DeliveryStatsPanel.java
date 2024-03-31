package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.delivery.model.constant.StatusType;
import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.gui.service.DeliveryService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DeliveryStatsPanel extends JPanel {
    public DeliveryStatsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 64, 64));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(70, 64, 64));

        add(centerPanel, BorderLayout.CENTER);

        fetchDeliveries();
    }


    private void fetchDeliveries() {
        Map<String, Integer> deliveryStatusCounts = getDeliveryStatusCounts();

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : deliveryStatusCounts.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }


        JFreeChart chart = ChartFactory.createPieChart(
                "Delivery Status Distribution",
                dataset,
                true,
                true,
                false);


        chart.setBackgroundPaint(new Color(70, 64, 64));
        chart.getPlot().setBackgroundPaint(new Color(70, 64, 64));
        chart.getLegend().setBackgroundPaint(new Color(70, 64, 64));
        chart.getLegend().setItemPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.WHITE);


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(new Color(70, 64, 64));


        JPanel centerPanel = (JPanel) this.getComponent(0);
        centerPanel.add(chartPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private Map<String, Integer> getDeliveryStatusCounts() {
        long waitingCount = 0;
        long shippedCount = 0;
        long shippingCount = 0;
        DeliveryService deliveryService = new DeliveryService();
        try {
            ResponseEntity<DeliveryResponse> response = deliveryService
                    .fetchFilterDeliveries(null,  null);
            List<DeliveryDTO> deliveries = Objects.requireNonNull(response.getBody()).deliveries();
            waitingCount = deliveries.stream()
                    .filter(delivery -> delivery.statusName().equalsIgnoreCase(StatusType.WAITING.getName()))
                    .count();
            shippedCount = deliveries.stream()
                    .filter(delivery -> delivery.statusName().equalsIgnoreCase(StatusType.SHIPPED.getName()))
                    .count();
            shippingCount = deliveries.stream()
                    .filter(delivery -> delivery.statusName().equalsIgnoreCase(StatusType.SHIPPING.getName()))
                    .count();
        } catch (HttpClientErrorException e) {
            JOptionPane.showMessageDialog(this, "Internal Server Error", "Server Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return Map.of("Waiting", Math.toIntExact(waitingCount), "Shipped", Math.toIntExact(shippedCount),
                "Shipping", Math.toIntExact(shippingCount));
    }
}
