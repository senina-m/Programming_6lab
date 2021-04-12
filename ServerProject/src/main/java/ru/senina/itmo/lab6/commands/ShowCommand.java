package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeeper;
import ru.senina.itmo.lab6.CollectionKeeperParser;
import ru.senina.itmo.lab6.parser.ParsingException;

/**
 * Command shows all collection elements
 */
@CommandAnnotation(name = "show", collectionKeeper = true, parser = true)
public class ShowCommand extends CommandWithoutArgs{

    private CollectionKeeper collectionKeeper;
    private CollectionKeeperParser parser;

    public ShowCommand() {
        super("show", "print to standard output all elements of the collection in string representation");
    }

    public void setCollectionKeeper(CollectionKeeper collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    public void setParser(CollectionKeeperParser parser) {
        this.parser = parser;
    }

    @Override
    protected String doRun(){
        try {
            if(collectionKeeper.getAmountOfElements()!= 0) {
                collectionKeeper.sort();
                return parser.fromCollectionToStringElements(collectionKeeper);
            } else {
                return "No elements in collection.";
            }
        }
        catch (ParsingException e){
            return "Parsing was failed. " + e.getMessage();
        }
    }
}
