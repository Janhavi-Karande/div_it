package com.example.expensesharing.services;

import com.example.expensesharing.dtos.UserExpenseHelper;
import com.example.expensesharing.exceptions.GroupNotFoundException;
import com.example.expensesharing.exceptions.UserNotFoundException;
import com.example.expensesharing.models.*;
import com.example.expensesharing.repositories.ExpenseRepository;
import com.example.expensesharing.repositories.GroupRepository;
import com.example.expensesharing.repositories.UserExpenseRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {
    private final UserExpenseRepository userExpenseRepository;
    private ExpenseRepository expenseRepository;
    private GroupService groupService;
    private GroupRepository groupRepository;
    private UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, GroupService groupService,
                          UserService userService, UserExpenseRepository userExpenseRepository,
                          GroupRepository groupRepository) {
        this.expenseRepository = expenseRepository;
        this.groupService = groupService;
        this.userService = userService;
        this.userExpenseRepository = userExpenseRepository;
        this.groupRepository = groupRepository;
    }

    @Transactional
    public Expense createExpense(String expenseName, String description, Double totalAmount,
                                 String groupName, List<UserExpenseHelper> userExpenseHelpers, String createdByUserEmail) throws GroupNotFoundException, UserNotFoundException {

        // validate Data
        if (expenseName == null || expenseName.isEmpty() || totalAmount == null || totalAmount <= 0 || createdByUserEmail == null || userExpenseHelpers == null || userExpenseHelpers.isEmpty()) {
            throw new IllegalArgumentException("Invalid expense data. Name, totalAmount, createdByUserId, and userExpenseHelpers are required.");
        }

        Expense expense = new Expense();

        // get user object from user service
        User createdByUser = userService.getUser(createdByUserEmail);

        // get group object from group name
        Group group = groupService.getGroup(groupName);

        // set the values for expense object
        expense.setGroup(group);
        expense.setDescription(description);
        expense.setName(expenseName);
        expense.setTotalAmount(totalAmount);
        expense.setCreatedBy(createdByUser);
        expense.setExpenseType(ExpenseType.REAL);
        expenseRepository.save(expense);

        // userExpenseList will email addresses of user those who are involved in the expense who owes money and who receives money
        List<UserExpense> userExpenseList = new ArrayList<>();

        for(UserExpenseHelper userExpenseHelper : userExpenseHelpers) {
            User user = userService.getUser(userExpenseHelper.getUserEmail());
            UserExpense userExpense = new UserExpense();

            userExpense.setUser(user);
            userExpense.setExpense(expense);
            userExpense.setAmount(userExpenseHelper.getAmount());
            userExpense.setUserExpenseType(userExpenseHelper.getUserExpenseType());

            userExpenseRepository.save(userExpense);
            userExpenseList.add(userExpense);

        }

        expense.setUserExpenseList(userExpenseList);
        expense = expenseRepository.save(expense);

        // save expense in the group object
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(expense);
        group.setExpenses(expenseList);
        groupRepository.save(group);

        return expense;
    }
}
