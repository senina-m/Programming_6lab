package ru.senina.itmo.lab6.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.senina.itmo.lab6.ICollectionKeeper;
import ru.senina.itmo.lab6.labwork.LabWork;
import ru.senina.itmo.lab6.parser.CollectionKeeperParser;

/**
 * Parent of all ru.senina.itmo.lab6.commands classes
 */
//TODO: Не добавить ли метод "очистить команду"
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddCommand.class, name = "AddCommand"),
        @JsonSubTypes.Type(value = ClearCommand.class, name = "ClearCommand"),
        @JsonSubTypes.Type(value = CreateCollectionCommand.class, name = "CreateCollectionCommand"),
        @JsonSubTypes.Type(value = ExecuteScriptCommand.class, name = "ExecuteScriptCommand"),

        @JsonSubTypes.Type(value = ExitCommand.class, name = "ExitCommand"),
        @JsonSubTypes.Type(value = FilterByDescriptionCommand.class, name = "FilterByDescriptionCommand"),
        @JsonSubTypes.Type(value = HelpCommand.class, name = "HelpCommand"),
        @JsonSubTypes.Type(value = InfoCommand.class, name = "InfoCommand"),

        @JsonSubTypes.Type(value = MinByDifficultyCommand.class, name = "MinByDifficultyCommand"),
        @JsonSubTypes.Type(value = PrintDescendingCommand.class, name = "PrintDescendingCommand"),
        @JsonSubTypes.Type(value = RemoveAtCommand.class, name = "RemoveAtCommand"),
        @JsonSubTypes.Type(value = RemoveByIDCommand.class, name = "RemoveByIDCommand"),
        @JsonSubTypes.Type(value = RemoveGreaterCommand.class, name = "RemoveGreaterCommand"),

        @JsonSubTypes.Type(value = PrintDescendingCommand.class, name = "PrintDescendingCommand"),
        @JsonSubTypes.Type(value = RemoveAtCommand.class, name = "RemoveAtCommand"),
        @JsonSubTypes.Type(value = RemoveByIDCommand.class, name = "RemoveByIDCommand"),
        @JsonSubTypes.Type(value = RemoveGreaterCommand.class, name = "RemoveGreaterCommand")
        }
)
public abstract class Command {
    private String[] args;
    private final String name;
    private final String description;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public String run() {
        validateArguments();
        return doRun();
    }

    /**
     * Command execute method
     *
     * @return value to print in output like the result of command execute
     */
    protected abstract String doRun();

    /**
     * Arguments validation method
     */
    public abstract void validateArguments();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setArgs(String[] args, LabWork element) {
    }

    public void setArgs(ICollectionKeeper collectionKeeper) {
    }

    public void setParser(CollectionKeeperParser parser) {
    }
}
