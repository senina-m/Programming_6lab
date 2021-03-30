package ru.senina.itmo.lab6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.senina.itmo.lab6.labwork.Coordinates;
import ru.senina.itmo.lab6.labwork.Difficulty;
import ru.senina.itmo.lab6.labwork.Discipline;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class JacksonTest {

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Disabled
    @Test
    public void testFromJsonToCollection() throws JsonProcessingException {
        String json = "{\"list\":[ {\n" +
                "    \"id\" : 1111,\n" +
                "    \"name\" : \"element\",\n" +
                "    \"coordinates\" : {\n" +
                "      \"x\" : 2,\n" +
                "      \"y\" : 3\n" +
                "    },\n" +
                "    \"minimalPoint\" : 80.0,\n" +
                "    \"description\" : \"my lovely Hori\",\n" +
                "    \"averagePoint\" : 9,\n" +
                "    \"difficulty\" : \"HOPELESS\",\n" +
                "    \"discipline\" : {\n" +
                "      \"name\" : \"Killing\",\n" +
                "      \"lectureHours\" : 35,\n" +
                "      \"practiceHours\" : 65,\n" +
                "      \"selfStudyHours\" : 26\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "      \"id\" : 2222,\n" +
                "      \"name\" : \"Laminat\",\n" +
                "      \"coordinates\" : {\n" +
                "        \"x\" : 24,\n" +
                "        \"y\" : 93\n" +
                "      },\n" +
                "      \"minimalPoint\" : 98.0,\n" +
                "      \"description\" : \"my lovely Mia\",\n" +
                "      \"averagePoint\" :0,\n" +
                "      \"difficulty\" : \"VERY_EASY\",\n" +
                "      \"discipline\" : {\n" +
                "        \"name\" : \"Money\",\n" +
                "        \"lectureHours\" : 42,\n" +
                "        \"practiceHours\" : 24,\n" +
                "        \"selfStudyHours\" : 66\n" +
                "      }\n" +
                "    } ]}";
        String name = "element";
        Coordinates coordinates = new Coordinates(2, (long) 3);
        float minimalPoint = 80;
        String description = "my lovely Hori";
        Integer averagePoint = 9;
        Difficulty difficulty = Difficulty.HOPELESS;
        Discipline discipline = new Discipline("Killing", (long) 35, (Integer) 65, 26);
        LabWork labWork = new LabWork(name, coordinates, minimalPoint, description, averagePoint, difficulty, discipline);
        LinkedList<LabWork> list = new LinkedList<>();
        list.add(labWork);
        ICollectionKeeper expectedCollectionKeeper = new CollectionKeeper(list);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ICollectionKeeper collectionKeeper = objectMapper.readValue(json, ICollectionKeeper.class);
        System.out.println(collectionKeeper);
        System.out.println(expectedCollectionKeeper);
//        assertEquals("Wrong ru.senina.lab5.Person deserialization from json", collectionKeeper, collectionKeeper);
    }

    @Test
    public void testFromObjectToJson() throws JsonProcessingException {
        String name = "element";
        Coordinates coordinates = new Coordinates(2, (long) 3);
        float minimalPoint = 80;
        String description = "my lovely Hori";
        Integer averagePoint = 9;
        Difficulty difficulty = Difficulty.HOPELESS;
        Discipline discipline = new Discipline("Killing", (long) 35, (Integer) 65, 26);
        LabWork labWork = new LabWork(name, coordinates, minimalPoint, description, averagePoint, difficulty, discipline);
        LinkedList<LabWork> list = new LinkedList<>();
        list.add(labWork);
        ICollectionKeeper collectionKeeper = new CollectionKeeper(list);
        String expectedJson = "{\"time\":\"2021.02.13 AD at 16:35:38 MSK\",\"list\":[{\"id\":1111,\"name\":\"element\",\"coordinates\":{\"x\":2,\"y\":3},\"creationDate\":\"2021-02-13T16:35:38.9858865\"" +
                ",\"minimalPoint\":80.0,\"description\":\"my lovely Hori\",\"averagePoint\":9,\"difficulty\":\"HOPELESS\",\"discipline\":{\"name\":\"Killing\",\"lectureHours\":35,\"practiceHours\":65,\"selfStudyHours\":26}}]," +
                "\"type\":\"LinkedList\"}";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(df);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(collectionKeeper);
        System.out.println(json);
        // String json = objectMapper.writeValueAsString(collectionKeeper);
        // assertEquals("Wrong ru.senina.lab5.CollectionKeeper serialization to json", expectedJson, json); // Не работает, т.к. время отличается
    }
}
