package com.example.expensesharing.dtos;

import com.example.expensesharing.models.Group;
import com.example.expensesharing.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateExpenseRequestDto {
    private String name;
    private Double totalAmount;
    private String description;
    private String createdByUserEmail;
    private List<UserExpenseHelper> userExpenseHelpers;
    private String groupName;
}
