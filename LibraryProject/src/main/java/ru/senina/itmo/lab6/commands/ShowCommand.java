package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.Parser;
import ru.senina.itmo.lab6.ParsingException;

/**
 * Command shows all collection elements
 */
public class ShowCommand extends CommandWithoutArgs {

    private final CollectionKeepers collectionKeeper;
    private final Parser parser;

    public ShowCommand(CollectionKeepers collectionKeeper, Parser parser) {
        super("show", "print to standard output all elements of the collection in string representation");
        this.collectionKeeper =  collectionKeeper;
        this.parser = parser;
    }

    @Override
    protected String doRun(){
        try {
            if(collectionKeeper.getAmountOfElements()!= 0) {
                return parser.fromCollectionKeeperToStringElements(collectionKeeper);
            } else {
                return "No elements in collection.";
            }
        }
        catch (ParsingException e){
            return "Parsing was failed. " + e.getMessage();
        }
    }
}
