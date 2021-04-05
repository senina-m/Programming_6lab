package ru.senina.itmo.lab6;

public class ClientTest {
    public static void main(String[] args){
        ClientNetConnector net = new ClientNetConnector();
        net.startConnection("localhost", 8181);
        System.out.println(net.receiveMessage());
        net.sendMessage("b");
        net.stopConnection();
    }
}
