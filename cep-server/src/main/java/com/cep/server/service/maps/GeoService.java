package com.cep.server.service.maps;

import com.cep.server.model.Location;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
@Slf4j
public class GeoService {

    @Autowired
    private GeoApiContext context;

    public Location findLatLongByIndex(String index) {

        try {
            GeocodingResult[] results = GeocodingApi.newRequest(context).address(index).await();

            if (results != null && results.length > 0) {
                log.info("Location found: {}", results);

                return Location.builder()
                        .lat(results[0].geometry.location.lat)
                        .lng(results[0].geometry.location.lng)
                        .build();
            } else {
                throw new ValidationException("Result not found");
            }
        } catch (Exception e) {
            log.warn("Address not found: {}", e.getMessage());
        }


        return null;

    }

}
