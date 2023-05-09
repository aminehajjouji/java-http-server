package com.httpserver.core;

import com.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListnerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    public ServerListnerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);

    }

    @Override
    public void run() {
        try {
            while(serverSocket.isBound() && !serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                LOGGER.info("Connection accepted from " + socket.getInetAddress());
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.run();
            }

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Problem with setting up socket", e);
        }finally{
            if(serverSocket != null ){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
