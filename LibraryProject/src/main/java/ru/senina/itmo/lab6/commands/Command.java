package ru.senina.itmo.lab6.commands;

/**
 * Parent of all ru.senina.itmo.lab6.commands classes
 */
public abstract class Command {
    private String[] args;
    private final String name;
    private final String description;

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
}
