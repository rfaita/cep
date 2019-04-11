package com.cep.example.controller;

import com.cep.client.CepClient;
import com.cep.shared.dto.CepDTO;
import com.cep.shared.dto.EnvelopeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CepTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CepTestController.class);

    @Autowired
    private CepClient cepClient;

    @GetMapping("findByCep")
    public EnvelopeDTO<List<CepDTO>> findByCep() {
        LOGGER.info("findByCep");
        return cepClient.find("13607225");
    }

    @GetMapping("findByLogradouro")
    public EnvelopeDTO<List<CepDTO>> findByLogradouro() {
        LOGGER.info("findByLogradouro");
        return cepClient.find("candido souza de oliveira");
    }

}
