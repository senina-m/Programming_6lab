package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.parser.Parser;
import ru.senina.itmo.lab6.parser.ParsingException;

/**
 * Command shows all collection elements
 */
@CommandAnnotation(name = "show", collectionKeeper = true, parser = true)
public class ShowCommand<T extends CollectionElement> extends CommandWithoutArgs<T> {

    private ICollectionKeeper<T> collectionKeeper;
    private Parser<T> parser;

    public ShowCommand() {
        super("show", "print to standard output all elements of the collection in string representation");
    }

    public void setArgs(ICollectionKeeper<T> collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    public void setArgs(Parser<T> parser) {
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
