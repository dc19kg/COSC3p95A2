package org.example;


import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class FileReceiverServer {

    // Creates a ForkJoinPool for handling tasks in parallel.
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static void main(String[] args) throws IOException {



        // Sets up a server socket to listen on port 1234.
        ServerSocket serverSocket = new ServerSocket(1234);
        // Directory where received files will be saved.
        String saveDirectory = "src/new_files";
        new File(saveDirectory).mkdirs();

        while (true) {
            // Accepts incoming connections and handles them using ForkJoinPool.
            Socket clientSocket = serverSocket.accept();
            forkJoinPool.execute(new FileTransferTask(clientSocket, saveDirectory));
        }
    }
}
