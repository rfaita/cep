package com.cep.test.controller;

import com.cep.server.controller.CepController;
import com.cep.server.service.CepService;
import com.cep.shared.dto.CepDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.cep.test.helper.CepBuilder.createBasicCepAnswer;
import static com.cep.test.helper.CepDTOBuilder.createBasicCepDTO;
import static com.cep.test.helper.CepDTOBuilder.createBasicCepDTOAnswer;
import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false, controllers = CepController.class)
public class CepControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CepService cepService;


    @Test
    public void consulta_cep_param_number_with_success() throws Exception {

        CepDTO cep = createBasicCepDTO("12345678");

        given(cepService.find(any(String.class))).willAnswer(createBasicCepAnswer());

        mvc.perform(
                get(format("/api/public/cep/%s", cep.getCep()))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].cep", is(cep.getCep())));

        verify(cepService).find("12345678");
    }

    @Test
    public void consulta_cep_param_string_with_success() throws Exception {

        CepDTO cep = createBasicCepDTO("rafael teste");

        given(cepService.find(any(String.class))).willAnswer(createBasicCepAnswer());

        mvc.perform(
                get(format("/api/public/cep/%s", cep.getCep()))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].cep", is(cep.getCep())));

        verify(cepService).find("rafael teste");
    }

}
