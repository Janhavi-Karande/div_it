package com.example.expensesharing.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDetailsRequestDto {
    private String name;
    private String email;
    private String phone;
    private String password;
}
