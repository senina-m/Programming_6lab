package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;

/**
 * Command sorts collection
 */
@CommandAnnotation(name = "sort", collectionKeeper = true)
public class SortCommand<T extends CollectionElement> extends CommandWithoutArgs<T> {
    private ICollectionKeeper<T> collectionKeeper;

    public SortCommand() {
        super("sort", "sort the collection in natural order");
    }

    public void setArgs(ICollectionKeeper<T> collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.sort();
    }
}
