package ru.senina.itmo.lab6.labwork;

import java.util.Objects;

//TODO: Надо обязать элементы коллекции поддерживать парсинг
public abstract class ElementOfCollection {
    private final java.time.LocalDateTime creationDate = java.time.LocalDateTime.now();
    //Поле не может быть null, Значение этого поля должно генерироваться автоматически https://javadevblog.com/polnoe-rukovodstvo-po-java-8-date-time-api-primery-localdate-instant-localdatetime-parse-i-format.html
    private Long id = Math.abs((long) Objects.hash(creationDate)); //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
