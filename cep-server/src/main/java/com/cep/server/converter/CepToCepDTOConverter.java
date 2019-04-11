package com.cep.server.converter;

import com.cep.server.model.Cep;
import com.cep.shared.dto.CepDTO;
import com.cep.shared.dto.LocationDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class CepToCepDTOConverter implements Converter<Cep, CepDTO> {

    @Override
    public CepDTO convert(Cep source) {
        CepDTO ret = CepDTO.builder()
                .cep(source.getCep())
                .bairro(source.getBairro())
                .cidade(source.getCidade())
                .estado(source.getEstado())
                .logradouro(source.getLogradouro())
                .refreshedAt(source.getRefreshedAt())
                .uf(source.getUf())

                .build();

        if (!ObjectUtils.isEmpty(source.getLocation())) {
            ret.setLocation(
                    LocationDTO.builder()
                            .lat(source.getLocation().getLat())
                            .lng(source.getLocation().getLng())
                            .build()
            );
        }

        return ret;
    }
}
