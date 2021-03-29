package ru.senina.itmo.lab6;

public class CommandResponse {
    private final int commandNumber;
    private final String response;
    private final String commandName;

    public CommandResponse(int commandNumber, String commandName, String response) {
        this.commandName = commandName;
        this.commandNumber = commandNumber;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public int getCommandNumber() {
        return commandNumber;
    }
}