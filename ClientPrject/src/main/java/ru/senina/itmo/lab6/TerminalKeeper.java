package ru.senina.itmo.lab6;

import ru.senina.itmo.lab6.commands.Command;
import ru.senina.itmo.lab6.commands.CommandAnnotation;
import ru.senina.itmo.lab6.labwork.Coordinates;
import ru.senina.itmo.lab6.labwork.Difficulty;
import ru.senina.itmo.lab6.labwork.Discipline;
import ru.senina.itmo.lab6.labwork.LabWork;

import java.util.*;


public class TerminalKeeper<T extends CollectionElement> {
    private boolean systemInIsClosed = false;
    private Scanner in = new Scanner(System.in);

    public TerminalKeeper(Map<String, Command> commands) {
        this.commands = commands;
    }

    Map<String, Command> commands;

    public Command readNextCommand() {
        while (!systemInIsClosed) {
            try {
                System.out.print("> ");
                String[] line = cleanLine(in.nextLine().split("[ \t\f]+"));
                if (line.length > 0) {
                    if (commands.containsKey(line[0])) {
                        Command command = commands.get(line[0]);
                        if (command.getClass().isAnnotationPresent(CommandAnnotation.class)) {
                            CommandAnnotation annotation = command.getClass().getAnnotation(CommandAnnotation.class);
                            if (annotation.element()) {
                                T element = readElement();
                                command.setArgs(line, element);
                            }
                        } else {
                            command.setArgs(line);
                        }
                        return command;
                    } else {
                        System.out.println("There is no such command.");
                    }
                }

            } catch (InvalidArgumentsException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("You have entered the end of file symbol. Program will be terminate and collection will be saved.");
                systemInIsClosed = true;
                commands.get("save").setArgs(new String[]{"save"});
                return commands.get("save");
            }
        }
        commands.get("exit").setArgs(new String[]{"exit"});
        return commands.get("exit");
    }

    private String[] cleanLine(String[] line) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : line) {
            if (!s.equals("")) {
                result.add(s);
            }
        }
        String[] resultStr = new String[result.size()];
        return result.toArray(resultStr);
    }

    //TODO: Можно ли читать разные типы элементов. Наверное нельзя - мы работаем с одной коллекцией, но можно будет выбрать с какой запускаться.

    /**
     * @return NULLABLE
     */
    private T readElement() {
        while (true) {
            try {
                LabWork element = new LabWork();
                System.out.println("You run a command, which needs LabWork element to be entered.");

                System.out.println("Enter element's name.");
                element.setName(in.nextLine());

                System.out.println("Enter coordinates. In first line x <= 74. In second y >= -47.");
                element.setCoordinates(new Coordinates(Integer.parseInt(in.nextLine()), Long.parseLong(in.nextLine())));

                System.out.println("Enter minimal point.");
                element.setMinimalPoint(Float.parseFloat(in.nextLine()));

                System.out.println("Enter element description.");
                element.setDescription(in.nextLine());

                System.out.println("Enter average point.");
                element.setAveragePoint(Integer.parseInt(in.nextLine()));

                System.out.println("Enter one difficulty of following list:");
                Difficulty[] difficulties = Difficulty.values();
                for (Difficulty difficulty : difficulties) {
                    System.out.print(difficulty.toString() + "; ");
                }

                element.setDifficulty(in.nextLine());
                System.out.println("Enter discipline parametrs:");
                Discipline discipline = new Discipline();
                System.out.println("Enter discipline name.");
                discipline.setName(in.nextLine());
                System.out.println("Enter discipline lectureHours.");
                discipline.setLectureHours(Long.parseLong(in.nextLine()));
                System.out.println("Enter discipline practiceHours.");
                discipline.setPracticeHours(Integer.parseInt(in.nextLine()));
                System.out.println("Enter discipline selfStudyHours.");
                discipline.setSelfStudyHours(Integer.parseInt(in.nextLine()));
                element.setDiscipline(discipline);
                //TODO: Как этого избежать, мне нажло при чтении знать какой элемент. Возможно нужно сделать метод, который возвращает меп {вопрос который задать : куда присвоить}
                return (T) element;
            } catch (InvalidArgumentsException | NumberFormatException e) {
                System.out.println("You have entered invalidate value." + e.getMessage() + "\nDo you want to exit from command? (yes/no)");
                if (in.nextLine().equals("yes")) {
                    System.out.println("You have exit from previous command.");
                    return null;
                } else {
                    System.out.println("Try again.");
                }
            }
        }
    }

    public LinkedList<Command> executeScript(String filename) {
        in = new Scanner(filename);
        LinkedList<Command> commandsQueue = new LinkedList<>();
        while (in.hasNext()){
            commandsQueue.add(readNextCommand());
        }
        //TODO: метод executeFromFile, который будет возвращать список комманд к исполнению
        return commandsQueue;
    }

    public void printResponse(CommandResponse response) {
        //TODO: проверка на правильность порядка ответов
        System.out.println(response.getResponse());
    }
}
