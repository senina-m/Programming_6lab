package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.InvalidArgumentsException;
import ru.senina.itmo.lab6.parser.CollectionKeeperParser;

@CommandAnnotation(name ="create_collection", collectionKeeper = true, parser = true, filename = true)
public class CreateCollectionCommand extends CommandWithoutArgs{
    private ICollectionKeeper collectionKeeper;
    private CollectionKeeperParser parser;
    private final String filename;

    public CreateCollectionCommand(String filename) {
        super("create_collection", "create collection from elements from given file");
        this.filename = filename;
    }

    @Override
    public void setArgs(ICollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    public void setParser(CollectionKeeperParser parser) {
        this.parser = parser;
    }

    @Override
    //TODO:почему при наследовании не отлавливаются ошибки?
    protected String doRun() throws InvalidArgumentsException {
        String fileContents = parser.fromFileToString(filename);
        collectionKeeper.setList(parser.fromStringToObject(fileContents).getList());
        if(collectionKeeper.getList() != null) {
            return "Collection was successfully created";
        }else {
           throw new InvalidArgumentsException("File " + filename + " was invalid.");
        }
    }
}
