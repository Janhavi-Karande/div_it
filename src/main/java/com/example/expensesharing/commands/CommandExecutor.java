package com.example.expensesharing.commands;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandExecutor {
    public List<Command> commands = new ArrayList<>();

    @Autowired
    private LoginUserCommand loginUserCommand;

    @Autowired
    private SignUpUserCommand signUpUserCommand;

    @Autowired
    private AddMemberCommand addMemberCommand;

    @Autowired
    private CreateGroupCommand createGroupCommand;

    @Autowired
    private RemoveMemberCommand removeMemberCommand;

    @Autowired
    private HistoryCommand historyCommand;

    @Autowired
    private GroupsCommand groupsCommand;

    @PostConstruct
    public void init() {
        commands.add(loginUserCommand);
        commands.add(signUpUserCommand);
        commands.add(createGroupCommand);
        commands.add(addMemberCommand);
        commands.add(removeMemberCommand);
        commands.add(historyCommand);
        commands.add(groupsCommand);
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void removeCommand(Command command) {
        commands.remove(command);
    }

    public void execute(String input) {

        System.out.println("Number of commands: " + commands.size());

        for (Command command : commands) {
            System.out.println("Result = " + command.matches(input));
            if(command.matches(input)){
                command.execute(input);
                break;
            }
        }

    }

}
