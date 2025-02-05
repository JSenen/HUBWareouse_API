package com.jsenen.hubwarehouse.controller;

import com.jsenen.hubwarehouse.domain.User;
import com.jsenen.hubwarehouse.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    

  
    @GetMapping("/user")
    public ResponseEntity<Iterable<User>> getAll(@RequestParam(name = "userMail", defaultValue = "", required = false) String userMail){
        logger.info("UserController getAll()");
        //Comprobar si se ha añadido mail como Request Param
        if (userMail.isEmpty()){
            logger.info("/user getAll() searching ");
            return ResponseEntity.ok(userService.findAll());
        }
        
        return ResponseEntity.ok(userService.searchByMail(userMail));
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody @Valid User user)  {
        logger.info("UserController addUser()" + user);
        User newUser = userService.addOne(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PatchMapping("/user/pass/{iduser}")
    public ResponseEntity<User> updateUserPass( @PathVariable("iduser") long id, @RequestBody User user) {
        User updateUser = userService.updateRolUser(id, user);
        return ResponseEntity.status((HttpStatus.ACCEPTED)).body(updateUser);
    }

}
