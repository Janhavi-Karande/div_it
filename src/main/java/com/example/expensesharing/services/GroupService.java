package com.example.expensesharing.services;

import com.example.expensesharing.exceptions.GroupNotFoundException;
import com.example.expensesharing.exceptions.InvalidRequestException;
import com.example.expensesharing.exceptions.UserNotFoundException;
import com.example.expensesharing.models.Group;
import com.example.expensesharing.models.User;
import com.example.expensesharing.repositories.GroupRepository;
import com.example.expensesharing.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final UserService userService;
    private GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public Group createGroup(String name, String description, String adminEmail) throws UserNotFoundException {
        Group group = new Group();

        // check if group with same name already exists
        Optional<Group> optionalGroup = groupRepository.findByName(name);

        if(optionalGroup.isPresent()){
            System.out.println("Group with name" +optionalGroup.get().getName()+ " already exists");
        }

        // check if given user(admin) exists
       User admin = userService.getUser(adminEmail);

        List<User> membersList = new ArrayList<>();

        membersList.add(admin);
        group.setName(name);
        group.setDescription(description);
        group.setMembers(membersList);
        group.setAdmin(admin);

        return groupRepository.save(group);
    }

    public Group getGroup(String groupName) throws GroupNotFoundException {

        Optional<Group> optionalGroup = groupRepository.findByName(groupName);
        if(optionalGroup.isEmpty()){
            throw new GroupNotFoundException("Group with group name " +groupName+ " does not exists.");
        }

        return optionalGroup.get();
    }

    @Transactional
    public List<User> addMember(String adminEmail, String userEmail, String groupName) throws UserNotFoundException, GroupNotFoundException {

        User admin = userService.getUser(adminEmail);
        User user = userService.getUser(userEmail);

        Group group = getGroup(groupName);

        List<User> members = group.getMembers();

        if(!group.getAdmin().getEmail().equals(admin.getEmail())){
            throw new UserNotFoundException("User with email " +admin.getEmail()+ " is not admin.");
        }

        members.add(user);

        groupRepository.save(group);
        return members;

    }

    @Transactional
    public List<User> removeMember(String adminEmail, String userEmail, String groupName) throws UserNotFoundException, GroupNotFoundException, InvalidRequestException {

        User user = userService.getUser(userEmail);
        Group group = getGroup(groupName);
        User admin = userService.getUser(adminEmail);

        List<User> members = group.getMembers();

        if(!group.getAdmin().getEmail().equals(admin.getEmail())){
            throw new UserNotFoundException("User with email " +admin.getEmail()+ " is not admin.");
        }


       if(userEmail.equals(admin.getEmail())){
           throw new InvalidRequestException("Admin can't be removed from a group.");
       }
        members.remove(user);

        groupRepository.save(group);
        return members;
    }

     // TO-DO: get all members of a group

    @Transactional
    public List<User> getAllMembers(String groupName) throws GroupNotFoundException {

        Optional<Group> optionalGroup = groupRepository.findByName(groupName);
        if(optionalGroup.isEmpty()){
            throw new GroupNotFoundException("Group with group name " +groupName+ " does not exists.");
        }

        Group group = optionalGroup.get();
        return group.getMembers();
    }

    @Transactional
    public List<Group> getAllGroups(String userEmail){
        List<Group> groups = groupRepository.findAllByMemberEmail(userEmail);
        return groups;
    }
}
