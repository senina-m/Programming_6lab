package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.labwork.LabWork;

/**
 * Command removes all elements greater than given
 */
@CommandAnnotation(name = "remove_greater", element = true, collectionKeeper = true)
public class RemoveGreaterCommand extends CommandWithoutArgs{
    private ICollectionKeeper collectionKeeper;
    private LabWork element;

    public RemoveGreaterCommand() {
        super("remove_greater {element}", "remove all items from the collection that are greater than the specified one");
    }

    public void setCollectionKeeper(ICollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.removeGreater(element);
    }

    @Override
    public void setArgsWithElement(String[] args, LabWork element){
        setArgs(args);
        this.element = element;
    }

    public LabWork getElement() {
        return element;
    }

    public void setElement(LabWork element) {
        this.element = element;
    }
}
