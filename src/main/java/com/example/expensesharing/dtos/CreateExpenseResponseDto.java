package com.example.expensesharing.dtos;

import com.example.expensesharing.models.Expense;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExpenseResponseDto {
    private Expense expense;
    private ResponseStatus responseStatus;
}
