package com.alumni.exception;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */

public class HostNotFoundException extends AlumniProcessorException {

    public HostNotFoundException() {
    }

    public HostNotFoundException(String msg) {
        super(msg);
    }
}
