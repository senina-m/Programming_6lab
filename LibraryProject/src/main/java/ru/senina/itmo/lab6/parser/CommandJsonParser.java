package ru.senina.itmo.lab6.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.commands.Command;

public class CommandJsonParser extends JsonParser<Command>{
    public CommandJsonParser(ObjectMapper objectMapper) {
        super(objectMapper, Command.class);
    }
}
