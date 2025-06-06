package com.example.expensesharing.dtos;

import com.example.expensesharing.models.Expense;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpGroupResponseDto {
    private List<Expense> settledExpenses;
    private ResponseStatus responseStatus;
}
