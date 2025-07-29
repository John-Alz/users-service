package com.micorservice.users.infrastructure.feign.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micorservice.users.infrastructure.dto.ErrorResponse;
import com.micorservice.users.infrastructure.exception.NoDataFoundException;
import com.micorservice.users.infrastructure.exception.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        String defaultMessage = "Ha ocurrido un error en el servidor.";

        try {
            String body = new String(response.body().asInputStream().readAllBytes());
            ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);
            String message = errorResponse.message() != null ? errorResponse.message() : defaultMessage;
            return switch (HttpStatus.valueOf(response.status())) {
                case NOT_FOUND -> new NoDataFoundException(message);
                case UNAUTHORIZED, FORBIDDEN -> new UnauthorizedException(message);
                case BAD_REQUEST -> new IllegalArgumentException(message);
                default -> new RuntimeException(message);
            };
        } catch (IOException e) {
            return defaultDecoder.decode(s, response);
        }

    }

}
