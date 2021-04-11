package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.labwork.LabWork;
import ru.senina.itmo.lab6.CollectionKeeperParser;
import ru.senina.itmo.lab6.parser.ParsingException;

import java.util.List;

/**
 * Command to print elements of collection in inverted sorted order
 */
@CommandAnnotation(name = "print_descending", collectionKeeper = true, parser = true)
public class PrintDescendingCommand extends CommandWithoutArgs {
    private ICollectionKeeper collectionKeeper;
    private CollectionKeeperParser parser;

    public PrintDescendingCommand() {
        super("print_descending", "display the elements of the collection in descending order");
    }
    public void setCollectionKeeper(ICollectionKeeper collectionKeeper){
        this.collectionKeeper = collectionKeeper;
    }

    public void setParser( CollectionKeeperParser parser){
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        try {
            List<LabWork> list = collectionKeeper.getSortedList();
            if(list.size() != 0){
                StringBuilder result = new StringBuilder();
                result.append("You entered a command print_descending:\n");
                for(int i = list.size() - 1; i >= 0; i--){
                    result.append("Element ").append(i + 1).append(": \n").append(parser.fromElementToString(list.get(i))).append("\n");
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
