package ru.senina.itmo.lab6;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.parser.JsonParser;
import ru.senina.itmo.lab6.parser.Parser;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;

/**
 * Class to deal with input and output and keep CollectionKeeper class instance.
 */
public class ClientKeeper {
    private final String filename;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientNetConnector netConnector = new ClientNetConnector();
    private TerminalKeeper terminalKeeper;
    private int numberOfCommands = 0;
    private int recursionLevel = 0;
    private boolean working = true;
    private final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    /**
     * @param filename the path to file from which we read and to which we write collection data
     */
    public ClientKeeper(String filename) {
        this.filename = filename;
    }

    /**
     * Method to start a new collection and System.in reader
     */
    public void start() {
        CommandArgs createCollectionCommand = null;
        try {
            File f = new File(filename);
            if (f.isDirectory() || !Files.isReadable(f.toPath())) {
                System.out.println("There is no rights for reading file. Change rights and run program again!");
                System.exit(0);
            }
            createCollectionCommand = new CommandArgs("create_collection", new String[]{"create_collection", Parser.fromFileToString(filename)});
        } catch (NullPointerException e) {
            System.out.println("File path is wrong. Run program again with correct filename.");
            System.exit(0);
        }

        //TODO метод чтобы менять после запуска
        int serverPort = 8181;
        netConnector.startConnection("localhost", serverPort);
        String message = netConnector.receiveMessage();
        SetOfCommands commandsMap = new JsonParser<>(objectMapper, SetOfCommands.class).fromStringToObject(message);
        commandsMap.getCommandsWithArgs().put("execute_script", new String[]{""});
        terminalKeeper = new TerminalKeeper(commandsMap.getCommandsWithArgs(), objectMapper);
        newCommand(createCollectionCommand);
        
        while (working) {
            CommandArgs command = terminalKeeper.readNextCommand();
            newCommand(command);
        }
        netConnector.stopConnection();
        System.exit(0);
    }

    public void newCommand(CommandArgs command) {
        switch (command.getCommandName()) {
            case ("exit"):
                working = false;
                break;
            case ("execute_script"):
                if (recursionLevel < 10) {
                    recursionLevel++;
                    try {
                        LinkedList<CommandArgs> scriptCommands = terminalKeeper.executeScript(command.getArgs()[1]);
                        for (CommandArgs c : scriptCommands) {
                            newCommand(c);
                        }
                    }catch (FileAccessException e){
                        terminalKeeper.printResponse(new CommandResponse(numberOfCommands++, command.getCommandName(), e.getMessage()));
                    }
                } else {
                    terminalKeeper.printResponse(new CommandResponse(numberOfCommands++, "execute_script",
                            "You have stacked in the recursion! It's not allowed to deep in more then 10 levels. " +
                                    "\n No more recursive scripts would be executed!"));
                    recursionLevel = 0;
                }
                break;
            default:
                command.setNumber(numberOfCommands++);
                String message = commandArgsJsonParser.fromObjectToString(command);
                netConnector.sendMessage(message);
                String response = netConnector.receiveMessage();
                CommandResponse commandAnswer = responseParser.fromStringToObject(response);
                terminalKeeper.printResponse(commandAnswer);
        }
    }
}

