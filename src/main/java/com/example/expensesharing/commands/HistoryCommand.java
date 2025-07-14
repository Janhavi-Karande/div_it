package com.example.expensesharing.commands;

import com.example.expensesharing.controllers.ExpenseController;
import com.example.expensesharing.dtos.ExpenseHistoryRequestDto;
import com.example.expensesharing.dtos.ExpenseHistoryResponseDto;
import com.example.expensesharing.dtos.ResponseStatus;
import com.example.expensesharing.models.Expense;
import com.example.expensesharing.models.ExpenseType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryCommand implements Command {

    private ExpenseController expenseController;

    public HistoryCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        String userEmail = words.get(0);
        String groupName = words.get(2);

        ExpenseHistoryRequestDto historyRequestDto = new ExpenseHistoryRequestDto();

        historyRequestDto.setUserEmail(userEmail);
        historyRequestDto.setGroupName(groupName);

        ExpenseHistoryResponseDto historyResponseDto = expenseController.getExpenseHistory(historyRequestDto);
        if(historyResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){

            System.out.println("Expense history is successful.");
            for(Expense expense: historyResponseDto.getExpenses()){
                if(expense.getExpenseType().equals(ExpenseType.REAL)){
                    System.out.println(expense.getName()+ " - Not settle" );
                }
                else if (expense.getExpenseType().equals(ExpenseType.SETTLED)) {
                    System.out.println(expense.getName()+ " - Settled" );

                }
            }
        }
        else{
            System.out.println("Expense history is not successful.");
        }

    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));
        return words.size() == 3 && words.get(1).equalsIgnoreCase(CommandKeywords.history);
    }
}
