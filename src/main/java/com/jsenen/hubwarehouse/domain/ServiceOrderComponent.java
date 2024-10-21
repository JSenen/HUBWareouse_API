package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "service_order_component")
public class ServiceOrderComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_order_id")
    @JsonBackReference
    private ServiceOrders serviceOrder;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    @Column(name = "quantity")
    private int quantity;
}

