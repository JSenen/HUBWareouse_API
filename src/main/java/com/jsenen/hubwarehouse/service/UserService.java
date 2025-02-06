package com.jsenen.hubwarehouse.service;

import com.jsenen.hubwarehouse.domain.Component;
import com.jsenen.hubwarehouse.domain.User;

import java.util.List;

public interface UserService {

    Iterable<User> findAll();
    User addOne(User user);
    List<User> searchByMail(String userMail);

    User updateRolUser(long id, User user);

    boolean emailExists(String email);
}
