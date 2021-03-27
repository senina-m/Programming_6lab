package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;

/**
 * Command clear collection - delete all elements
 */
public class ClearCommand extends CommandWithoutArgs {
    private final CollectionKeepers collectionKeeper;
    public ClearCommand(CollectionKeepers collectionKeeper) {
        super("clear", "clear collection");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.clear();
    }
}
