package com.example.expensesharing.dtos;

import com.example.expensesharing.models.Expense;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionsInGroupResponseDto {
    private List<Expense> expenses;
    private ResponseStatus responseStatus;
}
