package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ServiceOrders;
import com.jsenen.hubwarehouse.service.ServiceOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "ServiceOrder", description = "This controller contains all the endpoints that can manage service orders")
public class ServiceOrderController {

    private final static Logger logger = LoggerFactory.getLogger(ComponentController.class);
    private final String TAG = "ServiceOrderController";

    @Autowired
    ServiceOrderService serviceOrderService;

    @GetMapping("/so")
    public ResponseEntity<Iterable<ServiceOrders>> getAll() {
        logger.info(" getAllServiceOrders()",TAG);
        return ResponseEntity.ok(serviceOrderService.findAll());
    }

    @PostMapping("/so/addnew")
    public ResponseEntity<ServiceOrders> addComponent(@RequestBody ServiceOrders serviceOrders) {
        // Llamar al servicio para agregar el nuevo componente
        logger.info("Save ServiceOrder " + serviceOrders,TAG);
        ServiceOrders newServiceOrder= serviceOrderService.addNewServiceOrder(serviceOrders);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceOrders);
    }
}
