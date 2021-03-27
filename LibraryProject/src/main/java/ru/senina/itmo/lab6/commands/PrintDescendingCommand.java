package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.Parser;
import ru.senina.itmo.lab6.ParsingException;
import ru.senina.itmo.lab6.labwork.ElementOfCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to print elements of collection in inverted sorted order
 */
public class PrintDescendingCommand extends CommandWithoutArgs {
    private final CollectionKeepers collectionKeeper;
    private final Parser parser;

    public PrintDescendingCommand(CollectionKeepers collectionKeeper, Parser parser) {
        super("print_descending", "display the elements of the collection in descending order");
        this.collectionKeeper = collectionKeeper;
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        try {
            //TODO: что-то тут не так с переводом коллекции в список
            List<? extends ElementOfCollection> resultElements = new ArrayList(collectionKeeper.getSortedList());
            if(resultElements.size() != 0){
                StringBuilder result = new StringBuilder();
                result.append("You entered a command print_descending\":\n");
                for(int i = resultElements.size() - 1; i >= 0; i--){
                    result.append("Element ").append(i + 1).append(": \n").append(parser.fromElementToString(resultElements.get(i))).append("\n");
                }
                return result.toString();
            }else{
                return "There is now elements in collection now.";
            }
        }catch (ParsingException e){
            return "Parsing in print_descending was failed";
        }
    }
}
