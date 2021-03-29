package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.parser.Parser;

@CommandAnnotation(name ="create_collection", collectionKeeper = true, parser = true, filename = true)
public class CreateCollectionCommand<T extends CollectionElement> extends CommandWithoutArgs<T>{
    private ICollectionKeeper<T> collectionKeeper;
    // парсер парсит CollectionKeeper или LinkedList<LabWork>
    private Parser<T> parser;
    private final String filename;

    public CreateCollectionCommand(String filename) {
        super("create_collection", "create collection from elements from given file");
        this.filename = filename;
    }

    @Override
    public void setArgs(ICollectionKeeper<T> collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    public void setArgs(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        String fileContents = parser.fromFileToString(filename);
        //TODO: fix method: make list of elements from file content
//        collectionKeeper = parser.fromStringToObject(fileContents);
        return "Collection was successfully created";
    }
}
