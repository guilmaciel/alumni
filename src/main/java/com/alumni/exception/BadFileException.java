package com.alumni.exception;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * Exception class for File related errors
 *
 */

public class BadFileException extends AlumniProcessorException{

    public BadFileException(String msg) {
        super(msg);
    }
}
