package com.example.expensesharing.dtos;

import com.example.expensesharing.models.Group;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@Controller
public class CreateGroupResponseDto {
    private Group group;
    private ResponseStatus responseStatus;
}
