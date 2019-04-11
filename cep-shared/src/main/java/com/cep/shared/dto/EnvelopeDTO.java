package com.cep.shared.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvelopeDTO<T> {

    private T data;
    private List<ErrorDTO> errors;

    public void setError(ErrorDTO error) {
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

}

