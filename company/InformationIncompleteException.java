package com.company;

public class InformationIncompleteException extends Exception {
    public InformationIncompleteException(String errorMessage) {
        super(errorMessage);
    }
}