package com.example.expensesharing.dtos;

import com.example.expensesharing.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.util.List;

@Getter
@Setter

public class CreateGroupRequestDto {
    private String groupName;
    private String description;
    private String adminEmail;
}
