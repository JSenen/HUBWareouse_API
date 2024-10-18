package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ServiceOrders;
import com.jsenen.hubwarehouse.service.ServiceOrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/so")
@Tag(name = "ServiceOrder", description = "This controller contains all the endpoints that can manage service orders")
public class ServiceOrderController {

    private final static Logger logger = LoggerFactory.getLogger(ComponentController.class);
    private final String TAG = "ServiceOrderController";

    @Autowired
    ServiceOrderService serviceOrderService;

    @GetMapping("")
    public ResponseEntity<Iterable<ServiceOrders>> getAll() {
        logger.info(" getAllServiceOrders()",TAG);
        return ResponseEntity.ok(serviceOrderService.findAll());
    }

    @PostMapping("/addnew")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<ServiceOrders> addComponent(@RequestBody ServiceOrders serviceOrders) {
        logger.info("Fecha de inicio: " + serviceOrders.getDateStart());
        logger.info("Fecha de fin: " + serviceOrders.getDateFinish());

        ServiceOrders newServiceOrder = serviceOrderService.addNewServiceOrder(serviceOrders);
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOrder);
    }

    @DeleteMapping("/delete/{idServiceOrder}")
    public ResponseEntity<Void> delServiceOrder(@PathVariable("idServiceOrder") long id) {
        logger.info("Delete component ID: " + id);
        serviceOrderService.deleteServiceOrder(id);
        return ResponseEntity.noContent().build();
    }
}
