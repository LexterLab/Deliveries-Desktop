package com.tuvarna.delivery.delivery.model;

import com.tuvarna.delivery.city.model.City;
import com.tuvarna.delivery.office.model.Courier;
import com.tuvarna.delivery.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_city_id")
    private City fromCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_cityid")
    private City toCity;

    @Column(nullable = false)
    private Double weightKG;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double totalPrice;

    private String details;

    @CreationTimestamp
    private LocalDateTime orderedAt;

    private LocalDateTime deliveredAt;
}
