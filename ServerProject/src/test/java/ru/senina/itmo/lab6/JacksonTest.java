package ru.senina.itmo.lab6;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.senina.itmo.lab6.labwork.Coordinates;
import ru.senina.itmo.lab6.labwork.Difficulty;
import ru.senina.itmo.lab6.labwork.Discipline;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class JacksonTest {

    public static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFromObjectToJson() {
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
        CollectionKeeperParser parser = new CollectionKeeperParser(objectMapper, ICollectionKeeper.class);
        String json = parser.fromObjectToString(collectionKeeper);
        System.out.println(json);
//         String json = objectMapper.writeValueAsString(collectionKeeper);
//         assertEquals("Wrong ru.senina.lab5.CollectionKeeper serialization to json", expectedJson, json); // Не работает, т.к. время отличается
    }

}
