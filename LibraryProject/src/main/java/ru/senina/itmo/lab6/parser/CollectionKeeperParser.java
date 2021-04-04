package ru.senina.itmo.lab6.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.util.LinkedList;

public class CollectionKeeperParser extends JsonParser<ICollectionKeeper> {

    public CollectionKeeperParser(ObjectMapper objectMapper, Class<ICollectionKeeper> classT) {
        super(objectMapper, classT);
    }

    public String fromCollectionToStringElements(ICollectionKeeper object) throws ParsingException{
        try {
            StringBuilder resultString = new StringBuilder();
            LinkedList<LabWork> list = object.getList();
            for (int i = 0; i < list.size(); i++) {
                resultString.append("\nElement ").append(i + 1).append(":\n").append(fromElementToString(list.get(i)));
            }
            return resultString.toString();
        } catch (JsonProcessingException e) {
            throw new ParsingException("Something wrong with object.");
            //TODO: обработать ошибку JsonProcessingException она не должна выпасть, подумать, когда это может случиться
        }
    }

    public String fromElementToString(LabWork element) throws JsonProcessingException {
        return CollectionKeeperParser.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(element);
    }
}
