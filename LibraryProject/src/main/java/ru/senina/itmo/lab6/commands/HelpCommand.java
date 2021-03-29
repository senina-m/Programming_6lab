package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.util.Map;

/**
 * Command displays help for available ru.senina.itmo.lab6.commands
 */

@CommandAnnotation(name = "help")
public class HelpCommand<T extends CollectionElement> extends CommandWithoutArgs<T> {
    private final Map<String, Command<LabWork>> commands;

    public HelpCommand(Map<String, Command<LabWork>> commands) {
        super("help", "displays help for available commands");
        this.commands = commands;
    }

    @Override
    public String doRun() {
        StringBuilder string = new StringBuilder();
        string.append("The full list of Commands is here: \n");
        for(Command<LabWork> command: commands.values()){
            string.append(command.getName()).append(" : ").append(command.getDescription()).append("\n");
        }
        return string.toString();
    }
}