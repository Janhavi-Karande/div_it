package com.example.expensesharing.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity(name = "expenses")
public class Expense extends BaseModel{
    private String name;
    private Double totalAmount;
    private String description;

    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;

    @ManyToOne
    private User createdBy;

    @OneToMany(mappedBy = "expense")
    private List<UserExpense> userExpenseList;

    @ManyToOne
    private Group group;


}
