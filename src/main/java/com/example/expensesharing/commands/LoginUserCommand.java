package com.example.expensesharing.commands;

import com.example.expensesharing.controllers.UserController;
import com.example.expensesharing.dtos.ResponseStatus;
import com.example.expensesharing.dtos.UserLoginRequestDto;
import com.example.expensesharing.dtos.UserLoginResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginUserCommand implements Command {

    private UserController userController;

    public LoginUserCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void execute(String input) {

        List<String> words = List.of(input.split(" "));
        String email = words.get(0);
        String password = words.get(2);

        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setEmail(email);
        userLoginRequestDto.setPassword(password);

        UserLoginResponseDto userLoginResponseDto = userController.login(userLoginRequestDto);

        if(userLoginResponseDto.getStatus().equals(ResponseStatus.SUCCESS)){
            System.out.println(email + " logged in successfully");
        }
        else{
            System.out.println(email + " login failed");

        }
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 3 && words.get(1).equalsIgnoreCase(CommandKeywords.login);
    }
}
