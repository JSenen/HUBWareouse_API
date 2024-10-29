package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.ServiceOrders;
import com.jsenen.hubwarehouse.exception.EntityNotFound;
import com.jsenen.hubwarehouse.service.ServiceOrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/so")
@Tag(name = "ServiceOrder", description = "This controller contains all the endpoints that can manage service orders")
public class ServiceOrderController {

    private final static Logger logger = LoggerFactory.getLogger(ComponentController.class);
    private final String TAG = "ServiceOrderController";

    @Autowired
    ServiceOrderService serviceOrderService;
    @CrossOrigin(origins = "http://localhost")
    @GetMapping("")
    public ResponseEntity<Iterable<ServiceOrders>> getAll() {
        logger.info(" getAllServiceOrders()",TAG);
        return ResponseEntity.ok(serviceOrderService.findAll());
    }
    @CrossOrigin(origins = "http://localhost")
    @GetMapping("/{IdServiceOrder}")
    public ResponseEntity<ServiceOrders> searchSOById(@PathVariable("IdServiceOrder") String idServiceOrder) {
        logger.info(" searchSObyID: " + idServiceOrder,TAG);
        long idLong = Long.parseLong(idServiceOrder);
        Optional<ServiceOrders> searchSO = serviceOrderService.findById(idLong);

        if (searchSO.isPresent()) {
            return ResponseEntity.ok(searchSO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devolver 404 si no se encuentra
        }
    }

    @PostMapping("/addnew")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<ServiceOrders> addComponent(@RequestBody ServiceOrders serviceOrders) {
        logger.info("Fecha de inicio: " + serviceOrders.getDateStart());
        logger.info("Fecha de fin: " + serviceOrders.getDateFinish());

        ServiceOrders newServiceOrder = serviceOrderService.addNewServiceOrder(serviceOrders);
        return ResponseEntity.status(HttpStatus.CREATED).body(newServiceOrder);
    }
    @CrossOrigin(origins = "http://localhost")
    @DeleteMapping("/delete/{idServiceOrder}")
    public ResponseEntity<Void> delServiceOrder(@PathVariable("idServiceOrder") long id) {
        logger.info("Delete component ID: " + id);
        serviceOrderService.deleteServiceOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{idServiceOrder}")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<ServiceOrders> editServiceOrder(@PathVariable("idServiceOrder") long id, @RequestBody ServiceOrders serviceOrders) throws EntityNotFound {
        logger.info("Edit service order id: " + id + " and json: " + serviceOrders);
        ServiceOrders serviceOrdersToEdit = serviceOrderService.updateServiceOrder(id, serviceOrders);
        return ResponseEntity.status(HttpStatus.OK).body(serviceOrdersToEdit);  // Cambiar a 200 OK
    }
    @CrossOrigin(origins = "http://localhost")
    @GetMapping("/component/{idComponent}")
    public ResponseEntity<List<ServiceOrders>> searchSObyComponent(@PathVariable("idComponent") long id) {
        logger.info("Search Service Orders by Component ID " + id);
        List<ServiceOrders> serviceOrders = serviceOrderService.searchByComponentId(id);
        if (!serviceOrders.isEmpty()) {
            return ResponseEntity.ok(serviceOrders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devolver 404 si no se encuentra
        }
    }

}
