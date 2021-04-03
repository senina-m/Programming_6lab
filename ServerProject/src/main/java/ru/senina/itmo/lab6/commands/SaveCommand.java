package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.parser.CollectionKeeperParser;

/**
 * Command saves collection to file
 */
@CommandAnnotation(name = "save", collectionKeeper = true, parser = true, filename = true)
public class SaveCommand extends CommandWithoutArgs {
    private ICollectionKeeper collectionKeeper;
    private CollectionKeeperParser parser;
    private String filename;

    public SaveCommand(String filename) {
        super("save", "save collection to file");
        this.filename = filename;
    }

    public void setCollectionKeeper(ICollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    public void setParser(CollectionKeeperParser parser) {
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        parser.writeStringToFile(filename, parser.fromObjectToString(collectionKeeper));
        return "Collection was successfully saved to " + filename + " file.";
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
