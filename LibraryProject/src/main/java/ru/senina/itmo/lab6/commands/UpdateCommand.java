package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.InvalidArgumentsException;
import ru.senina.itmo.lab6.labwork.LabWork;

/**
 * Command updates the element with given ID in collection
 */
public class UpdateCommand extends Command implements ElementNeed {
    final CollectionKeepers collectionKeeper;
    private LabWork element;
    private long id;

    public UpdateCommand(CollectionKeepers collectionKeeper) {
        super("update id {element}", "update the value of the collection element whose id is equal to the given");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.updateID(id, element);
    }

    @Override
    public void validateArguments() {
        String[] args = getArgs();
        if(args.length == 2){
            try{
                this.id = Long.parseLong(args[1]);
            }
            catch (NumberFormatException e){
                throw new InvalidArgumentsException("Update command argument has to be long.");
            }
        }else {
            throw new InvalidArgumentsException("Update command has to have an argument - id of the element.");
        }
    }

    @Override
    public void setLabWorkElement(LabWork labWorkElement) {
        this.element = labWorkElement;
    }
}
