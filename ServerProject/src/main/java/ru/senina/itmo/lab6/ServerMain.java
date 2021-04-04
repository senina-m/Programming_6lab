package ru.senina.itmo.lab6;

/**
 * @author Senina Mariya
 * Main class of programm to start app.
 */
public class ServerMain {

    public static void main(String[] args){
        ServerKeeper serverKeeper = new ServerKeeper();
        serverKeeper.start();
    }
}
