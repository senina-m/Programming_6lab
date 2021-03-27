package ru.senina.itmo.lab6;

import ru.senina.itmo.lab6.labwork.ElementOfCollection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public interface Parser {
    //TODO: Add exceptions
    //TODO: rename methods

    /**
     * @return CollectionKeeper instance with fields serialized from json
     */
    CollectionKeepers fromStringToCollectionKeeper(String json) throws ParsingException;
    /**@return json string from given file*/

    /**
     * @param filename of the file from which string would be read
     * @return string from given file
     */

    default String fromFileToString(String filename){
        Path path = Paths.get(filename);
        checkRights(path);
        try {
            StringBuilder resultString = new StringBuilder();
            File f = new File(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line = br.readLine();
            while (line != null) {
                resultString.append(line);
                line = br.readLine();
            }
            br.close();
            return resultString.toString();

        } catch (IOException e) {
//                TODO: Что тут писать, если я обработала ошибку (или думаю что обработала)?
            //TODO: нормально ли что у меня и nio и io в одном методе ?
        }
        return null;
    }


    /**
     * Method to write json string to file
     *
     * @param filename the path to which file value have be written
     * @param str      the json string
     */
    default void writeStringToFile(String filename, String str) throws FileAccessException {
        Path path = Paths.get(filename);
        checkRights(path);
        try {
            Files.write(path, ("" + str).getBytes());
        } catch (IOException e) {
//                TODO: Что тут писать, если я обработала ошибку (или думаю что обработала)?
        }
    }


    /**
     * Method that parses CollectionKeeper Object to json string
     */
    abstract String fromCollectionKeeperToString(CollectionKeepers collectionKeeper);

    /**
     * Method to parse CollectionKeeper elements Objects to json string
     */
    abstract String fromCollectionKeeperToStringElements(CollectionKeepers collectionKeeper);

    /**
     * Method to parse given LabWork element to json string
     */
    String fromElementToString(ElementOfCollection element);

    default void checkRights(Path path) throws FileAccessException {
        if (Files.notExists(path)) {
            throw new FileAccessException("File " + path.toString() + " doesn't exist!", path.toString());
        } else if (Files.isWritable(path)) {
            throw new FileAccessException("Invalid file rights. File " + path.toString() + " isn't writable!", path.toString());
        }
    }
}