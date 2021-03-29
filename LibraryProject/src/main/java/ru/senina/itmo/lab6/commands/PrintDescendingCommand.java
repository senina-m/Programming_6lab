package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.parser.Parser;
import ru.senina.itmo.lab6.parser.ParsingException;
import ru.senina.itmo.lab6.CollectionElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to print elements of collection in inverted sorted order
 */
@CommandAnnotation(name = "print_descending", collectionKeeper = true, parser = true)
public class PrintDescendingCommand <T extends CollectionElement> extends CommandWithoutArgs<T> {
    private ICollectionKeeper<T> collectionKeeper;
    private Parser<T> parser;

    public PrintDescendingCommand() {
        super("print_descending", "display the elements of the collection in descending order");
    }
    public void setArgs(ICollectionKeeper<T> collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    public void setArgs(Parser<T> parser){
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        try {
            //TODO: что-то тут не так с переводом коллекции в список
            ArrayList<T> resultElements = new ArrayList(collectionKeeper.getSortedList());
            if(resultElements.size() != 0){
                StringBuilder result = new StringBuilder();
                result.append("You entered a command print_descending\":\n");
                for(int i = resultElements.size() - 1; i >= 0; i--){
                    result.append("Element ").append(i + 1).append(": \n").append(parser.fromObjectToString(resultElements.get(i))).append("\n");
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
