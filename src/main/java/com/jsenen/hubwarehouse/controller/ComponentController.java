package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.FarnellComponent;
import com.jsenen.hubwarehouse.exception.EntityNotFound;
import com.jsenen.hubwarehouse.service.ComponentService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ComponentController {

    private final static Logger logger = LoggerFactory.getLogger(ComponentController.class);
    private final String TAG = "ComponentController";

    @Autowired
    ComponentService componentService;


    @GetMapping("/components")
    public ResponseEntity<Iterable<Component>> getAll() {
        logger.info(" gelAllComponents()",TAG);
        return ResponseEntity.ok(componentService.findAll());
    }

    @GetMapping("/farnell/{productNumber}")
    public ResponseEntity<FarnellComponent> getFarnell(@PathVariable("productNumber") String productNumber) {
        logger.info("Searching in Farnell API for part number: " + productNumber);
        FarnellComponent component = componentService.searchPNumberFarnell(productNumber);
        if (component != null) {
            return ResponseEntity.ok(component);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/component/edit/{idComponent}")
    public ResponseEntity<Component> editComponent (@PathVariable("idComponent") long id, @RequestBody Component component) throws EntityNotFound {
        Component componentToEdit = componentService.updateComponent(id, component);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(componentToEdit);
    }

    @GetMapping("/components/search/{partNumber}")
    public ResponseEntity<Component> searchComponet(@PathVariable("partNumber") String partNumber) {
        logger.info(" searchComponetByPartNumber: " + partNumber,TAG);
        Optional<Component> searchPart = componentService.findByPartNumber(partNumber);

        if (searchPart.isPresent()) {
            return ResponseEntity.ok(searchPart.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devolver 404 si no se encuentra
        }
    }

    @GetMapping("/component/search/{IdComponent}")
    public ResponseEntity<Component> searchComponentById(@PathVariable("IdComponent") String idComponent) {
        logger.info(" searchComponetById: " + idComponent,TAG);
        long idLong = Long.parseLong(idComponent);
        Optional<Component> searchPart = componentService.findById(idLong);

        if (searchPart.isPresent()) {
            return ResponseEntity.ok(searchPart.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devolver 404 si no se encuentra
        }
    }

    @PostMapping("/component")
    public ResponseEntity<Component> addComponent(@RequestBody Component component) {
        // Llamar al servicio para agregar el nuevo componente
        Component componentNew = componentService.addNewComponent(component);
        return ResponseEntity.status(HttpStatus.CREATED).body(componentNew);
    }
    @PostMapping("/component/addnew")
    public ResponseEntity<Component> addNewComponent(@RequestBody Component component) {
        logger.info(" Save new component from webapp: " + component,TAG);
        // Llamar al servicio para agregar el nuevo componente
        Component componentNew = componentService.addNewComponentFromWeb(component);
        return ResponseEntity.status(HttpStatus.CREATED).body(componentNew);
    }


    @DeleteMapping("components/{idComponent}")
    public ResponseEntity<Void> delDepartment(@PathVariable("idComponent") long id) {

        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }

}
