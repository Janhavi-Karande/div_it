package com.example.expensesharing.dtos;

import com.example.expensesharing.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddRemoveMemberGroupResponseDto {
    private List<User> members;
    private ResponseStatus responseStatus;
}
