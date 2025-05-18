package com.example.expensesharing.dtos;

import com.example.expensesharing.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDetailsResponseDto {
    private User user;
    private ResponseStatus responseStatus;
}
