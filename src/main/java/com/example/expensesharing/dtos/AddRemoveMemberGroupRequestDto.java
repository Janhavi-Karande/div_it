package com.example.expensesharing.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRemoveMemberGroupRequestDto {
    private String groupName;
    private String userEmail;
    private String adminEmail;
}
