package ru.senina.itmo.lab6.commands;

import java.util.Map;

/**
 * Command displays help for available ru.senina.itmo.lab6.commands
 */

@CommandAnnotation(name = "help")
public class HelpCommand extends CommandWithoutArgs {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        super("help", "displays help for available commands");
        this.commands = commands;
    }

    @Override
    public String doRun() {
        StringBuilder string = new StringBuilder();
        string.append("The full list of Commands is here: \n");
        for(Command command: commands.values()){
            string.append(command.getName()).append(" : ").append(command.getDescription()).append("\n");
        }
        return string.toString();
    }
}