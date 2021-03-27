package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.Parser;

/**
 * Command class to find the minimum difficult subject in the collection
 */
public class MinByDifficultyCommand extends CommandWithoutArgs {
    private final CollectionKeepers collectionKeeper;
    private final Parser parser;

    public MinByDifficultyCommand(CollectionKeepers collectionKeeper, Parser parser) {
        super("min_by_difficulty", "remove any object from the collection with the minimum difficulty value");
        this.collectionKeeper = collectionKeeper;
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        try {
            return "The less difficult subject is: \n" + parser.fromElementToString(collectionKeeper.minByDifficulty());
        } catch (IndexOutOfBoundsException e){
            return "Can't do min_by_difficulty command. " + e.getMessage();
        }
    }
}
