package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.Parser;

import java.io.IOException;

/**
 * Command saves collection to file
 */
public class SaveCommand extends CommandWithoutArgs {
    private final CollectionKeepers collectionKeeper;
    private final Parser parser;
    private final String filename;

    public SaveCommand(CollectionKeepers collectionKeeper, Parser parser, String filename) {
        super("save", "save collection to file");
        this.collectionKeeper = collectionKeeper;
        this.parser = parser;
        this.filename = filename;
    }

    @Override
    protected String doRun(){
        parser.writeStringToFile(filename, parser.fromCollectionKeeperToString(collectionKeeper));
        return "Collection was successfully saved to " + filename + " file.";
    }
}
