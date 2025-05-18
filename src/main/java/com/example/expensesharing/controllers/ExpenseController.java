package com.example.expensesharing.controllers;

import com.example.expensesharing.dtos.CreateExpenseRequestDto;
import com.example.expensesharing.dtos.CreateExpenseResponseDto;
import com.example.expensesharing.dtos.ResponseStatus;
import com.example.expensesharing.models.Expense;
import com.example.expensesharing.services.ExpenseService;
import org.springframework.stereotype.Controller;

@Controller
public class ExpenseController {
    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {

        this.expenseService = expenseService;
    }

    public CreateExpenseResponseDto createExpense(CreateExpenseRequestDto createExpenseRequestDto) {
        CreateExpenseResponseDto createExpenseResponseDto = new CreateExpenseResponseDto();
        try{
            Expense expense = expenseService.createExpense(createExpenseRequestDto.getName(), createExpenseRequestDto.getDescription(),
                    createExpenseRequestDto.getTotalAmount(), createExpenseRequestDto.getGroupName(), createExpenseRequestDto.getUserExpenseHelpers(),
                    createExpenseRequestDto.getCreatedByUserEmail());

            createExpenseResponseDto.setExpense(expense);
            createExpenseResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            e.printStackTrace();
            createExpenseResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return createExpenseResponseDto;
    }
}
