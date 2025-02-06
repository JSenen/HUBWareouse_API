package com.jsenen.hubwarehouse.service;


import com.jsenen.hubwarehouse.domain.User;
import com.jsenen.hubwarehouse.exception.EntityNotFound;
import com.jsenen.hubwarehouse.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;


    @Override
    public Iterable<User> findAll() {
        Iterable<User> userList = userRepository.findAll();
        logger.info("User Service findAll users --> " + userList);
        return userList;
    }

    @Override
    public User addOne(User user) {
        //La contraseña del usuario se encriptara en la base de datos
        /*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String plainPassword = user.getPasswordUser(); //Contraseña texto plano
        String hashedPassword = passwordEncoder.encode(plainPassword); // Contraseña Hash
       */
        user.setPasswordUser(user.getPasswordUser());
        return userRepository.save(user);
    }

    @Override
    public List<User> searchByMail(String userMail) {
        List<User> usersList = userRepository.findByUserMail(userMail);
        logger.info("User Service IMPL searchByMail --> " + userMail);
        return  usersList;
    }

    @Override
    public User updateRolUser(long id, User user) {

        User modUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("El usuario no se encuentra"));

        //La contraseña del usuario se encriptara en la base de datos
        /* BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String plainPassword = user.getPasswordUser(); //Contraseña texto plano
        String hashedPassword = passwordEncoder.encode(plainPassword); */
        modUser.setPasswordUser(user.getPasswordUser());
        userRepository.save(modUser);
        return null;
    }

    @Override
    public boolean emailExists(String email) {
        logger.info("Check if email exists: " + email);
        return userRepository.findByUserIntroMail(email).isPresent();
    }

    @Override
    public void deleteUser(long id) {
        logger.info("Delete user Id " + id);
        userRepository.deleteById(id);
    }


}
