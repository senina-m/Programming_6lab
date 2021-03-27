package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.InvalidArgumentsException;

/**
 * Command removes element from collection by it's ID
 */
public class RemoveByIDCommand extends Command {
    private final CollectionKeepers collectionKeeper;
    private long id;

    public RemoveByIDCommand(CollectionKeepers collectionKeeper) {
        super("remove_by_id id", "remove an item from the collection by its id");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun(){
        return collectionKeeper.removeById(id);
    }

    @Override
    public void validateArguments() {
        String[] args = this.getArgs();
        if(args.length == 2){
            this.id = Long.parseLong(args[1]);
        }else {
            throw new InvalidArgumentsException("Remove_by_id command has the only argument - id.");
        }
    }
}
