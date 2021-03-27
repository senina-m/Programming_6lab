package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionKeepers;
import ru.senina.itmo.lab6.InvalidArgumentsException;

/** * Command that removes element on given place in collection
 */
public class RemoveAtCommand extends Command {
    private final CollectionKeepers collectionKeeper;
    private int index;

    public RemoveAtCommand(CollectionKeepers collectionKeeper) {
        super("remove_at index", "remove the element at the given collection position (index)");
        this.collectionKeeper = collectionKeeper;
    }

    @Override
    protected String doRun() {
        return collectionKeeper.removeAt(index);
    }

    @Override
    public void validateArguments() {
        String[] args = getArgs();
        if (args.length == 2) {
            try {
                this.index = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new InvalidArgumentsException("Remove_at command argument has to be Integer.");
            }
        } else {
            throw new InvalidArgumentsException("Update command has to have an argument - index of the element.");
        }
    }
}
