package ru.senina.itmo.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

//TODO: Обработать ошибки
public class ServerNetConnector {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(int port){
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            Logging.log(Level.INFO, "Connection was accepted.");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e){
            //TODO: обработаьь ошибки UnknownHostException
            throw new RuntimeException(e);
        }
    }

    public String hasNextCommand() {
        String line;
        try {
            line  = in.readLine();
        }catch (Exception e){
            //TODO: обработаьь ошибки
            System.out.println(e.toString());
            throw new RuntimeException(e);
        }
        Logging.log(Level.INFO, "Received message: '" + line + "'.");
        return line;
    }

    public void sendResponse(String str){
        byte[] byteMessage = str.getBytes(StandardCharsets.UTF_8);
        out.println(str);
        Logging.log(Level.INFO, "Message '" + str + "' was send.");
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            Logging.log(Level.INFO, "Connection was closed.");
        } catch (IOException e){
            //TODO: обработаьь ошибки
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfConnectionClosed(){
        return serverSocket.isClosed();
    }
}
