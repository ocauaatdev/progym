package com.progym.progym.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Usuário já existente!");
    }
}
