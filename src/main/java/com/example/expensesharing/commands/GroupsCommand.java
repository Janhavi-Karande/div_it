package com.example.expensesharing.commands;

import com.example.expensesharing.controllers.GroupController;
import com.example.expensesharing.dtos.GetGroupsRequestDto;
import com.example.expensesharing.dtos.GetGroupsResponseDto;
import com.example.expensesharing.dtos.ResponseStatus;
import com.example.expensesharing.models.Group;
import com.example.expensesharing.services.GroupService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupsCommand implements Command {

    private GroupController groupController;

    public GroupsCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));
        String userEmail = words.get(0);

        GetGroupsRequestDto getGroupsRequestDto = new GetGroupsRequestDto();
        getGroupsRequestDto.setUserEmail(userEmail);

        GetGroupsResponseDto getGroupsResponseDto = groupController.getAllGroups(getGroupsRequestDto);

        if(getGroupsResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){

            if(getGroupsResponseDto.getGroups().isEmpty()){
                System.out.println(userEmail + " is not a member of any group");
            }
            else {
                for (Group g : getGroupsResponseDto.getGroups()) {
                    System.out.println(userEmail + " is member of " + g.getName());
                }
            }

        }
        else {
            System.out.println("Groups command failed.");
        }
    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 2 && words.get(1).equalsIgnoreCase(CommandKeywords.groups);
    }
}
