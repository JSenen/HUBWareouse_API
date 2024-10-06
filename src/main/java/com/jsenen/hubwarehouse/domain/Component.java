package com.jsenen.hubwarehouse.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "components")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_component")
    private long idComponent;

    @Column(name = "pn_component")
    private String partNumberComponent;

    @Column(name = "description_component")
    private String descriptionComponent;

    @Column(name = "detail_description")
    private String detailDescriptionCmp;

    @Column(name = "amount_component")
    private int amountComponent;

    @Column(name = "row_component")
    private String rowComponent;

    @Column(name = "column_component")
    private String columnComponent;

    @Column(name = "manufacturer_component")
    private String manufacturerComponent;

    // Campos adicionales

    @Column(name = "technical_attributes", length = Integer.MAX_VALUE)
    private String technicalAttributes;  // Aquí guardamos los atributos técnicos como JSON
}
