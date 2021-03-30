package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;

/**
 * Command clear collection - delete all elements
 */
@CommandAnnotation(name = "clear", collectionKeeper = true)
public class ClearCommand extends CommandWithoutArgs{
    private ICollectionKeeper collectionKeeper;
    public ClearCommand() {
        super("clear", "clear collection");
    }

    public void setArgs(ICollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.clear();
    }
}
