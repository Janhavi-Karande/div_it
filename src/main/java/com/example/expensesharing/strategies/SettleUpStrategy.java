package com.example.expensesharing.strategies;

import com.example.expensesharing.models.Expense;

import java.util.List;

public interface SettleUpStrategy {

    List<Expense> settleUp(List<Expense> expenses);
}
