package com.example.expensesharing.repositories;

import com.example.expensesharing.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Expense save(Expense expense);
}
