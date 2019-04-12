package com.cep.server.service;

import com.cep.server.model.Cep;
import com.cep.server.repository.CepRepository;
import com.cep.server.service.maps.GeoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
public class CepGeoService {

    @Autowired
    private CepRepository cepRepository;
    @Autowired
    private GeoService geoService;

    @Async("asyncExecutor")
    @Cacheable(value = "cep", key = "#data", condition = "#ceps != null && !#ceps.isEmpty()")
    public List<Cep> computeLocation(String data, List<Cep> ceps) {
        ceps.stream().forEach(cep -> {
            if (ObjectUtils.isEmpty(cep.getLocation())) {
                cep.setLocation(geoService.findLatLongByIndex(
                        cep.getLogradouro().concat(",").concat(cep.getCidade())
                ));
                cepRepository.save(cep);
            } else {
                log.info("CEP already have a location, skipped. {}", cep.getCep());
            }
        });

        return ceps;
    }
}
