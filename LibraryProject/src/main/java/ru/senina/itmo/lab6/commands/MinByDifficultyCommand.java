package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.parser.Parser;

/**
 * Command class to find the minimum difficult subject in the collection
 */
@CommandAnnotation(name = "min_by_difficulty", collectionKeeper = true, parser = true)
public class MinByDifficultyCommand<T extends CollectionElement> extends CommandWithoutArgs<T> {
    private ICollectionKeeper<T> collectionKeeper;
    private Parser<T> parser;

    public MinByDifficultyCommand() {
        super("min_by_difficulty", "remove any object from the collection with the minimum difficulty value");
    }

    public void setArgs(ICollectionKeeper<T> collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        try {
            return "The less difficult subject is: \n" + parser.fromObjectToString(collectionKeeper.minByDifficulty());
        } catch (IndexOutOfBoundsException e){
            return "Can't do min_by_difficulty command. " + e.getMessage();
        }
    }
}
