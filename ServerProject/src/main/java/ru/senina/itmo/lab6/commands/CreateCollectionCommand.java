package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.InvalidArgumentsException;
import ru.senina.itmo.lab6.Logging;
import ru.senina.itmo.lab6.parser.CollectionKeeperParser;
import ru.senina.itmo.lab6.parser.ParsingException;

import java.util.logging.Level;

@CommandAnnotation(name = "create_collection", collectionKeeper = true, parser = true, filename = true)
public class CreateCollectionCommand extends Command {
    private ICollectionKeeper collectionKeeper;
    private CollectionKeeperParser parser;
    private String collectionString;

    public String getCollectionString() {
        return collectionString;
    }

    public void setCollectionString(String filename) {
        this.collectionString = filename;
    }

    public CreateCollectionCommand() {
        super("create_collection", "create collection from elements from given file");
    }

    @Override
    public void setCollectionKeeper(ICollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    public void setParser(CollectionKeeperParser parser) {
        this.parser = parser;
    }

    @Override
    //TODO:почему при наследовании не отлавливаются ошибки?
    protected String doRun() throws InvalidArgumentsException {
        try {
            collectionKeeper.setList(parser.fromStringToObject(collectionString).getList());
        } catch (ParsingException e) {
            Logging.log(Level.WARNING, "Collection file was incorrect, collection wasn't updated with start values.");
            return "File was incorrect, collection will be empty!";
        }
        if (collectionKeeper.getList() != null) {
            return "Collection was successfully created";
        } else {
            throw new InvalidArgumentsException("File " + collectionString + " was invalid.");
        }

    }

    @Override
    public void validateArguments() {
        String[] args = this.getArgs();
        if (args.length == 2) {
            this.collectionString = args[1];
        } else {
            throw new InvalidArgumentsException("Create collection command has the only argument - contents of a collection file.");
        }
    }
}
