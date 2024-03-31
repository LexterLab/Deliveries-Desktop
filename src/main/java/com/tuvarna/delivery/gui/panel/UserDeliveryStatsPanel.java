package com.tuvarna.delivery.gui.panel;

import com.tuvarna.delivery.delivery.payload.response.DeliveryDTO;
import com.tuvarna.delivery.delivery.payload.response.DeliveryResponse;
import com.tuvarna.delivery.gui.service.UserService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.*;
import java.awt.*;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDeliveryStatsPanel extends JPanel {
    public UserDeliveryStatsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 64, 64));
        setPreferredSize(new Dimension(1200, 500));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(70, 64, 64));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        fetchAndSplitData(dataset);
        JFreeChart chart = ChartFactory.createBarChart(
                "Annual Deliveries Distribution",
                "Month",
                "Deliveries",
                dataset
        );

        chart.getPlot().setBackgroundPaint(new Color(70, 64, 64));
        chart.getLegend().setBackgroundPaint(new Color(70, 64, 64));
        chart.getLegend().setItemPaint(Color.WHITE);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));


        centerPanel.add(chartPanel);

        add(centerPanel, BorderLayout.CENTER);
    }


    public void fetchAndSplitData(DefaultCategoryDataset categoryDataset) {
        try {
            UserService service = new UserService();
            ResponseEntity<DeliveryResponse> response = service.fetchFilteredUserDeliveries(null);
            List<DeliveryDTO> deliveries = Objects.requireNonNull(response.getBody()).deliveries();

            Map<Month, Long> deliveriesPerMonthMap = deliveries.stream()
                    .collect(Collectors.groupingBy(delivery -> delivery.orderedAt().getMonth(), Collectors.counting()));

            for (Month month : Month.values()) {
                long deliveriesCount = deliveriesPerMonthMap.getOrDefault(month, 0L);
                categoryDataset.addValue(deliveriesCount, "Deliveries", month.toString());
            }
        } catch (HttpClientErrorException e) {
            JOptionPane.showMessageDialog(this, "Internal Server Error", "Server Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
