package ru.senina.itmo.lab6.commands;

import ru.senina.itmo.lab6.CollectionElement;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.parser.Parser;

/**
 * Parent of all ru.senina.itmo.lab6.commands classes
 */
//TODO: Не добавить ли метод "очистить команду"
public abstract class Command <T extends CollectionElement>{
    private String[] args;
    private final String name;
    private final String description;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setArgs(String[] args){
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public String run(){
        validateArguments();
        return doRun();
    }

    /**
     * Command execute method
     * @return value to print in output like the result of command execute
     */
    protected abstract String doRun();

    /**
     * Arguments validation method
     */
    public abstract void validateArguments();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setArgs(String[] args, T element){}

    public void setArgs(ICollectionKeeper<T> collectionKeeper){}

    public void setArgs(Parser<T> parser){}
}
