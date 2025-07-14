package com.example.expensesharing.controllers;

import com.example.expensesharing.dtos.*;
import com.example.expensesharing.models.Group;
import com.example.expensesharing.models.User;
import com.example.expensesharing.services.GroupService;
import org.springframework.stereotype.Controller;

import java.util.List;

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
                    createGroupRequestDto.getAdminEmail());

            createGroupResponseDto.setGroup(group);
            createGroupResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            createGroupResponseDto.setResponseStatus(ResponseStatus.FAILURE);

        }
        return createGroupResponseDto;
    }

    public AddRemoveMemberGroupResponseDto addMember(AddRemoveMemberGroupRequestDto addRemoveMemberGroupRequestDto) {
        AddRemoveMemberGroupResponseDto responseDto = new AddRemoveMemberGroupResponseDto();

        try{
            List<User> members = groupService.addMember(addRemoveMemberGroupRequestDto.getAdminEmail(),
                    addRemoveMemberGroupRequestDto.getUserEmail(),
                    addRemoveMemberGroupRequestDto.getGroupName());

            responseDto.setMembers(members);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public AddRemoveMemberGroupResponseDto removeMember(AddRemoveMemberGroupRequestDto addRemoveMemberGroupRequestDto) {
        AddRemoveMemberGroupResponseDto responseDto = new AddRemoveMemberGroupResponseDto();

        try{
            List<User> members = groupService.removeMember(addRemoveMemberGroupRequestDto.getAdminEmail(),
                    addRemoveMemberGroupRequestDto.getUserEmail(),
                    addRemoveMemberGroupRequestDto.getGroupName());

            responseDto.setMembers(members);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public AddRemoveMemberGroupResponseDto getAllMembers(AddRemoveMemberGroupRequestDto getMembersRequestDto) {

        AddRemoveMemberGroupResponseDto responseDto = new AddRemoveMemberGroupResponseDto();

        try{
            List<User> members = groupService.getAllMembers(getMembersRequestDto.getGroupName());
            responseDto.setMembers(members);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public GetGroupsResponseDto getAllGroups(GetGroupsRequestDto getGroupsRequestDto) {
        GetGroupsResponseDto responseDto = new GetGroupsResponseDto();

        try{
            List<Group> groups = groupService.getAllGroups(getGroupsRequestDto.getUserEmail());
            responseDto.setGroups(groups);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
