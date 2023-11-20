package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class FileGenerator {
    public static void main(String[] args) throws IOException {
        String directoryPath = "src/_3p95_FILES"; // Replace with your directory path
        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            String filename = directoryPath + "/file" + i + ".txt";
            long fileSize = 5 * 1024 + random.nextInt(100 * 1024 * 1024); // Size between 5KB and 100MB
            try (FileOutputStream fos = new FileOutputStream(filename)) {
                byte[] buffer = new byte[1024];
                long written = 0;
                while (written < fileSize) {
                    random.nextBytes(buffer);
                    long bytesToWrite = Math.min(buffer.length, fileSize - written);
                    fos.write(buffer, 0, (int) bytesToWrite);
                    written += bytesToWrite;
                }
            }
        }
    }
}
