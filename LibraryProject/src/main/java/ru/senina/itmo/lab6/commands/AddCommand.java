package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.labwork.LabWork;

/**
 * Command adds new element to collection
 */
@CommandAnnotation(name = "add", element = true, collectionKeeper = true)
public class AddCommand extends CommandWithoutArgs{
    ICollectionKeeper collectionKeeper;
    private LabWork element;

    public LabWork getElement() {
        return element;
    }

    public void setElement(LabWork element) {
        this.element = element;
    }

    public AddCommand() {
        super("add {element}", "add new element to collection");
    }

    public void setCollectionKeeper(ICollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.add(element);
    }

    @Override
    public void setArgsWithElement(String[] args, LabWork element){
        setArgs(args);
        this.element = element;
    }

}
