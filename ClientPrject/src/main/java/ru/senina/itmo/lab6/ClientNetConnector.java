package ru.senina.itmo.lab6;


import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

//TODO: Обработать ошибки
public class ClientNetConnector {
    //TODO: передавать как-то не хардкодить разобраться с хостами
    private SocketChannel serverSocketChannel;
    private Selector selector;
    private final boolean debug = true;

    public void startConnection(String host, int serverPort) {
        try {
            selector = Selector.open();
            if (debug) {
                System.out.println("DEBUG: Selector is created!");
            }
            serverSocketChannel = SocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
            serverSocketChannel.connect(new InetSocketAddress(host, serverPort));
            if (debug) {
                System.out.println("DEBUG: Starting to connect!");
            }
            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid() && key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                            if (debug) {
                                System.out.println("DEBUG: Finished to connect! Client is connected to server!");
                            }
                        }
                        return;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }


    public void sendMessage(String msg) {
        if (debug) {
            System.out.println("DEBUG: Sending of a message started!");
        }
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        try {
            ByteBuffer outBuffer = ByteBuffer.wrap(bytes);
            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid() && key.isWritable()) {
                        if (debug) {
                            System.out.println("DEBUG: There is a key to write!");
                        }
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.write(outBuffer);
                        if (outBuffer.remaining() < 1) {
                            if (debug) {
                                System.out.println("DEBUG: Writing is finished!");
                            }
                            return;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }

    /**
     * @return NULLABLE if there was no answer
     */
    public String receiveMessage() {
        if (debug) {
            System.out.println("DEBUG: Reading started!");
        }
        try {
            //TODO: sleep for while-true
            while (true) {
                selector.select();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
//                    if (debug) {
//                        System.out.println("DEBUG: Key: " + selectionKey.readyOps());
//                    }
                    if (selectionKey.isReadable()) {
                        if (debug) {
                            System.out.println("DEBUG: Reading there was a key to read!");
                        }
                        SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
                        StringBuilder message = new StringBuilder();
                        while (clientSocketChannel.read(buffer) > 0){
                            message.append(new String(buffer.array(), 0, buffer.position()));
                            buffer.compact();
                        }
//                        buffer.clear();
//                        clientSocketChannel.read(buffer);
//                        int len = buffer.getInt();
//                        if (debug) {
//                            System.out.println("DEBUG: Len of message is: " + len + "!");
//                        }
//                        while (buffer.position() < len + 4) {
//                            clientSocketChannel.read(buffer);
//                        }
                        //IF DEBUG
//                            System.out.print("DEBUG: array of read bytes: ");
//                            for (int i = 3; i < buffer.position() - 4; i++) {
//                                System.out.print(buffer.array()[i] + " ");
//                            }
//                            System.out.println();
//                        }
//                        return new String(buffer.array(), 4, buffer.position() - 4);
                        if (debug) {
                            System.out.println("DEBUG: Reading is finished!");
                        }
                        return message.toString();
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }

    public void stopConnection() {
        try {
            serverSocketChannel.close();
            if (debug) {
                System.out.println("DEBUG: Connection is closed!");
            }
        } catch (IOException e) {
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }
}