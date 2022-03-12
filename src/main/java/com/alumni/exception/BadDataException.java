package com.alumni.exception;

public class BadDataException extends AlumniProcessorException

    /**
     *
     * Name: Guilherme Maciel.
     * Creation Date: 2022-03-11
     * Last Update: 2022-03-12
     *
     * Exception class for data related errors.
     *
     */

{
    public BadDataException(String msg) {
        super(msg);
    }
}
