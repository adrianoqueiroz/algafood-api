package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseError {
    private String status;
    private OffsetDateTime timestamp;
    private String type;
    private String title;
    private String detail;
    private String userMessage;
    private List<Object> objects;

    @Getter
    @Setter
    @Builder
    public static class Object {
        private String name;
        private String userMessage;
    }
}
