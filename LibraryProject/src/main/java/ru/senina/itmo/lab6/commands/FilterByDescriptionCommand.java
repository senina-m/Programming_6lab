package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.InvalidArgumentsException;
import ru.senina.itmo.lab6.parser.Parser;
import ru.senina.itmo.lab6.parser.ParsingException;
import ru.senina.itmo.lab6.CollectionElement;

import java.util.List;

/**
 * Command to find all elements in collection with given description
 */
@CommandAnnotation(name = "filter_by_description", collectionKeeper = true, parser = true)
public class FilterByDescriptionCommand<T extends CollectionElement> extends Command<T> {
    private ICollectionKeeper<T> collectionKeeper;
    private String description;
    private Parser<T> parser;

    public FilterByDescriptionCommand() {
        super("filter_by_description description", "display elements whose description field value is equal to the given one");
    }

    @Override
    public void setArgs(ICollectionKeeper<T> collectionKeeper) {
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    public void setArgs(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    protected String doRun() {
        try {
            List<T> resultElements = collectionKeeper.filterByDescription(description);
            if(resultElements.size() != 0){
                StringBuilder result = new StringBuilder();
                result.append("You entered a command filter_by_description. These are the elements with description \"").append(description).append("\":\n");
                for(int i = 0; i < resultElements.size(); i++){
                    result.append("Element ").append(i + 1).append(": \n").append(parser.fromObjectToString(resultElements.get(i))).append("\n");
                }
                return result.toString();
            }else{
                return "There is now elements with description \"" + description + "\".";
            }
        }catch (ParsingException e){
            return "Parsing in filter_by_description was failed. " + e.getMessage();
        }
    }

    @Override
    public void validateArguments() {
        String[] args = getArgs();
        if(args.length >= 2){
            StringBuilder description = new StringBuilder();
            for(int i = 1; i < args.length; i++){
                description.append(args[i]);
                if(i != args.length - 1){
                    description.append(" ");
                }
            }
            this.description = description.toString();
        }else {
            throw new InvalidArgumentsException("Command filter_by_description has to have a String argument.");
        }
    }
}
