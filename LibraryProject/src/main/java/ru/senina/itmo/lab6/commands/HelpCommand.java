package ru.senina.itmo.lab6.commands;

import java.util.Map;

/**
 * Command displays help for available ru.senina.itmo.lab6.commands
 */

public class HelpCommand extends CommandWithoutArgs {
    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        super("help", "displays help for available ru.senina.itmo.lab6.commands");
        this.commands = commands;
    }

    @Override
    public String doRun() {
        StringBuilder string = new StringBuilder();
        string.append("The full list of ru.senina.itmo.lab6.commands is here: \n");
        for(Command command: commands.values()){
            string.append(command.getName()).append(" : ").append(command.getDescription()).append("\n");
        }
        return string.toString();
    }
}

//    String ru.senina.itmo.lab6.commands = "help : display help for available ru.senina.itmo.lab6.commands \n" +
//            "info : print information about the collection (type, initialization date, number of elements, etc.) to the standard output stream\n" +
//            "show : print to standard output all elements of the collection in string representation\n" +
//            "add {element} : add new element to collection\n" +
//            "update id {element} : update the value of the collection element whose id is equal to the given\n" +
//            "remove_by_id id : remove an item from the collection by its id\n" +
//            "clear : clear collection\n" +
//            "save : save collection to file\n" +
//            "execute_script file_name : read and execute the script from the specified file. The script contains ru.senina.itmo.lab6.commands in the same form in which the user enters them interactively.\n" +
//            "exit : end the program (without saving to file)\n" +
//            "remove_at index : remove the element at the given collection position (index)\n" +
//            "remove_greater {element} : remove all items from the collection that are greater than the specified one\n" +
//            "sort : sort the collection in natural order\n" +
//            "min_by_difficulty : remove any object from the collection with the minimum difficulty value\n" +
//            "filter_by_description description: display elements whose description field value is equal to the given one\n" +
//            "print_descending : display the elements of the collection in descending order\n";
