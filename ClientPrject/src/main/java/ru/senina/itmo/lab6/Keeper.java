package ru.senina.itmo.lab6;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.parser.JsonParser;
import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;

/**
 * Class to deal with input and output and keep CollectionKeeper class instance.
 */
public class Keeper {
    private final String filename;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientNetConnector netConnector = new ClientNetConnector();
    //TODO метод чтобы менять после запуска
    private int serverHost = 8181;
    private TerminalKeeper terminalKeeper;
    private int numberOfCommands = 0;
    private int recursionLevel = 0;
    private boolean working = true;
    private final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    /**
     * @param filename the path to file from which we read and to which we write collection data
     */
    public Keeper(String filename) {
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
            createCollectionCommand = new CommandArgs("create_collection", new String[]{filename});
        } catch (NullPointerException e) {
            System.out.println("File path is wrong. Run program again with correct filename.");
            System.exit(0);
        }

        netConnector.startConnection("local host", serverHost);
        SetOfCommands commandsMap = new JsonParser<SetOfCommands>(objectMapper, SetOfCommands.class).fromStringToObject(netConnector.receiveMessage());
        terminalKeeper = new TerminalKeeper(commandsMap.getCommandsWithArgs());
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
                    LinkedList<CommandArgs> scriptCommands = terminalKeeper.executeScript(command.getArgs()[1]);
                    for (CommandArgs c : scriptCommands) {
                        newCommand(c);
                    }
                } else {
                    terminalKeeper.printResponse(new CommandResponse(numberOfCommands++, "execute_script",
                            "You have stacked in the recursion! It's not allowed to deep in more then 10 levels. " +
                                    "\n All script commands wouldn't be executed!"));
                    recursionLevel = 0;
                }
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

