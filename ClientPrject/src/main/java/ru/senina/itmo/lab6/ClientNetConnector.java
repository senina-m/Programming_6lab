package ru.senina.itmo.lab6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

//TODO: Обработать ошибки
public class ClientNetConnector {
    private int clientPort;
    //TODO: передавать как-то не хардкодитьб разобраться с хостами
    private final int serverPort = 5660;
    private ByteBuffer buffer;
    private SocketChannel socketChannel;
    private final int bufferCapacity = 1024;

    public void startConnection(String ip, int clientPort) {
        try {
            this.clientPort = clientPort;
            socketChannel = SocketChannel.open(new InetSocketAddress(ip, clientPort));
            socketChannel.configureBlocking(false);
            buffer = ByteBuffer.allocate(bufferCapacity);
        } catch (IOException e) {
            //TODO: Обработать ошибку
        }
    }

    public void sendMessage(String msg) {
        try {
            //TODO: подумать про кодировку
            byte[] byteMessage = msg.getBytes(StandardCharsets.UTF_8);
            buffer.clear();
            buffer.put(byteMessage);
            buffer.flip();
            socketChannel.write(buffer);
        } catch (IOException e) {
            //TODO: Обработать ошибку
        }
    }

    /**
     * @return NULLABLE if there was no answer
     * @throws IOException if there were some problems with buffer
     */
    public String receiveMessage() {
        try {
            if (socketChannel.read(buffer) > 0) {
                return new String(buffer.array(), 0, buffer.position());
            }
        } catch (IOException e) {
            //TODO: Обработать ошибку
        }

        return null;
    }

    public void stopConnection() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            //TODO: Обработать ошибку
        }
    }
}