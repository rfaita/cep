package com.cep.test.service;

import com.cep.server.model.Cep;
import com.cep.server.repository.CepRepository;
import com.cep.server.service.CepService;
import com.cep.server.service.maps.GeoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.cep.test.helper.CepBuilder.createCompleteCep;
import static com.cep.test.helper.LocationBuilder.createBasicLocation;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "cep.maps.enabled=true"
})
public class CepServiceTest {

    @TestConfiguration
    static class CepServiceTestContextConfiguration {

        @Bean
        public CepService cepService() {
            return new CepService();
        }

    }

    @Autowired
    private CepService cepService;

    @MockBean
    private CepRepository repository;

    @MockBean
    private GeoService geoService;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Before
    public void setUp() {

        given(repository.findByCep("12345678")).willReturn(createCompleteCep("12345678"));
        given(repository.findAllByOrderByScoreDesc(any(TextCriteria.class), any(PageRequest.class)))
                .willReturn(
                        Arrays.asList(
                                createCompleteCep("98765432"),
                                createCompleteCep("98765431")
                        )
                );

        given(geoService.findLatLongByIndex(any(String.class))).willReturn(createBasicLocation());
    }


    @Test
    public void find_cep_with_cep_number_with_success() {

        List<Cep> list = cepService.find("12345678");

        Assert.assertEquals(1, list.size());

        Cep ret = list.get(0);

        Assert.assertTrue(ret != null);
        Assert.assertEquals("bairro 12345678", ret.getBairro());
        Assert.assertEquals("logradouro 12345678", ret.getLogradouro());
        Assert.assertEquals("cidade 12345678", ret.getCidade());
        Assert.assertEquals("estado 12345678", ret.getEstado());
        Assert.assertEquals("uf 12345678", ret.getUf());
        Assert.assertEquals("123 12345678", ret.getId());
        Assert.assertEquals("12345678", ret.getCep());


        Assert.assertEquals(new Double(1d), ret.getLocation().getLat());
        Assert.assertEquals(new Double(2d), ret.getLocation().getLng());

    }

    @Test
    public void find_cep_with_cep_number_not_found() {

        List<Cep> list = cepService.find("12345679");

        Assert.assertEquals(0, list.size());
    }

    @Test
    public void find_cep_with_logradouro_with_success() {

        List<Cep> list = cepService.find("qqqqqq");

        Assert.assertEquals(2, list.size());

        Cep ret = list.get(0);

        Assert.assertTrue(ret != null);
        Assert.assertEquals("bairro 98765432", ret.getBairro());
        Assert.assertEquals("logradouro 98765432", ret.getLogradouro());
        Assert.assertEquals("cidade 98765432", ret.getCidade());
        Assert.assertEquals("estado 98765432", ret.getEstado());
        Assert.assertEquals("uf 98765432", ret.getUf());
        Assert.assertEquals("123 98765432", ret.getId());
        Assert.assertEquals("98765432", ret.getCep());

        Assert.assertEquals(new Double(1d), ret.getLocation().getLat());
        Assert.assertEquals(new Double(2d), ret.getLocation().getLng());
        ret = list.get(1);

        Assert.assertTrue(ret != null);
        Assert.assertEquals("bairro 98765431", ret.getBairro());
        Assert.assertEquals("logradouro 98765431", ret.getLogradouro());
        Assert.assertEquals("cidade 98765431", ret.getCidade());
        Assert.assertEquals("estado 98765431", ret.getEstado());
        Assert.assertEquals("uf 98765431", ret.getUf());
        Assert.assertEquals("123 98765431", ret.getId());
        Assert.assertEquals("98765431", ret.getCep());

        Assert.assertEquals(new Double(1d), ret.getLocation().getLat());
        Assert.assertEquals(new Double(2d), ret.getLocation().getLng());
    }


}


