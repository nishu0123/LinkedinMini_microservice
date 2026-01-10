package com.Nishant.LinkedIn_Mini.UserService.Entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Getter
@Setter
public class UserEntity {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(
            name = "user_name",
            nullable = false
    )
    String userName;
    @Column(
            name = "email",
            nullable = false
    )
    String email;
    @Column(
            name = "password",
            nullable = false
    )
    String password;
}
