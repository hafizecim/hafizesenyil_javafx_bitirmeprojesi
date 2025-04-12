package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationLogger {
    // Dosyaya yazma
    public void logNotification(String message) {
        String fileName = "notifications.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
            writer.write(message + " - " + timestamp);
            writer.newLine();  // Yeni satıra geç
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

