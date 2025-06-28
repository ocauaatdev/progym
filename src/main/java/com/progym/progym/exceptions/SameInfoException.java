package com.progym.progym.exceptions;

public class SameInfoException extends RuntimeException{
    public SameInfoException(){
        super("As informações fornecidas são as mesmas que as já existentes.");
    }
}
