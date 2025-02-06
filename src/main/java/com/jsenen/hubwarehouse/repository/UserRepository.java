package com.jsenen.hubwarehouse.repository;

import com.jsenen.hubwarehouse.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findAll();

    List<User> findByUserMail(String userMail);

    @Query("SELECT u FROM User u WHERE u.userMail = :email") // ✅ Usa el nombre correcto de la entidad
    Optional<User> findByUserIntroMail(@Param("email") String email); // ✅ Cambia el nombre para que coincida
}
