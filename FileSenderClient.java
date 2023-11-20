package org.example;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSenderClient {
    public static void main(String[] args) throws IOException {
        // Directory where files to be sent are located.
        File folder = new File("src/_3p95_FILES");
        for (File file : folder.listFiles()) {
            try (Socket socket = new Socket("localhost", 1234);
                 FileInputStream fis = new FileInputStream(file);
                 BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                 DataOutputStream dos = new DataOutputStream(bos)) {

                // Sends file metadata (name and size) to the server.
                dos.writeUTF(file.getName());
                dos.writeLong(file.length());

                // Sends file content to the server.
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                // Handle exceptions appropriately
                e.printStackTrace();
                throw new RuntimeException("Error during file transfer", e);
            }
        }
    }
}
