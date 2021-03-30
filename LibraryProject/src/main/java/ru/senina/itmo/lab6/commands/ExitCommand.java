package ru.senina.itmo.lab6.commands;


@CommandAnnotation(name = "exit")
public class ExitCommand extends CommandWithoutArgs{
    public ExitCommand() {
        super("exit", "end the program (without saving to file)");
    }

    @Override
    protected String doRun() {
        return "Exit command was execute!";
    }
}
