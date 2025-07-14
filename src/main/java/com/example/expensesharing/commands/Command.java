package com.example.expensesharing.commands;

public interface Command {
    void execute(String input);

    boolean matches(String input);
}
