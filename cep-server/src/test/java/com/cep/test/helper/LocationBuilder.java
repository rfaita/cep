package com.cep.test.helper;

import com.cep.server.model.Location;

public class LocationBuilder {

    public static Location createBasicLocation() {
        return Location.builder().lat(1d).lng(2d).build();
    }
}
