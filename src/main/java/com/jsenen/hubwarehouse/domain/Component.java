package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "components")
@Schema(description = "Component attributes")
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_component")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Component", example ="12")
    private long idComponent;

    @Column(name = "pn_component")
    @Schema(description = "Component partnumber", example ="231-4567AS")
    private String partNumberComponent;

    @Column(name = "description_component")
    @Schema(description = "Component description", example ="WURTH ELEKTRONIK - 173950336 - Convertidor DC/DC, Propósito General,")
    private String descriptionComponent;

    @Column(name = "detail_description")
    @Schema(description = "Component detail", example ="Convertidor DC/DC, Propósito General, 1 Salida, 1.65 W, 3.3 V, 500 mA, MagI3C FDSM Series")
    private String detailDescriptionCmp;

    @Column(name = "amount_component")
    @Schema(description = "Component amount", example ="42")
    private int amountComponent;

    @Column(name = "row_component")
    @Schema(description = "Component warehouse row", example ="B")
    private String rowComponent;

    @Column(name = "column_component")
    @Schema(description = "Component warehouse column", example ="3")
    private String columnComponent;

    @Column(name = "manufacturer_component")
    @Schema(description = "Component manufacturer", example ="3")
    private String manufacturerComponent;

    @Column(name = "img")
    @Schema(description = "Component img", example ="")
    private String image;

    @Column(name = "datasheet")
    @Schema( description = "Component datasheet", example = "")
    private String datasheets;

    // Campos adicionales

    @Column(name = "technical_attributes", length = Integer.MAX_VALUE)
    @Schema(description = "Component Attributes list", example ="")
    private String technicalAttributes;  // Aquí guardamos los atributos técnicos como JSON

    // Si tienes una relación hacia ServiceOrderComponent, asegúrate de usar @JsonBackReference o @JsonIgnore
    @OneToMany(mappedBy = "component")
    @JsonIgnore // Alternativa para evitar serialización recursiva
    private List<ServiceOrderComponent> serviceOrderComponents;

}
