package com.cep.server.controller;

import com.cep.server.service.cepaberto.CepAbertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cepaberto")
public class CepAbertoController {

    @Autowired
    private CepAbertoService cepAbertoService;

    @RequestMapping(value = "/build", method = RequestMethod.GET)
    public void build() throws Exception {
        cepAbertoService.build();
    }

}
