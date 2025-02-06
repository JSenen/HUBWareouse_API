package com.jsenen.hubwarehouse.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long idUser;

    @Column(name = "user_mail")
    @NotBlank
    private String userMail;

    @Column(name = "user_password")
    @NotBlank
    private String passwordUser;

    @Column(name = "user_rol")
    private String rolUser;

}
