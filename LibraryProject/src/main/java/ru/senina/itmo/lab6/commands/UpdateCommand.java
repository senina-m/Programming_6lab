package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.InvalidArgumentsException;

/**
 * Command updates the element with given ID in collection
 */
@CommandAnnotation(name = "update", element = true, collectionKeeper = true, id = true)
public class UpdateCommand<T extends CollectionElement> extends Command<T>{
    private ICollectionKeeper<T> collectionKeeper;
    private T element;
    private long id;

    public UpdateCommand() {
        super("update id {element}", "update the value of the collection element whose id is equal to the given");
    }

    public void setArgs(ICollectionKeeper<T> collectionKeeper) {
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
    public void setArgs(String[] args, T element){
        setArgs(args);
        this.element = element;
    }
}
