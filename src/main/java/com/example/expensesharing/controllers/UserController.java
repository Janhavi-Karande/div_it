package com.example.expensesharing.controllers;

import com.example.expensesharing.dtos.*;
import com.example.expensesharing.models.User;
import com.example.expensesharing.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    public UserLoginResponseDto login(UserLoginRequestDto loginRequestDto)  {
        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        try {
            User user = userService.login(loginRequestDto.getEmail(),
                    loginRequestDto.getPassword());

            responseDto.setStatus(ResponseStatus.SUCCESS);

        }
        catch (Exception e) {
            responseDto.setStatus(ResponseStatus.FAILURE);
            System.out.println("Error occurred while login "+e.getMessage());
        }
        return responseDto;
    }

    public UserSignUpResponseDto signup(UserSignUpRequestDto userSignUpRequestDto)  {
        UserSignUpResponseDto responseDto = new UserSignUpResponseDto();

        try {
            User user = userService.signup(userSignUpRequestDto.getName(),
                    userSignUpRequestDto.getEmail(), userSignUpRequestDto.getPassword(),
                    userSignUpRequestDto.getPhone());

            responseDto.setUserId(user.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        }
        catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            System.out.println("Error occurred while signing in "+e.getMessage());
        }
        return responseDto;
    }

    public UpdateUserDetailsResponseDto updateUserName(UpdateUserDetailsRequestDto updateUserDetailsRequestDto)  {
        UpdateUserDetailsResponseDto responseDto = new UpdateUserDetailsResponseDto();

        try{
            User user = userService.updateUserName(updateUserDetailsRequestDto.getEmail(),
                    updateUserDetailsRequestDto.getName());
            responseDto.setUser(user);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            System.out.println("Error occurred while updating name "+e.getMessage());
        }
        return responseDto;
    }

    public UpdateUserDetailsResponseDto updateUserPassword(UpdateUserDetailsRequestDto updateUserDetailsRequestDto)  {
        UpdateUserDetailsResponseDto responseDto = new UpdateUserDetailsResponseDto();

        try{
            User user = userService.updateUserPassword(updateUserDetailsRequestDto.getEmail(),
                    updateUserDetailsRequestDto.getPassword());
            responseDto.setUser(user);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        }
        catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            System.out.println("Error occurred while updating password "+e.getMessage());
        }

        return responseDto;
    }

    public UpdateUserDetailsResponseDto updateUserPhone(UpdateUserDetailsRequestDto updateUserDetailsRequestDto)  {
        UpdateUserDetailsResponseDto responseDto = new UpdateUserDetailsResponseDto();

        try{
            User user = userService.updateUserPhoneNumber(updateUserDetailsRequestDto.getEmail(),
                    updateUserDetailsRequestDto.getPhone());
            responseDto.setUser(user);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        }
        catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            System.out.println("Error occurred while updating phone "+e.getMessage());
        }

        return responseDto;
    }

    public UpdateUserDetailsResponseDto updateUserEmail(UpdateUserDetailsRequestDto updateUserDetailsRequestDto)  {
        UpdateUserDetailsResponseDto responseDto = new UpdateUserDetailsResponseDto();

        try{
            User user = userService.updateUserEmail(updateUserDetailsRequestDto.getEmail());
            responseDto.setUser(user);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        }
        catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            System.out.println("Error occurred while updating email "+e.getMessage());
        }

        return responseDto;
    }

}
