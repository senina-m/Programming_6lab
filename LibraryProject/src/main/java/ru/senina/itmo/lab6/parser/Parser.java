package ru.senina.itmo.lab6.parser;

import ru.senina.itmo.lab6.FileAccessException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public abstract class Parser<T> {
    private T object;

    //TODO: Add exceptions

    /**@return instance with fields serialized from string*/
    public abstract T fromStringToObject(String str) throws ParsingException;

    /**@param filename of the file from which string would be read
     * @return string from given file*/
    public String fromFileToString(String filename) throws FileAccessException {
        Path path = Paths.get(filename);

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
            checkRights(path);
            //TODO: нормально ли что у меня и nio и io в одном методе ?
        }
        return null;
    }


    /**Method to write string to file
     * @param filename the path to which file value have be written
     * @param str      the string*/
    public void writeStringToFile(String filename, String str) throws FileAccessException {
        Path path = Paths.get(filename);
        try {
            Files.write(path, ("" + str).getBytes());
        } catch (IOException e) {
            checkRights(path);
        }
    }


    /**
     * Method that parses Object to string
     */
    public abstract String fromObjectToString(T object);


    private void checkRights(Path path) throws FileAccessException {
        if (Files.notExists(path)) {
            throw new FileAccessException("File " + path.toString() + " doesn't exist!", path.toString());
        } else if (!Files.isWritable(path)) {
            throw new FileAccessException("Invalid file rights. File " + path.toString() + " isn't writable!", path.toString());
        } else if (!Files.isReadable(path)) {
            throw new FileAccessException("Invalid file rights. File " + path.toString() + " isn't readable!", path.toString());
        }
    }
}