package com.example.expensesharing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "users_expenses")
public class UserExpense extends BaseModel{
    @ManyToOne
    private User user;

    @ManyToOne
    private Expense expense;

    @Enumerated(EnumType.ORDINAL)
    private UserExpenseType userExpenseType;
    private Double amount;

}
