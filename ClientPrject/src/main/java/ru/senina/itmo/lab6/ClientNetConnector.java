package ru.senina.itmo.lab6;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//TODO: Обработать ошибки
public class ClientNetConnector {
    private int ServerPort;
    //TODO: передавать как-то не хардкодитьб разобраться с хостами
    private PrintStream output;
    private BufferedReader input;
    private Socket socket;

    public void startConnection(String ipOrHost, int serverPort) {
        try {
            this.ServerPort = serverPort;
            socket = new Socket("localhost", 8181);
            output = new PrintStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String msg) {
        output.println(msg);
    }

    /**
     * @return NULLABLE if there was no answer
     */
    public String receiveMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }

    public void stopConnection() {
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }
}