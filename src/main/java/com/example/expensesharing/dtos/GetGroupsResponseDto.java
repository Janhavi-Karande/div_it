package com.example.expensesharing.dtos;

import com.example.expensesharing.models.Group;
import com.example.expensesharing.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetGroupsResponseDto {
    private List<Group> groups;
    private ResponseStatus responseStatus;
}
