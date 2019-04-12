package com.cep.server.service;

import com.cep.server.dto.CountDTO;
import com.cep.server.model.Cep;
import com.cep.server.repository.CepRepository;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CepService {

    @Value("${cep.maps.enabled}")
    private Boolean geoEnabled;

    @Autowired
    private CepRepository cepRepository;
    @Autowired
    private CepGeoService cepGeoService;

    private Cep findByCep(String cep) {

        return cepRepository.findByCep(cep);
    }

    private List<Cep> findAllByOrderByScoreDesc(String text) {

        TextCriteria crit = TextCriteria
                .forLanguage("pt")
                .caseSensitive(false)
                .matchingAny(text);

        return cepRepository.findAllByOrderByScoreDesc(crit, PageRequest.of(0, 10));
    }

    @Cacheable(value = "cepCount")
    public CountDTO count() {
        return CountDTO.builder().number(cepRepository.count()).build();
    }

    @Cacheable(value = "cep", key = "#data", condition = "false")
    public List<Cep> find(String data) {
        List<Cep> ret = new ArrayList<>();
        if (data.length() <= 3) {
            return ret;
        }
        if (StringUtils.isNumeric(data)) {
            Cep cep = findByCep(data);
            if (!ObjectUtils.isEmpty(cep)) {
                ret.add(cep);
            }
        } else {
            ret.addAll(findAllByOrderByScoreDesc(data));
        }

        if (geoEnabled) {
            cepGeoService.computeLocation(data, ret);
        }

        return ret;
    }



}
