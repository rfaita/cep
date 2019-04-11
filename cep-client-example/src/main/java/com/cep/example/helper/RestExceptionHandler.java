package com.cep.example.helper;

import com.cep.shared.dto.EnvelopeDTO;
import com.cep.shared.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<EnvelopeDTO> exceptionHandler(ConstraintViolationException e) {

        List<ErrorDTO> errs = new ArrayList<>(e.getConstraintViolations().size());

        e.getConstraintViolations().stream().forEach((er) -> {
            errs.add(ErrorDTO
                    .builder()
                    .errorCode(er.getPropertyPath().toString())
                    .errorMessage(er.getMessage())
                    .build());
        });

        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>(
                EnvelopeDTO
                        .builder()
                        .errors(errs)
                        .build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    public ResponseEntity<EnvelopeDTO> exceptionHandler(ValidationException e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>(
                EnvelopeDTO
                        .builder()
                        .errors(Arrays.asList(
                                ErrorDTO
                                        .builder()
                                        .errorMessage(e.getMessage())
                                        .build()
                        ))
                        .build(),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<EnvelopeDTO> exceptionHandler(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<>(
                EnvelopeDTO
                        .builder()
                        .errors(Arrays.asList(
                                ErrorDTO
                                        .builder()
                                        .errorMessage(e.getMessage())
                                        .build()
                        ))
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {

        final List<ErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.add(
                    ErrorDTO
                            .builder()
                            .errorCode(error.getField())
                            .errorMessage(error.getDefaultMessage())
                            .build());
        });
        ex.getBindingResult().getGlobalErrors().forEach((error) -> {
            errors.add(
                    ErrorDTO
                            .builder()
                            .errorCode(error.getObjectName())
                            .errorMessage(error.getDefaultMessage())
                            .build());
        });
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                EnvelopeDTO
                        .builder()
                        .errors(errors)
                        .build()
                , HttpStatus.BAD_REQUEST);
    }

}
