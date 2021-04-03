package ru.senina.itmo.lab6;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.commands.*;
import ru.senina.itmo.lab6.labwork.LabWork;
import ru.senina.itmo.lab6.parser.CollectionKeeperParser;
import ru.senina.itmo.lab6.parser.JsonParser;
import ru.senina.itmo.lab6.parser.Parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Class to deal with input and output and keep CollectionKeeper class instance.
 */
public class Keeper {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String filename = "my_file.json";
    private final ServerNetConnector netConnector = new ServerNetConnector();
    //TODO метод чтобы менять после запуска
    private int serverPort = 8181;
    private ICollectionKeeper collectionKeeper = new CollectionKeeper(new LinkedList<LabWork>());
    private final CollectionKeeperParser collectionKeeperParser = new CollectionKeeperParser(objectMapper, ICollectionKeeper.class);
    private final Parser<CommandResponse>  responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private final Parser<CommandArgs> commandJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);

    public Keeper() {
    }

    /**
     * Method to start a new collection and System.in reader
     */
    public void start() {

        //TODO: try_with_resources
        try {
            //TODO: проверить что там с файлом
            netConnector.startConnection(serverPort);
            Map<String, Command> commandMap = createCommandMap();
            SetOfCommands setOfCommands = new SetOfCommands(createCommandsArgsMap(commandMap));
            netConnector.sendResponse(new JsonParser<SetOfCommands>(objectMapper, SetOfCommands.class).fromObjectToString(setOfCommands));

            while (true) {
                String strCommand = netConnector.hasNextCommand();
                CommandArgs commandArgs = commandJsonParser.fromStringToObject(strCommand);
                Command command = commandMap.get(commandArgs.getCommandName());
                command.setArgs(commandArgs);
                if (command.getClass().isAnnotationPresent(CommandAnnotation.class)) {
                    CommandAnnotation annotation = command.getClass().getAnnotation(CommandAnnotation.class);
                    if (annotation.collectionKeeper()) {
                        command.setCollectionKeeper(collectionKeeper);
                    }
                    if (annotation.parser()) {
                        command.setParser(collectionKeeperParser);
                    }
                    if(annotation.element()){
                        //TODO: Check that element isn't null
                        command.setElement(commandArgs.getElement());
                    }
                }
                String commandResult = command.run();
                //TODO: Разорвать соединение, если клиент его разорвал и выйти
                if(netConnector.checkIfConnectionClosed()){
                    netConnector.stopConnection();
                    System.exit(0);
                }
                netConnector.sendResponse(responseParser.fromObjectToString(new CommandResponse(command.getNumber(), command.getName(), commandResult)));
            }
        } finally {
            netConnector.stopConnection();
        }
    }

    private Map<String, Command> createCommandMap(){
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
        return commandMap;
    }

    private Map<String, String[]> createCommandsArgsMap(Map<String, Command> map){
        Map<String, String[]> commandsArgsMap = new HashMap<>();
        for(Command command : map.values()){
            if(command.getClass().isAnnotationPresent(CommandAnnotation.class)) {
                CommandAnnotation annotation = command.getClass().getAnnotation(CommandAnnotation.class);
                if (annotation.element()) {
                    commandsArgsMap.put(annotation.name(), new String[]{"element"});
                }
            }
        }
        return commandsArgsMap;
    }
}
