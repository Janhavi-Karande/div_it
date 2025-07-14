package com.example.expensesharing.commands;

import com.example.expensesharing.controllers.UserController;
import com.example.expensesharing.dtos.ResponseStatus;
import com.example.expensesharing.dtos.UserSignUpRequestDto;
import com.example.expensesharing.dtos.UserSignUpResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignUpUserCommand implements Command {

    private UserController userController;

    public SignUpUserCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        String username = words.get(1);
        String password = words.get(2);
        String email = words.get(3);
        String phone = words.get(4);

        UserSignUpRequestDto signUpRequestDto = new UserSignUpRequestDto();
        signUpRequestDto.setName(username);
        signUpRequestDto.setPassword(password);
        signUpRequestDto.setEmail(email);
        signUpRequestDto.setPhone(phone);

        UserSignUpResponseDto signUpResponseDto = userController.signup(signUpRequestDto);

        if(signUpResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            System.out.println(username+ " User signed up successfully");
        }
        else{
            System.out.println(username+ " User sign up failed");
        }
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 5 && words.get(0).equalsIgnoreCase(CommandKeywords.signUp);
    }
}
