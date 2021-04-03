package ru.senina.itmo.lab6.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.parser.CollectionKeeperParser;

/**
 * Command class to find the minimum difficult subject in the collection
 */
@CommandAnnotation(name = "min_by_difficulty", collectionKeeper = true, parser = true)
public class MinByDifficultyCommand extends CommandWithoutArgs {
    private ICollectionKeeper collectionKeeper;
    private CollectionKeeperParser parser;

    @Override
    public void setParser(CollectionKeeperParser parser) {
        this.parser = parser;
    }

    public MinByDifficultyCommand() {
        super("min_by_difficulty", "remove any object from the collection with the minimum difficulty value");
    }

    public void setCollectionKeeper(ICollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        try {
            return "The less difficult subject is: \n" + parser.fromElementToString(collectionKeeper.minByDifficulty());
        } catch (IndexOutOfBoundsException e){
            return "Can't do min_by_difficulty command. " + e.getMessage();
        } catch ( JsonProcessingException e){
            return "Minimal element with such description was incorrect.";
        }
    }
}
