package com.example.expensesharing.exceptions;

public class GroupNotFoundException extends Exception {
    public GroupNotFoundException() {}
    public GroupNotFoundException(String message) {
        super(message);
    }
}
