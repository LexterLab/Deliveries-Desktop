package com.tuvarna.delivery.courier.model;

import com.tuvarna.delivery.office.model.Office;
import com.tuvarna.delivery.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "couriers")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String workPhoneNumber;
    @Column(nullable = false)
    private Integer yearsOfExperience;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "office_id")
    private Office office;

}
