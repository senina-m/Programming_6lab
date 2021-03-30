package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;

/**
 * Command sorts collection
 */
@CommandAnnotation(name = "sort", collectionKeeper = true)
public class SortCommand extends CommandWithoutArgs {
    private ICollectionKeeper collectionKeeper;

    public SortCommand() {
        super("sort", "sort the collection in natural order");
    }

    public void setArgs(ICollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.sort();
    }
}
