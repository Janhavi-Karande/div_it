package com.example.expensesharing.controllers;

import com.example.expensesharing.dtos.CreateGroupRequestDto;
import com.example.expensesharing.dtos.CreateGroupResponseDto;
import com.example.expensesharing.dtos.ResponseStatus;
import com.example.expensesharing.models.Group;
import com.example.expensesharing.services.GroupService;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;

    }

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto createGroupRequestDto) {
        CreateGroupResponseDto createGroupResponseDto = new CreateGroupResponseDto();

        try{
            Group group = groupService.createGroup(createGroupRequestDto.getGroupName(), createGroupRequestDto.getDescription(),
                    createGroupRequestDto.getMembers(), createGroupRequestDto.getAdmin());

            createGroupResponseDto.setGroup(group);
            createGroupResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            createGroupResponseDto.setResponseStatus(ResponseStatus.FAILURE);

        }
        return createGroupResponseDto;
    }
}
