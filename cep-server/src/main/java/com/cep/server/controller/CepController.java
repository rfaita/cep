package com.cep.server.controller;

import com.cep.server.model.Cep;
import com.cep.server.service.CepService;
import com.cep.shared.contract.CepContract;
import com.cep.shared.dto.CepDTO;
import com.cep.shared.dto.EnvelopeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/public")
@Slf4j
public class CepController implements CepContract {

    @Autowired
    private CepService cepService;

    @Autowired
    private ConversionService conversionService;

    @Override
    @ApiOperation(value = "Permite realizar a busca de CEP pelo próprio cep ou algum trecho do endereço.")
    public EnvelopeDTO<List<CepDTO>> find(@PathVariable String data) {
        log.info("find: data '{}'", data);

        List<CepDTO> ret = (List<CepDTO>) conversionService.convert(
                cepService.find(data),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Cep.class)),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(CepDTO.class))
        );

        return EnvelopeDTO.<List<CepDTO>>builder()
                .data(ret)
                .build();
    }

}
