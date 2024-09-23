package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComponentController {

    @Autowired
    ComponentService componentService;

    @GetMapping("/components")
    public ResponseEntity<Iterable<Component>> getAll() {
        return ResponseEntity.ok(componentService.findAll());
    }

    @PostMapping("/component")
    public ResponseEntity<Component> addComponent(@RequestBody Component component) {
        // Llamar al servicio para agregar el nuevo componente
        Component componentNew = componentService.addNewComponent(component);
        return ResponseEntity.status(HttpStatus.CREATED).body(componentNew);
    }


}
