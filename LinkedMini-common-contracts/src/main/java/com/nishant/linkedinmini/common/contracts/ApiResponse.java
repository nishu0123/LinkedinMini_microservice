package com.nishant.linkedinmini.common.contracts;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {

    private LocalDateTime timestamp;

    private int status;

    private String message;

    private T data;
}