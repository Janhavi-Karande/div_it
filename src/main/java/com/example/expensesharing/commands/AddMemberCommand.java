package com.example.expensesharing.commands;

import com.example.expensesharing.controllers.GroupController;
import com.example.expensesharing.dtos.AddRemoveMemberGroupRequestDto;
import com.example.expensesharing.dtos.AddRemoveMemberGroupResponseDto;
import com.example.expensesharing.dtos.ResponseStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddMemberCommand implements Command {
    private GroupController groupController;

    public AddMemberCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public void execute(String input) {
        List<String> words = List.of(input.split(" "));

        String adminEmail = words.get(0);
        String groupName = words.get(2);
        String memberEmail = words.get(3);

        AddRemoveMemberGroupRequestDto memberGroupRequestDto = new AddRemoveMemberGroupRequestDto();
        memberGroupRequestDto.setAdminEmail(adminEmail);
        memberGroupRequestDto.setGroupName(groupName);
        memberGroupRequestDto.setUserEmail(memberEmail);

        AddRemoveMemberGroupResponseDto memberGroupResponseDto = groupController.addMember(memberGroupRequestDto);

        if(memberGroupResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            System.out.println(memberEmail+ " added successfully");
        }
        else{
            System.out.println("Failed to add member " +memberEmail);
        }

    }

    @Override
    public boolean matches(String input) {
        List<String> words = List.of(input.split(" "));

        return words.size() == 4 && words.get(1).equalsIgnoreCase(CommandKeywords.addMember);
    }
}
