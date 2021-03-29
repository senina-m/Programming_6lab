package ru.senina.itmo.lab6;

import ru.senina.itmo.lab6.commands.Command;

public class SerializedCommand {
    private String name;
    private String command;
    private Class<? extends Command> commandType;

    public SerializedCommand(Command command, Parser parser) {
        this.name = command.getName();
        this.command = parser.fromObjectToString(command);
        if(command != null){
            this.commandType = command.getClass();
        }
    }

    public String getName() {
        return name;
    }

    public SerializedCommand() {
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Class<? extends Command> getCommandType() {
        return commandType;
    }

    public void setCommandType(Class<? extends Command> commandType) {
        this.commandType = commandType;
    }
}
