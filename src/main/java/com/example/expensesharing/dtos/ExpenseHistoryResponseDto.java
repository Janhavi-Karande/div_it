package com.example.expensesharing.dtos;

import com.example.expensesharing.models.Expense;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ExpenseHistoryResponseDto {
    private List<Expense> expenses;
    private ResponseStatus responseStatus;
}
