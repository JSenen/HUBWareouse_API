package com.jsenen.hubwarehouse.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "service_orders")
@Schema(description = "Service order domain")
public class ServiceOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "so_id")
    private long idServiceOrder;
    @Column(name = "so_number")
    private String orderNumber;
    @Column(name = "so_status")
    private String status;
    @Column(name = "so_datestart")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateStart;
    @Column(name = "so_datafinish")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateFinish;

    @OneToMany(mappedBy = "serviceOrder", cascade = CascadeType.ALL)
    private List<ServiceOrderComponent> serviceOrderComponents; // Relación con la entidad intermedia

}
