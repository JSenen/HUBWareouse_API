package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.FarnellComponent;
import com.jsenen.hubwarehouse.service.FarnellQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FarnellQueryController {

    @Autowired
    FarnellQueryService farnellQueryService;

    @GetMapping("/farnell/{partnumber}")
    public ResponseEntity<FarnellComponent> getFarnellComponent(@PathVariable String partnumber) {
        System.out.println("Part number received: " + partnumber); // Añadir log
        FarnellComponent component = farnellQueryService.findOne(partnumber);
        if (component != null) {
            System.out.println("Component found: " + component); // Añadir log
        } else {
            System.out.println("Component not found for part number: " + partnumber); // Añadir log
        }
        return ResponseEntity.ok(component);
    }
}
