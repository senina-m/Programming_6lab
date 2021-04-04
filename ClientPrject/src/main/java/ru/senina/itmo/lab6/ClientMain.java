package ru.senina.itmo.lab6;

import java.util.Optional;


/**
 * @author Senina Mariya
 * Main class of programm to start app.
 */
public class ClientMain {

    public static void main(String[] args){
        try{
            String path = Optional.ofNullable(System.getenv("SENINA")).orElseThrow(
                    () -> new InvalidArgumentsException("\"SENINA\" variable is not set in the environment! \n Set file path to this variable! The program can't work without it!"));
            ClientKeeper clientKeeper = new ClientKeeper(path);
            clientKeeper.start();
        }catch (InvalidArgumentsException e){
            System.out.println(e.getMessage());
        }
    }
}
