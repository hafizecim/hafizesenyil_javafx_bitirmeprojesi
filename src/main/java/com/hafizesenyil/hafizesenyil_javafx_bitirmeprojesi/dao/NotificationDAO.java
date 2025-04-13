package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dao;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.NotificationDTO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.enums.NotificationMessageType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    private static final String FILE_PATH = "notification.txt"; // Bildirimleri kaydedeceğimiz dosya yolu
    private List<NotificationDTO> notifications = new ArrayList<>(); // Bildirimler listesi

    // Yeni bir bildirim ekleme
    public void addNotification(String message, NotificationMessageType notificationType) {
        NotificationDTO notification = new NotificationDTO();
        notification.setMessage(message);
        notification.setNotificationType(notificationType);
        notification.setTimestamp(LocalDateTime.now());

        // Bildirimi listeye ekle
        notifications.add(notification);

        // Bildirimi dosyaya kaydet
        saveToFile(notification);
    }

    // Tüm bildirimleri alır
    public List<NotificationDTO> getAllNotifications() {
        return notifications;
    }

    // Bildirimi dosyaya kaydetme
    private void saveToFile(NotificationDTO notification) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(notification.toString()); // NotificationDTO nesnesinin string halini yazdır
            writer.newLine(); // Yeni bir satır ekle
        } catch (IOException e) {
            System.err.println("Bildirim dosyaya kaydedilemedi: " + e.getMessage());
        }
    }

    // getNotificationByMessage metodu
    public NotificationDTO getNotificationByMessage(String message) {
        for (NotificationDTO notification : notifications) {
            if (notification.getMessage().equals(message)) {
                return notification;  // Mesajı bulduğunda, bildirimi döndür
            }
        }
        return null;  // Mesaj bulunmazsa null döndür
    }
}

