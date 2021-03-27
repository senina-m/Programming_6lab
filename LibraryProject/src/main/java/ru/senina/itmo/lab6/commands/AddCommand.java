package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.labwork.LabWork;

/**
 * Command adds new element to collection
 */
public class AddCommand extends CommandWithoutArgs implements ElementNeed{
    CollectionKeepers collectionKeeper;
    private LabWork element;

    public AddCommand(CollectionKeepers collectionKeeper) {
        super("add {element}", "add new element to collection");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    public void setLabWorkElement(LabWork labWorkElement) {
        this.element = labWorkElement;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.add(element);
    }

}
