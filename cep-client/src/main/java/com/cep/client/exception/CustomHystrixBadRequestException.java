package com.cep.client.exception;

import com.cep.shared.dto.ErrorDTO;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;

import java.util.List;

@Getter
public class CustomHystrixBadRequestException extends HystrixBadRequestException {

    private final List<ErrorDTO> errors;

    public CustomHystrixBadRequestException(List<ErrorDTO> errors) {
        super(null);
        this.errors = errors;
    }

    public CustomHystrixBadRequestException(String message, List<ErrorDTO> errors) {
        super(message);
        this.errors = errors;
    }

    public CustomHystrixBadRequestException(String message, Throwable cause, List<ErrorDTO> errors) {
        super(message, cause);
        this.errors = errors;
    }
}
