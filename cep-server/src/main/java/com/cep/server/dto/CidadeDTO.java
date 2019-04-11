package com.cep.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CidadeDTO {

    private String id;
    private String cidade;
    private EstadoDTO estado;

}
