package com.jsenen.hubwarehouse.repository;

import com.jsenen.hubwarehouse.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Iterable<User> findAll();

    List<User> findByUserMail(String userMail);
}
