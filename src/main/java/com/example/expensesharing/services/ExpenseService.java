package com.example.expensesharing.services;

import com.example.expensesharing.dtos.UserExpenseHelper;
import com.example.expensesharing.exceptions.GroupNotFoundException;
import com.example.expensesharing.exceptions.InvalidGroupMemberException;
import com.example.expensesharing.exceptions.UserNotFoundException;
import com.example.expensesharing.models.*;
import com.example.expensesharing.repositories.ExpenseRepository;
import com.example.expensesharing.repositories.GroupRepository;
import com.example.expensesharing.repositories.UserExpenseRepository;

import com.example.expensesharing.repositories.UserRepository;
import com.example.expensesharing.strategies.HeapSettleUpStrategy;
import com.example.expensesharing.strategies.SettleUpStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final UserExpenseRepository userExpenseRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final UserService userService;
    private final SettleUpStrategy settleUpStrategy;

    public ExpenseService(ExpenseRepository expenseRepository, GroupService groupService,
                          UserService userService, UserExpenseRepository userExpenseRepository,
                          GroupRepository groupRepository, UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.groupService = groupService;
        this.userService = userService;
        this.userExpenseRepository = userExpenseRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.settleUpStrategy = new HeapSettleUpStrategy();
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
        // get list of expenses int the current group
        List<Expense> expenseList = group.getExpenses();
        expenseList.add(expense);
        group.setExpenses(expenseList);
        groupRepository.save(group);

        return expense;
    }

    @Transactional
    public List<Expense> settleUp(Integer groupId, Integer userId) throws GroupNotFoundException, UserNotFoundException, InvalidGroupMemberException {

        // get the group object
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isEmpty()){
            throw new GroupNotFoundException("Group with id " +groupId+ " does not exists!");
        }

        Group group = optionalGroup.get();

        // check if user exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with id " +userId+ " does not exists!");
        }
        User user = optionalUser.get();

        // check if given user is a part of group
        if(!group.getMembers().contains(user)){
            throw new InvalidGroupMemberException(user.getName()+ " is not a member of " +group.getName());
        }

        // get all expenses in the group with REAl expense type
        List<Expense> expenseList = group.getExpenses();

        expenseList = expenseList.stream().
                filter(e -> e.getExpenseType() == ExpenseType.REAL).
                collect(Collectors.toList());


        List<Expense> expenses = settleUpStrategy.settleUp(expenseList);

        for(Expense expense : expenses) {
            //add settled expenses in the group
            expense.setGroup(group);
            group.getExpenses().add(expense);
            expenseRepository.save(expense);
        }

        // change expense type to settled
        for(Expense expense : expenseList) {
            expense.setExpenseType(ExpenseType.SETTLED);
            expenseRepository.save(expense);
        }

        groupRepository.save(group);
        return expenses;
    }
}
