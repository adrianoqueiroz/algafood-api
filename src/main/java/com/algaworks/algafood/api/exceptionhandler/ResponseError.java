package com.algaworks.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResponseError {
    private LocalDateTime timestamp;
    private int status;
    private String message;
}
