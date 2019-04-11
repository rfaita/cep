package com.cep.client.config;


import com.cep.client.exception.CustomHystrixBadRequestException;
import com.cep.shared.dto.EnvelopeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.io.IOException;

@Configuration
@EnableFeignClients(basePackages = {
        "com.cep.client"
})
public class CepClientConfig {

    @Component
    public static class CustomErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder delegate = new Default();

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public Exception decode(String methodKey, Response response) {

            EnvelopeDTO<?> data;
            try {
                data = mapper.readValue(response.body().asInputStream(), EnvelopeDTO.class);
            } catch (IOException e) {
                throw new ValidationException("Não foi possível decodificar o erro.", e);
            }

            if (!data.getErrors().isEmpty()) {
                return new CustomHystrixBadRequestException(data.getErrors());
            }

            return delegate.decode(methodKey, response);
        }
    }
}