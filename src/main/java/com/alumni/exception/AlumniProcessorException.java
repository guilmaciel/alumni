package com.alumni.exception;

/**
 *
 * Name: Guilherme Maciel.
 * Creation Date: 2022-03-11
 * Last Update: 2022-03-11
 *
 */
public class AlumniProcessorException extends RuntimeException{

    String msg;

    public AlumniProcessorException(){
        super("Host not Found");
    }
    public AlumniProcessorException(String msg){
        super(msg);
    }
}
