package com.Nishant.LinkedIn_Mini.UserService.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshTokenEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;
}