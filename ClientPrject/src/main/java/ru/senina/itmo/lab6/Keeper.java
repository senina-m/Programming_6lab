package ru.senina.itmo.lab6;

import ru.senina.itmo.lab6.commands.*;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Class to deal with input and output and keep CollectionKeeper class instance.
 */
public class Keeper {
    private final String filename;
    private final ClientNetConnector netConnector = new ClientNetConnector();
    //TODO метод чтобы менять после запуска
    private int serverHost = 8181;
    private TerminalKeeper<LabWork> terminalKeeper;
    private int numberOfCommands = 0;
    private int recursionLevel = 0;
    private boolean working = true;
    //Тут должен быть парсер для команд.
    // А тут должен быть парсер для ответов


    /**
     * @param filename the path to file from which we read and to which we write collection data
     */
    public Keeper(String filename) {
        this.filename = filename;
    }

    /**
     * Method to start a new collection and System.in reader
     */
    public void start(){
        try {
            File f = new File(filename);
            if (!f.isDirectory() && Files.isReadable(f.toPath())) {
                Command createCollection = new CreateCollectionCommand(filename);
            } else {
                System.out.println("There is no rights for reading file. Change rights and run program again!");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            System.out.println("File path is wrong. Run program again with correct filename.");
            System.exit(0);
        }

        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand(commandMap));
        commandMap.put("info", new InfoCommand());
        commandMap.put("show", new ShowCommand());
        commandMap.put("add", new AddCommand());
        commandMap.put("update", new UpdateCommand());
        commandMap.put("remove_by_id", new RemoveByIDCommand());
        commandMap.put("clear", new ClearCommand());
        commandMap.put("save", new SaveCommand(filename));
        commandMap.put("remove_at", new RemoveAtCommand());
        commandMap.put("remove_greater", new RemoveGreaterCommand());
        commandMap.put("sort", new SortCommand());
        commandMap.put("min_by_difficulty", new MinByDifficultyCommand());
        commandMap.put("filter_by_description", new FilterByDescriptionCommand());
        commandMap.put("print_descending", new PrintDescendingCommand());
        commandMap.put("execute_script", new ExecuteScriptCommand());
        commandMap.put("exit", new ExitCommand());

        netConnector.startConnection("local host", serverHost);
        terminalKeeper = new TerminalKeeper<>(commandMap);
        while (working) {
            Command command = terminalKeeper.readNextCommand();
            newCommand(command);
        }
        netConnector.stopConnection();
        System.exit(0);
    }

    public void newCommand(Command command) {
        if (command.getClass().isAnnotationPresent(CommandAnnotation.class)) {
            CommandAnnotation annotation = command.getClass().getAnnotation(CommandAnnotation.class);
            if (annotation.name().equals("exit")) {
                working = false;
            } else if (annotation.name().equals("execute_script")) {
                if(recursionLevel < 10) {
                    recursionLevel++;
                    LinkedList<Command> scriptCommands = terminalKeeper.executeScript(((ExecuteScriptCommand) command).getFilename());
                    for (Command c : scriptCommands){
                        newCommand(c);
                    }
                }else {
                    terminalKeeper.printResponse(new CommandResponse(numberOfCommands++, "execute_script",
                            "You have stacked in the recursion! It's not allowed to deep in more then 10 levels. " +
                                    "\n All script commands wouldn't be executed!" ));
                    recursionLevel = 0;
                }
            }else{
                command.setNumber(numberOfCommands++);
                String message = parser.fromObjectToString(command);
                netConnector.sendMessage(message);
                String response = netConnector.receiveMessage();
                CommandResponse commandAnswer = parser.fromSringToOBject(response);
                terminalKeeper.printResponse(commandAnswer);
            }
        }
    }
}
