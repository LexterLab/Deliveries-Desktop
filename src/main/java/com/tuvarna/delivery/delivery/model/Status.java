package com.tuvarna.delivery.delivery.model;

import com.tuvarna.delivery.delivery.model.constant.StatusType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private String type;
    private String information;

    public void setType(StatusType type) {
        this.type = type.getName();
    }
}
