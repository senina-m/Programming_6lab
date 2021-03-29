package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;

/**
 * Command clear collection - delete all elements
 */
@CommandAnnotation(name = "clear", collectionKeeper = true)
public class ClearCommand<T extends CollectionElement> extends CommandWithoutArgs<T> {
    private ICollectionKeeper<T> collectionKeeper;
    public ClearCommand() {
        super("clear", "clear collection");
    }

    public void setArgs(ICollectionKeeper<T> collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.clear();
    }
}
