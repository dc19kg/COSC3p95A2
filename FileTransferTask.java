package org.example;

import java.net.Socket;
import java.util.concurrent.RecursiveAction;
import java.io.*; // Importing I/O classes

public class FileTransferTask extends RecursiveAction {
    // Fields for the socket and save directory
    private Socket clientSocket;
    private String saveDirectory;

    // Constructor to initialize the fields
    FileTransferTask(Socket clientSocket, String saveDirectory) {
        this.clientSocket = clientSocket;
        this.saveDirectory = saveDirectory;
    }

    @Override

    protected void compute() {
        try {
            // Prepare to receive file data
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

            // Read file metadata (like file name and size)
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();

            // Set up file output stream to save the received file
            File outputFile = new File(saveDirectory, fileName);
            FileOutputStream fos = new FileOutputStream(outputFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // Read file content and write to file
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalRead = 0;
            while (totalRead < fileSize) {
                bytesRead = dis.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                bos.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }

            // Closing resources
            bos.close();
            dis.close();
            clientSocket.close();

        } catch (IOException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
    }
}



