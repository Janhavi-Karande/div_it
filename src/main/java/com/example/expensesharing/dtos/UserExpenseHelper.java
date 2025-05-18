package com.example.expensesharing.dtos;

import com.example.expensesharing.models.UserExpenseType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserExpenseHelper {
    private String userEmail;
    private UserExpenseType userExpenseType;
    private Double amount;

    public UserExpenseHelper(String userEmail, UserExpenseType userExpenseType, Double amount) {
        this.userEmail = userEmail;
        this.userExpenseType = userExpenseType;
        this.amount = amount;
    }
}
