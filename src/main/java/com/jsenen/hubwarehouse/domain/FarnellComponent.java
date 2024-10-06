package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "farnellcomponents")
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignorar campos desconocidos
public class FarnellComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_farnell")
    private long idComponent;

    @Column(name = "sku")
    private String sku;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "product_url")
    private String productURL;

    @Column(name = "product_status")
    private String productStatus;

    @Column(name = "rohs_status_code")
    private String rohsStatusCode;

    @Column(name = "pack_size")
    private int packSize;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "stock_level")
    private int stockLevel;

    @Column(name = "price")
    private double price;

    // Campos transitorios que no se almacenan en la base de datos
    @Transient
    private List<Price> prices;

    @Transient
    private Image image;

    @Transient
    private List<Attribute> attributes;
}
