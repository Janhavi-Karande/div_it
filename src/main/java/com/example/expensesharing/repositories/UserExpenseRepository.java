package com.example.expensesharing.repositories;

import com.example.expensesharing.models.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {
    UserExpense save(UserExpense userExpense);
}
