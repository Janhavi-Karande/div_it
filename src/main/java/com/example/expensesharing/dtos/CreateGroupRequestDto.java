package com.example.expensesharing.dtos;

import com.example.expensesharing.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.util.List;

@Getter
@Setter
@Controller
public class CreateGroupRequestDto {
    private String groupName;
    private String description;
    private List<User> members;
    private User admin;
}
