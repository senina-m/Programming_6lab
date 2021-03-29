package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;

/**
 * Command adds new element to collection
 */
@CommandAnnotation(name = "add", element = true, collectionKeeper = true)
public class AddCommand<T extends CollectionElement> extends CommandWithoutArgs<T>{
    ICollectionKeeper<T> collectionKeeper;
    private T element;

    public AddCommand() {
        super("add {element}", "add new element to collection");
    }

    public void setArgs(ICollectionKeeper<T> collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.add(element);
    }

    @Override
    public void setArgs(String[] args, T element){
        setArgs(args);
        this.element = element;
    }

}
