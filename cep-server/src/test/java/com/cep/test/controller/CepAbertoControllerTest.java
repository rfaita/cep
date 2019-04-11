package com.cep.test.controller;

import com.cep.server.controller.CepAbertoController;
import com.cep.server.service.cepaberto.CepAbertoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false, controllers = CepAbertoController.class)
public class CepAbertoControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private CepAbertoService cepAbertoService;

    @Test
    public void build_cep_with_success() throws Exception {

        doNothing().when(cepAbertoService).build();

        mvc.perform(
                get("/api/cepaberto/build")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(cepAbertoService).build();
    }


}
