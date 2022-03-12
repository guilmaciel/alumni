package com.alumni.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * Handles the Responses the endpoints will provide in case of any error during any call made.
 *
 */

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Catches an HostNotFoundException thrown and outputs a user friendly error response to the user.
     *
     *
     * @param ex Exception being handled
     * @param request The Original WebRequest
     * @return ResponseEntity that will be returned to the user.
     */
    @ExceptionHandler(value = {HostNotFoundException.class})
    protected ResponseEntity<Object> handleHostNotFoundException(HostNotFoundException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Catches an BadFileException thrown and outputs a user friendly error response to the user.
     *
     *
     * @param ex Exception being handled
     * @param request The Original WebRequest
     * @return ResponseEntity that will be returned to the user.
     */
    @ExceptionHandler(value = {BadFileException.class})
    protected ResponseEntity<Object> handleBadFileException(BadFileException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Catches an BadDataException thrown and outputs a user friendly error response to the user.
     *
     *
     * @param ex Exception being handled
     * @param request The Original WebRequest
     * @return ResponseEntity that will be returned to the user.
     */
    @ExceptionHandler(value = {BadDataException.class})
    protected ResponseEntity<Object> handleBadDataException(BadDataException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Catches an AlumniProcessorException thrown and outputs a user friendly error response to the user.
     *
     *
     * @param ex Exception being handled
     * @param request The Original WebRequest
     * @return ResponseEntity that will be returned to the user.
     */
    @ExceptionHandler(value = {AlumniProcessorException.class})
    protected ResponseEntity<Object> handleAlumniProcessorException(AlumniProcessorException ex, WebRequest request){
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
