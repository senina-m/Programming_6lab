package ru.senina.itmo.lab6;

public class FileAccessException extends RuntimeException {
    private String filename;
    FileAccessException(String message, String filename){
        super(message);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
