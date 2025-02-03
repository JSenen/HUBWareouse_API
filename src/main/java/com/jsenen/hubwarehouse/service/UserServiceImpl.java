package com.jsenen.hubwarehouse.service;


import com.jsenen.hubwarehouse.domain.User;
import com.jsenen.hubwarehouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


    @Override
    public Iterable<User> findAll() {
        Iterable<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User addOne(User user) {
        //La contraseña del usuario se encriptara en la base de datos
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String plainPassword = user.getPasswordUser(); //Contraseña texto plano
        String hashedPassword = passwordEncoder.encode(plainPassword); // Contraseña Hash
        user.setPasswordUser(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public List<User> searchByMail(String userMail) {
        List<User> usersList = userRepository.findByUserMail(userMail);
        return  usersList;
    }


}
