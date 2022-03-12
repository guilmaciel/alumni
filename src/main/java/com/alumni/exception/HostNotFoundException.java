package com.alumni.exception;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-12
 *
 * Exception class for when the system is not able to find a host.
 */

public class HostNotFoundException extends AlumniProcessorException {

    public HostNotFoundException() {
        this("No information related to the provided host was found.");
    }

    public HostNotFoundException(String msg) {
        super(msg);
    }
}
