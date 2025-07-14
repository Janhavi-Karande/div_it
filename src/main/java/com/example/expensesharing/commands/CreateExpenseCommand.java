package com.example.expensesharing.commands;

import java.util.List;

public class CreateExpenseCommand implements Command {
    @Override
    public void execute(String input) {

    }

    // NOT complete
    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 5 && words.get(0).equalsIgnoreCase(CommandKeywords.createExpense);
    }
}
