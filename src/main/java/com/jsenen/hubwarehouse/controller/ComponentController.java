package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.FarnellComponent;
import com.jsenen.hubwarehouse.exception.EntityNotFound;
import com.jsenen.hubwarehouse.service.ComponentService;
import com.jsenen.hubwarehouse.service.DigikeyService;
import com.jsenen.hubwarehouse.service.OAuth2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Component", description = "This controller contains all the endpoints that can manage components")
public class ComponentController {

    private final static Logger logger = LoggerFactory.getLogger(ComponentController.class);
    private final String TAG = "ComponentController";

    @Autowired
    ComponentService componentService;

    @Autowired
    DigikeyService digikeyService;


    @Operation(
            summary = "Retrieve all components on data base",
            description = "Retrieve all components on data base.",
            tags = { "Component"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })
    @GetMapping("/components")
    public ResponseEntity<Iterable<Component>> getAll() {
        logger.info(" gelAllComponents()",TAG);
        return ResponseEntity.ok(componentService.findAll());
    }

    @Operation(
            summary = "Retrieve a Farnell component by Product Number ",
            description = "Retrieve a Farnell component by Product Number ",
            tags = { "Component"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = FarnellComponent.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })

    @GetMapping("/farnell/{productNumber}")
    public ResponseEntity<FarnellComponent> getFarnell(@Parameter(description = "Partnumber of component") @PathVariable("productNumber") String productNumber) {
        logger.info("Searching in Farnell API for part number: " + productNumber);
        FarnellComponent component = componentService.searchPNumberFarnell(productNumber);
        if (component != null) {
            return ResponseEntity.ok(component);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Retrieve a DigiKey component by Product Number ",
            description = "Retrieve a DigiKey component by Product Number ",
            tags = { "Component"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content),
    })
    @GetMapping("/digikey/{productNumber}")
    public ResponseEntity<Component> getComponentFromDigikey(@PathVariable String productNumber) {

        logger.info("Searching in DigiKey API for part number: " + productNumber);
        Component component = digikeyService.getComponentData(productNumber);

        if (component != null) {
            return ResponseEntity.ok(component);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "Update component by ID ",
            description = "Retrieve component by ID  ",
            tags = { "Component"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })
    @PutMapping("/component/edit/{idComponent}")
    @CrossOrigin(origins = "http://localhost")
    public ResponseEntity<Component> editComponent (@Parameter(description = "Id of Component") @PathVariable("idComponent") long id, @RequestBody Component component) throws EntityNotFound {
        logger.info("Patch component id:" + id + "and component" + component);
        Component componentToEdit = componentService.updateComponent(id, component);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(componentToEdit);
    }

    @Operation(
            summary = "Search component by partnumber",
            description = "Search component by partnumber",
            tags = {"Component"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })
    @GetMapping("/components/search/{partNumber}")
    public ResponseEntity<Component> searchComponet(@Parameter(description = "Partnumber") @PathVariable("partNumber") String partNumber) {
        logger.info(" searchComponetByPartNumber: " + partNumber,TAG);
        Optional<Component> searchPart = componentService.findByPartNumber(partNumber);

        if (searchPart.isPresent()) {
            return ResponseEntity.ok(searchPart.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Devolver 404 si no se encuentra
        }
    }

    @Operation(
            summary = "Search component by ID",
            description = "Search component by ID",
            tags = {"Component"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })
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

    @Operation(
            summary = "Add new component from DigiKey API",
            description = "Add new component from DigiKey API",
            tags = {"Component"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })
    @PostMapping("/component")
    public ResponseEntity<Component> addComponent(@RequestBody Component component) {
        // Llamar al servicio para agregar el nuevo componente
        logger.info("Save component " + component,TAG);
        Component componentNew = componentService.addNewComponent(component);
        return ResponseEntity.status(HttpStatus.CREATED).body(componentNew);
    }

    @Operation(
            summary = "Add new component from WebApp",
            description = "Add new component from WebApp",
            tags = {"Component"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })
    @PostMapping("/component/addnew")
    public ResponseEntity<Component> addNewComponent(@RequestBody Component component) {
        logger.info(" Add new component: " + component,TAG);
        // Llamar al servicio para agregar el nuevo componente
        Component componentNew = componentService.addNewComponentFromWeb(component);
        return ResponseEntity.status(HttpStatus.CREATED).body(componentNew);
    }

    @Operation(
            summary = "Delete component",
            description = "Delete component search by ID",
            tags = {"Component"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Component.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid",
                    content = @Content),
    })
    @DeleteMapping("components/{idComponent}")
    public ResponseEntity<Void> delDepartment(@Parameter(description = "Id component") @PathVariable("idComponent") long id) {
        logger.info("Delete component ID: " + id);
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }

}
