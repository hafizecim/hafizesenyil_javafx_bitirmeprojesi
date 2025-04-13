package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.log;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dao.UserDAO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.UserDTO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.session.SessionManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static class UserLogger {

        private final UserDAO userDao;

        public UserLogger(UserDAO userDao) {
            this.userDao = userDao;
        }

        public void logYaz(String mesaj) {
            String username = getUserUsername();

            try {
                Files.createDirectories(Paths.get("logs"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                try (FileWriter writer = new FileWriter("logs/log.txt", true)) {
                    writer.write(LocalDateTime.now().format(formatter) + " - Kullanıcı: " + username + " - " + mesaj + "\n");
                }

            } catch (IOException e) {
                System.err.println("❌ Log dosyasına yazılamadı: " + e.getMessage());
            }
        }

        private String getUserUsername() {
            UserDTO user = SessionManager.getCurrentUser();
            return (user != null) ? user.getUsername() : "Unknown";
        }
    }
}

