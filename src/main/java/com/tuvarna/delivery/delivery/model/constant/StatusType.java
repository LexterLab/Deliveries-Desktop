package com.tuvarna.delivery.delivery.model.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatusType {

    SHIPPED("Shipped"),
    SHIPPING("In process of shipping"),
    WAITING("Waiting for a courier to accept");

    private final String name;
}
