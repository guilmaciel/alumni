package com.alumni.exception;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */

public class BadFileException extends AlumniProcessorException{

    public BadFileException() {
    }

    public BadFileException(String msg) {
        super(msg);
    }
}
