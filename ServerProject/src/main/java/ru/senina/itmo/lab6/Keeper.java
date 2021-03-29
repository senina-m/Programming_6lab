package ru.senina.itmo.lab6;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.commands.*;
import ru.senina.itmo.lab6.labwork.LabWork;
import ru.senina.itmo.lab6.parser.JsonParser;
import ru.senina.itmo.lab6.parser.Parser;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to deal with input and output and keep CollectionKeeper class instance.
 */
public class Keeper {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String filename;
    private final ServerNetConnector netConnector = new ServerNetConnector();
    //TODO метод чтобы менять после запуска
    private int serverPort = 8181;
    private final ICollectionKeeper<LabWork> collectionKeeper = new CollectionKeeper();
    private final Parser<SerializedCommand> requestParser = new JsonParser<SerializedCommand>(objectMapper, SerializedCommand.class);
    private final Parser<CommandResponse>  responseParser = new JsonParser<CommandResponse>(objectMapper, CommandResponse.class);

    public Keeper() {
    }

    /**
     * Method to start a new collection and System.in reader
     */
    public void start() {
        try {
            File f = new File(filename);
            if (!f.isDirectory() && Files.isReadable(f.toPath())) {
                Command<LabWork> createCollection = new CreateCollectionCommand(filename);
            } else {
                System.out.println("There is no rights for reading file. Change rights and run program again!");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            System.out.println("File path is wrong. Run program again with correct filename.");
            System.exit(0);
        }

        Map<String, Command<LabWork>> commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand<LabWork>(commandMap));
        commandMap.put("info", new InfoCommand<LabWork>());
        commandMap.put("show", new ShowCommand<LabWork>());
        commandMap.put("add", new AddCommand<LabWork>());
        commandMap.put("update", new UpdateCommand<LabWork>());
        commandMap.put("remove_by_id", new RemoveByIDCommand<LabWork>());
        commandMap.put("clear", new ClearCommand<LabWork>());
        commandMap.put("save", new SaveCommand<LabWork>(filename));
        commandMap.put("remove_at", new RemoveAtCommand<LabWork>());
        commandMap.put("remove_greater", new RemoveGreaterCommand<LabWork>());
        commandMap.put("sort", new SortCommand<LabWork>());
        commandMap.put("min_by_difficulty", new MinByDifficultyCommand<LabWork>());
        commandMap.put("filter_by_description", new FilterByDescriptionCommand<LabWork>());
        commandMap.put("print_descending", new PrintDescendingCommand<LabWork>());
        commandMap.put("execute_script", new ExecuteScriptCommand<LabWork>());
        commandMap.put("exit", new ExitCommand<LabWork>());

        Map<String, JsonParser<? extends Command<LabWork>>> parserMap = new HashMap<>();
        parserMap.put("help", new JsonParser<HelpCommand<LabWork>>(objectMapper, HelpCommand<LabWork>.class));
        parserMap.put("info", new JsonParser<InfoCommand<LabWork>>());


        //TODO: try_with_resources
        try {
            netConnector.startConnection(serverPort);
            while (true) {
                String strCommand = netConnector.hasNextCommand();
                SerializedCommand serializedCommand = requestParser.fromStringToObject(strCommand);
                Class<? extends Command<LabWork>> commandClass = commandMap.get(serializedCommand.getName()).getClass();
                Command<LabWork> command = new JsonParser<>().fromStringToObject(serializedCommand.getCommand(), serializedCommand.getCommandType());
                if (command.getClass().isAnnotationPresent(CommandAnnotation.class)) {
                    CommandAnnotation annotation = command.getClass().getAnnotation(CommandAnnotation.class);
                    if (annotation.collectionKeeper()) {
                        command.setArgs(collectionKeeper);
                    }
                    if (annotation.parser()) {
                        command.setArgs(parser);
                    }
                }
                String commandResult = command.run();
                //TODO: Разорвать соединение, если клиент его разорвал и выйти
                netConnector.sendResponse(responseParser.fromObjectToString(new CommandResponse(command.getNumber(), command.getName(), commandResult)));
            }
        } finally {
            netConnector.stopConnection();
        }
    }
}
