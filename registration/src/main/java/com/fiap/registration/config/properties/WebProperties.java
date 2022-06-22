package com.fiap.registration.config.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WebProperties {

    private CORSConfiguration cors = new CORSConfiguration();

    @Data
    public static class CORSConfiguration {
        private List<String> allowedOrigins = new ArrayList<>();
    }

}
