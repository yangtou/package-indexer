package com.example.packageindexer.server;


import com.example.packageindexer.message.MessageHandler;
import com.example.packageindexer.model.MessageHandleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class WebServer {

    private final MessageHandler messageHandler;
    private ThreadPoolExecutor threadPoolExecutor;


    @Autowired
    public WebServer(MessageHandler messageHandler,
                     @Value("${app.port}") final int port,
                     @Value("${app.threadPoolSize}") final int threadPoolSize) {
        this.messageHandler = messageHandler;
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);
        startServer(port);
    }

    private void startServer(final int port) {
        // open socket
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            // accept multiple users
            while(true) {
                Socket clientSocket = serverSocket.accept();
                threadPoolExecutor.execute(() -> {
                    try {
                        processConnection(clientSocket);
                    } catch (IOException e) {
                        System.out.println("Failed to close client connection");
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processConnection(final Socket clientSocket) throws IOException {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("Client " + clientSocket.getRemoteSocketAddress().toString() + " connected");
            out.println("You are connected to PackageIndexer server!");
            String inputLine;

            // accept multiple messages from one user
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Processing message from: " + clientSocket.getRemoteSocketAddress().toString());
                MessageHandleResult result = messageHandler.handleMessage(inputLine);
                out.println("Your message process is: " + result.getResult().toString() + "\nResponse is: " + result.getReason());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            clientSocket.close();
            System.out.println(clientSocket.getRemoteSocketAddress().toString() + " disconnected");
        }
    }
}

