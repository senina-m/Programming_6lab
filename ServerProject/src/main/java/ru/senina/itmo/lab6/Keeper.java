package ru.senina.itmo.lab6;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.commands.Command;
import ru.senina.itmo.lab6.commands.CommandAnnotation;
import ru.senina.itmo.lab6.commands.CreateCollectionCommand;
import ru.senina.itmo.lab6.parser.CollectionKeeperParser;
import ru.senina.itmo.lab6.parser.CommandJsonParser;
import ru.senina.itmo.lab6.parser.JsonParser;
import ru.senina.itmo.lab6.parser.Parser;

import java.io.File;
import java.nio.file.Files;

/**
 * Class to deal with input and output and keep CollectionKeeper class instance.
 */
public class Keeper {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String filename;
    private final ServerNetConnector netConnector = new ServerNetConnector();
    //TODO метод чтобы менять после запуска
    private int serverPort = 8181;
    private final ICollectionKeeper collectionKeeper = new CollectionKeeper();
    private final CollectionKeeperParser collectionKeeperParser = new CollectionKeeperParser(objectMapper, ICollectionKeeper.class);
    private final Parser<CommandResponse>  responseParser = new JsonParser<CommandResponse>(objectMapper, CommandResponse.class);
    private final CommandJsonParser commandJsonParser = new CommandJsonParser(objectMapper);

    public Keeper() {
    }

    /**
     * Method to start a new collection and System.in reader
     */
    public void start() {
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

        //TODO: try_with_resources
        try {
            netConnector.startConnection(serverPort);
            while (true) {
                String strCommand = netConnector.hasNextCommand();
                Command command = commandJsonParser.fromStringToObject(strCommand);
                if (command.getClass().isAnnotationPresent(CommandAnnotation.class)) {
                    CommandAnnotation annotation = command.getClass().getAnnotation(CommandAnnotation.class);
                    if (annotation.collectionKeeper()) {
                        command.setCollectionKeeper(collectionKeeper);
                    }
                    if (annotation.parser()) {
                        command.setParser(collectionKeeperParser);
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
}
