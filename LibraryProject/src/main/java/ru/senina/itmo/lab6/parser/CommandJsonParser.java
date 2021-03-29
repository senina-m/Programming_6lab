package ru.senina.itmo.lab6.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.commands.Command;
import ru.senina.itmo.lab6.labwork.LabWork;

public class CommandJsonParser<T extends Command<LabWork>> extends JsonParser<T>{
    public CommandJsonParser(ObjectMapper objectMapper, Class<T> classT) {
        super(objectMapper, classT);
    }
}
