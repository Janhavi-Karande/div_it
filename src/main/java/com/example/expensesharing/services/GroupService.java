package com.example.expensesharing.services;

import com.example.expensesharing.exceptions.GroupNotFoundException;
import com.example.expensesharing.exceptions.UserNotFoundException;
import com.example.expensesharing.models.Group;
import com.example.expensesharing.models.User;
import com.example.expensesharing.repositories.GroupRepository;
import com.example.expensesharing.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group createGroup(String name, String description, List<User> members, User admin) throws UserNotFoundException {
        Group group = new Group();

        // check if group with same name already exists
        Optional<Group> optionalGroup = groupRepository.findByName(name);

        if(optionalGroup.isPresent()){
            System.out.println("Group with name" +optionalGroup.get().getName()+ " already exists");
        }

        // check if given user exists
        Optional<User> optionalUserAdmin = userRepository.findByEmail(admin.getEmail());
        if(optionalUserAdmin.isEmpty()){
            throw  new UserNotFoundException("User with email " +admin.getEmail()+ " does not exits");
        }

        // check if all the members are users
        int membersCount = members.size();
        List<User> membersList = new ArrayList<>();

        for(int i=0; i<membersCount; i++){
            Optional<User> optionalMember = userRepository.findByEmail(members.get(i).getEmail());

            if(optionalMember.isEmpty()){
                throw  new UserNotFoundException("User with email " +members.get(i).getEmail()+ " does not exits \n Select members who are users.");
            }

            membersList.add(optionalMember.get());
        }

        membersList.add(admin);
        group.setName(name);
        group.setDescription(description);
        group.setMembers(membersList);
        group.setAdmin(optionalUserAdmin.get());

        return groupRepository.save(group);
    }

    public Group getGroup(String groupName) throws GroupNotFoundException {
        Optional<Group> optionalGroup = groupRepository.findByName(groupName);
        if(optionalGroup.isEmpty()){
            throw new GroupNotFoundException("Group with group name " +groupName+ " does not exists.");
        }

        Group group = optionalGroup.get();
        return group;
    }

}
