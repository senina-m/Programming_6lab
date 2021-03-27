package ru.senina.itmo.lab6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.senina.itmo.lab6.labwork.ElementOfCollection;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class that works with json and output
 */
public class JsonParser implements Parser {
    //TODO: Обрабатывать ошибки парсинга, чтобы не лезло наружу
    public static ObjectMapper objectMapper = new ObjectMapper();

    public JsonParser() {
    }

    /**@param json json string
     * @return CollectionKeeper instance with fields serialized from json or empty collection if string have mistakes.
     * @throws ParsingException if something got wrong with json
     */
    @Override
    public CollectionKeeper fromStringToCollectionKeeper(String json){
        try{
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            CollectionKeeper result = objectMapper.readValue(json, CollectionKeeper.class);
            if(result.getList() != null){
                return result;
            }else{
                return new CollectionKeeper();
            }
        } catch (ParsingException | JsonProcessingException e){
            throw new ParsingException("The file contents isn't valid. The collection will be empty.");
        }
    }

    /**
     * Method that parses CollectionKeeper Object to json string
     * @param collectionKeeper Object to parse
     * @return json string
     */
    @Override
    public String fromCollectionKeeperToString(CollectionKeepers collectionKeeper){
        try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setDateFormat(df);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(collectionKeeper);
        }catch (JsonProcessingException e){
            //TODO: обработать ошибку JsonProcessingException она не должна выпасть, подумать, когда это может случиться
        }
        return "CollectionKeeper object is empty.";
    }

    /**
     * Method to parse CollectionKeeper elements Objects to json string
     * @param collectionKeeper Object whose elements we need to parse
     * @return json string
     */
    @Override
    public String fromCollectionKeeperToStringElements(CollectionKeepers collectionKeeper){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setDateFormat(df);
            StringBuilder resultString = new StringBuilder();
            ArrayList<Object> list = new ArrayList(collectionKeeper.getList());
            //TODO: как-то доставать тип элементов коллекции, чтобы класть их в массив
            for (int i = 0; i < list.size(); i++) {
                resultString.append("\nElement ").append(i + 1).append(":\n").append(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list.get(i)));
            }
            return resultString.toString();
        }catch (JsonProcessingException e){
            //TODO: обработать ошибку JsonProcessingException она не должна выпасть, подумать, когда это может случиться
        }
        return "Collection is empty."; //TODO: Тут нужно кинуть исключение?
    }

    /**
     * Method to parse given LabWork element to json string
     * @param element LabWork object
     * @return json string
     */
    @Override
    public String fromElementToString(ElementOfCollection element){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setDateFormat(df);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(element);
        }catch (JsonProcessingException e){
            //TODO: обработать ошибку JsonProcessingException она не должна выпасть, подумать, когда это может случиться
        }
        return "Element is empty";
    }
}
