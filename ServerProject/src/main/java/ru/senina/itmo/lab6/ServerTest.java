package ru.senina.itmo.lab6;

import java.util.logging.Level;

public class ServerTest {
    public static void main(String[] args){
        ServerNetConnector net = new ServerNetConnector();
        net.startConnection(8181);
        String str = net.hasNextCommand();
        net.sendResponse("abcdefghijkmnlop");
        net.stopConnection();
    }
}
