package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.InvalidArgumentsException;

/**
 * Command's who don't have string arguments parent
 */
public abstract class CommandWithoutArgs<T extends CollectionElement> extends Command<T>{

    public CommandWithoutArgs(String name, String description) {
        super(name, description);
    }

    @Override
    public void validateArguments() {
        if(this.getArgs().length > 1){
            throw new InvalidArgumentsException("This command doesn't have any arguments.");
        }
    }
}