package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;

/**
 * Command prints information about the collection (type, initialization date, number of elements, etc.) to the standard output stream
 */
public class InfoCommand extends CommandWithoutArgs {
    CollectionKeepers collectionKeeper;

    public InfoCommand(CollectionKeepers collectionKeeper) {
        super("info", "print information about the collection (type, initialization date, number of elements, etc.) to the standard output stream");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return "You have entered info command.\n" +
                "This collection was created: " + collectionKeeper.getTime() + "\n"
                + "Collection type: " + collectionKeeper.getType() + "\n"
                + "Amount of collection's elements: " + collectionKeeper.getAmountOfElements() + "\n";
    }
}
