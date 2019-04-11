package com.cep.test.service;

import com.cep.server.model.Cep;
import com.cep.test.helper.CepBuilder;
import com.cep.server.repository.CepRepository;
import com.cep.server.service.cepaberto.CepAbertoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "cep.cepaberto.diretorio=./src/test/resources/cepaberto",
        "cep.cepaberto.estadoZip=estados.cepaberto.zip",
        "cep.cepaberto.cidadeZip=cidades.cepaberto.zip"
})
public class CepAbertoServiceTest {

    @TestConfiguration
    static class CepAbertoServiceTestContextConfiguration {

        @Bean
        public CepAbertoService CcpAbertoService() {
            return new CepAbertoService();
        }

    }

    @Autowired
    private CepAbertoService cepAbertoService;

    @MockBean
    private CepRepository repository;

    @Before
    public void setUp() {
        doNothing().when(repository).deleteAll();
        given(repository.save(any(Cep.class))).willAnswer(CepBuilder.createDefaultCepAnswer());
    }

    @Test
    public void basic_build_from_cep_aberto() throws Exception {
        cepAbertoService.build();

        ArgumentCaptor<Cep> argument = ArgumentCaptor.forClass(Cep.class);
        verify(repository, atMost(16)).save(argument.capture());

        Assert.assertEquals(16, argument.getAllValues().size());

        int ruaCount = 1;
        int bairroCount = 1;
        int cidadeCount = 1;
        int estadoCount = 1;
        int cidadeInitialCount = 1;
        for (Cep cep : argument.getAllValues()) {
            Assert.assertEquals(format("Rua %d", ruaCount), cep.getLogradouro());
            Assert.assertEquals(format("Bairro %d", bairroCount), cep.getBairro());
            Assert.assertEquals(format("Cidade%d", cidadeCount), cep.getCidade());
            Assert.assertEquals(format("Teste%d", estadoCount), cep.getEstado());
            Assert.assertEquals(format("T%d", estadoCount), cep.getUf());

            if (ruaCount % 2 == 0) {
                cidadeCount++;
            }
            if (bairroCount % 4 == 0) {
                cidadeCount = cidadeInitialCount;
            }
            if (ruaCount % 8 == 0 && bairroCount % 8 == 0) {
                ruaCount = 1;
                bairroCount = 1;
                cidadeInitialCount += 2;
                cidadeCount = cidadeInitialCount;
                estadoCount++;
            } else {
                ruaCount++;
                bairroCount++;
            }

        }
    }


}