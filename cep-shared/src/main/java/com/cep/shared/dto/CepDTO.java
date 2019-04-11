package com.cep.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CepDTO {

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String uf;
    private LocalDateTime refreshedAt;
    private LocationDTO location;

}
