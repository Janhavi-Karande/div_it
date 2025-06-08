package com.example.expensesharing.controllers;

import com.example.expensesharing.dtos.*;
import com.example.expensesharing.models.Expense;
import com.example.expensesharing.services.ExpenseService;
import org.springframework.stereotype.Controller;

import java.util.List;

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

    public SettleUpGroupResponseDto settleUp(SettleUpGroupRequestDto settleUpGroupRequestDto) {
        SettleUpGroupResponseDto settleUpResponseDto = new SettleUpGroupResponseDto();

        try{
            List<Expense> expenses = expenseService.settleUp(settleUpGroupRequestDto.getGroupName(),
                    settleUpGroupRequestDto.getUserId());

            settleUpResponseDto.setSettledExpenses(expenses);
            settleUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            settleUpResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return settleUpResponseDto;
    }

    public TransactionsInGroupResponseDto getAllTransactions(TransactionsInGroupRequestDto transactionsInGroupRequestDto) {
        TransactionsInGroupResponseDto transactionsInGroupResponseDto = new TransactionsInGroupResponseDto();

        try{
            List<Expense> settledExpenses = expenseService.getAllTransactions(transactionsInGroupRequestDto.getGroupName());

            transactionsInGroupResponseDto.setExpenses(settledExpenses);
            transactionsInGroupResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            e.printStackTrace();
            transactionsInGroupResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return transactionsInGroupResponseDto;
    }

    public ExpenseHistoryResponseDto getExpenseHistory(ExpenseHistoryRequestDto expenseHistoryRequestDto) {

        ExpenseHistoryResponseDto expenseHistoryResponseDto = new ExpenseHistoryResponseDto();

        try{
            List<Expense> expenses = expenseService.getExpenseHistory(expenseHistoryRequestDto.getGroupName());

            expenseHistoryResponseDto.setExpenses(expenses);
            expenseHistoryResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            e.printStackTrace();
            expenseHistoryResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return expenseHistoryResponseDto;

    }
}
