package ru.senina.itmo.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

//TODO: Обработать ошибку когда ждёт сообщения, а оно не приходит
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

    //TODO: подумать как быть, если таймаут истёк и нужен ли он вообще
    public String nextCommand(int timeout) throws TimeoutException{
        String line = null;
        try {
            while(line == null) {
                line = in.readLine();
                timeout--;
                if(timeout<0){
                    throw new TimeoutException("Reading time is out");
                }
            }
        }catch (IOException e){
            Logging.log(Level.WARNING, "Exception during nextCommand. " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        Logging.log(Level.INFO, "Received message: '" + line + "'.");
        return line;
    }

    public void sendResponse(String str){
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
            Logging.log(Level.WARNING, "Failed to stopConnection");
        }
    }

    public boolean checkIfConnectionClosed(){
        return serverSocket.isClosed();
    }

    public void reconnect(int port) {
        stopConnection();
        startConnection(port);
    }
}
