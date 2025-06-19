package com.progym.progym.exceptions;

public class IncorrectDoubleException extends RuntimeException{
    public IncorrectDoubleException(){
        super("Preencha corretamente os campos, utilizando um ponto (.) para separar os decimais!");
    }
}
