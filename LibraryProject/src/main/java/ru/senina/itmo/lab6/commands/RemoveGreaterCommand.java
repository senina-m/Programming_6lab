package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.labwork.LabWork;

/**
 * Command removes all elements greater than given
 */
public class RemoveGreaterCommand extends CommandWithoutArgs implements ElementNeed{
    private final CollectionKeepers collectionKeeper;
    private LabWork element;

    public RemoveGreaterCommand( CollectionKeepers collectionKeeper) {
        super("remove_greater {element}", "remove all items from the collection that are greater than the specified one");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.removeGreater(element);
    }

    @Override
    public void setLabWorkElement(LabWork labWorkElement) {
        this.element = labWorkElement;
    }
}
