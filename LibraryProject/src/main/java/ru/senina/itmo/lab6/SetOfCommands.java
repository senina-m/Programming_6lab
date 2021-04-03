package ru.senina.itmo.lab6;

import java.util.HashMap;
import java.util.Map;

public class SetOfCommands {
    private Map<String, String[]> commandsWithArgs;

    //TODO: подумать, какой здесь конструктор нужен
    public SetOfCommands(Map<String, String[]> commandsWithArgs) {
        this.commandsWithArgs = commandsWithArgs;
    }

    public SetOfCommands() {
    }

    public Map<String, String[]> getCommandsWithArgs() {
        return commandsWithArgs;
    }

    public void setCommandsWithArgs(Map<String, String[]> commandsWithArgs) {
        this.commandsWithArgs = commandsWithArgs;
    }
}
