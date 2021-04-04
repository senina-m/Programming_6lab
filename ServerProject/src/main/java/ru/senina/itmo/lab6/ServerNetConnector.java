package ru.senina.itmo.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

//TODO: Обработать ошибки
public class ServerNetConnector {
    private int serverPort = 5660;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(int port){
        try {
            this.serverPort = port;
            serverSocket = new ServerSocket(serverPort);
            clientSocket = serverSocket.accept();
            Logging.log(Level.INFO, "Connection was accepted.");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e){
            //TODO: обработаьь ошибки UnknownHostException
        }
    }

    public String hasNextCommand() {
        String line = "";
        try {
            line  = in.readLine();
        }catch (IOException e){
            //TODO: обработаьь ошибки
        }
        return line;
    }

    public void sendResponse(String str){
        out.println(str);
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
        }
    }

    public boolean checkIfConnectionClosed(){
        return serverSocket.isClosed();
    }
}
