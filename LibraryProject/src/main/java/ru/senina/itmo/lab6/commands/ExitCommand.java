package ru.senina.itmo.lab6.commands;


import ru.senina.itmo.lab6.CollectionElement;

@CommandAnnotation(name = "exit")
public class ExitCommand<T extends CollectionElement> extends CommandWithoutArgs<T>{
    public ExitCommand() {
        super("exit", "end the program (without saving to file)");
    }

    @Override
    protected String doRun() {
        return "Exit command was execute!";
    }
}
