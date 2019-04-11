package com.cep.shared.contract;

import com.cep.shared.dto.CepDTO;
import com.cep.shared.dto.EnvelopeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CepContract {

    @GetMapping(value = "cep/{data}")
    EnvelopeDTO<List<CepDTO>> find(@PathVariable("data") String data);

}
