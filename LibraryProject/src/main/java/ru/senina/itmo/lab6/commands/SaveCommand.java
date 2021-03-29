package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.labwork.LabWork;
import ru.senina.itmo.lab6.parser.Parser;

/**
 * Command saves collection to file
 */
@CommandAnnotation(name = "save", collectionKeeper = true, parser = true, filename = true)
public class SaveCommand<T extends CollectionElement> extends CommandWithoutArgs<T> {
    private ICollectionKeeper<T> collectionKeeper;
    private Parser<T> parser;
    private final String filename;

    public SaveCommand(String filename) {
        super("save", "save collection to file");
        this.filename = filename;
    }

    public void setArgs(ICollectionKeeper<T> collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    public void setArgs(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        parser.writeStringToFile(filename, parser.fromObjectToString(collectionKeeper));
        return "Collection was successfully saved to " + filename + " file.";
    }
}
