package com.cep.test.service;

import com.cep.server.model.Cep;
import com.cep.server.repository.CepRepository;
import com.cep.server.service.CepGeoService;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.cep.test.helper.CepBuilder.createCompleteCep;
import static com.cep.test.helper.CepBuilder.createDefaultCepAnswer;
import static com.cep.test.helper.LocationBuilder.createBasicLocation;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CepGeoServiceTest {

    @TestConfiguration
    static class CepGeoServiceTestContextConfiguration {

        @Bean
        public CepGeoService cepGeoService() {
            return new CepGeoService();
        }

    }

    @Autowired
    private CepGeoService cepGeoService;

    @MockBean
    private CepRepository repository;

    @MockBean
    private GeoService geoService;


    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Before
    public void setUp() {

        given(repository.save(any(Cep.class))).willAnswer(createDefaultCepAnswer());

        given(geoService.findLatLongByIndex(any(String.class))).willReturn(createBasicLocation());

    }

    @Test
    public void test_success_compute_lat_long() throws Exception {


        List<Cep> ceps =
                cepGeoService.computeLocation("teste",
                        Arrays.asList(createCompleteCep("123"), createCompleteCep("124")));

        Assert.assertEquals(2, ceps.size());

        Assert.assertEquals("123", ceps.get(0).getCep());
        Assert.assertEquals(new Double(1.0d), ceps.get(0).getLocation().getLat());
        Assert.assertEquals(new Double(2.0d), ceps.get(0).getLocation().getLng());

        Assert.assertEquals("124", ceps.get(1).getCep());
        Assert.assertEquals(new Double(1.0d), ceps.get(1).getLocation().getLat());
        Assert.assertEquals(new Double(2.0d), ceps.get(1).getLocation().getLng());

        verify(geoService, atLeast(2)).findLatLongByIndex(any(String.class));


    }

}
