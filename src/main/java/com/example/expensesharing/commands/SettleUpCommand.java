package com.example.expensesharing.commands;

import com.example.expensesharing.controllers.ExpenseController;
import com.example.expensesharing.dtos.SettleUpGroupRequestDto;
import com.example.expensesharing.dtos.SettleUpGroupResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettleUpCommand implements Command {

    private ExpenseController expenseController;

    public SettleUpCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public void execute(String input) {

        List<String> words = List.of(input.split(" "));

        Integer userId = Integer.valueOf(words.get(0));
        SettleUpGroupRequestDto settleUpGroupRequestDto = new SettleUpGroupRequestDto();

        settleUpGroupRequestDto.setUserId(userId);

        SettleUpGroupResponseDto responseDto = expenseController.settleUp(settleUpGroupRequestDto);

    }

    @Override
    public boolean matches(String input) {

        List<String> words = List.of(input.split(" "));

        return words.size() == 3 && words.get(1).equalsIgnoreCase(CommandKeywords.settleUp);
    }
}
