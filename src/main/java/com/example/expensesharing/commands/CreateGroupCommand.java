package com.example.expensesharing.commands;

import com.example.expensesharing.controllers.GroupController;
import com.example.expensesharing.dtos.CreateGroupRequestDto;
import com.example.expensesharing.dtos.CreateGroupResponseDto;
import com.example.expensesharing.dtos.ResponseStatus;
import com.example.expensesharing.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateGroupCommand implements Command {

    private GroupController groupController;

    public CreateGroupCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));

        String groupName = words.get(2);
        String email = words.get(0);

        CreateGroupRequestDto createGroupRequestDto = new CreateGroupRequestDto();
        createGroupRequestDto.setGroupName(groupName);
        createGroupRequestDto.setAdminEmail(email);

        CreateGroupResponseDto createGroupResponseDto = groupController.createGroup(createGroupRequestDto);

        if(createGroupResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            System.out.println(groupName+ " Group created successfully");
        }
        else{
            System.out.println(groupName+ " Group creation failed");
        }
    }

    @Override
    public boolean matches(String input) {

        List<String> words = List.of(input.split(" "));
        return words.size() == 3 && words.get(1).equals(CommandKeywords.createGroup);
    }
}
