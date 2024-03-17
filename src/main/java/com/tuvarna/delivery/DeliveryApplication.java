package com.tuvarna.delivery;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Delivery API",
        description = "Delivery Endpoints Documentation",
        version = "v1.0",
        contact = @Contact(
                name = "Alexander Parpulansky",
                email = "alexanderparpulansky@gmail.com"
        )))
public class DeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

}
