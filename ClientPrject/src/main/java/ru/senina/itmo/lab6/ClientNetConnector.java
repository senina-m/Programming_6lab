package ru.senina.itmo.lab6;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

//TODO: Обработать ошибки
public class ClientNetConnector {
    private int ServerPort;
    //TODO: передавать как-то не хардкодить разобраться с хостами
    private SocketChannel serverSocketChannel;
    private Selector selector;

    public void startConnection(String host, int serverPort) {
        try {
            selector = Selector.open();
            serverSocketChannel = SocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE | SelectionKey.OP_READ);
            serverSocketChannel.connect(new InetSocketAddress(host, serverPort));
            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid() || !key.isConnectable()) continue;
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                        channel.configureBlocking(false);
                    }
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }


    public void sendMessage(String msg) {
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        try {
            ByteBuffer outBuffer = ByteBuffer.wrap(bytes);
            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (!key.isValid() || !key.isWritable()) continue;
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.write(outBuffer);
                    if (outBuffer.remaining() < 1)
                        return;
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
        try {
            //TODO: sleep for while-true
            while (true) {
                selector.select();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
                        buffer.clear();
                        clientSocketChannel.read(buffer);
                        int len = buffer.getInt();
                        while (buffer.position() < len + 4) {
                            clientSocketChannel.read(buffer);
                        }
                        return new String(buffer.array(), 4, buffer.position());
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
        } catch (IOException e) {
            //TODO: Обработать ошибку
            throw new RuntimeException(e);
        }
    }
}