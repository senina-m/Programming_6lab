package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;

/**
 * Command sorts collection
 */
public class SortCommand extends CommandWithoutArgs {
    private final CollectionKeepers collectionKeeper;

    public SortCommand(CollectionKeepers collectionKeeper) {
        super("sort", "sort the collection in natural order");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.sort();
    }
}
