package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.enums.NotificationMessageType;

import java.time.LocalDateTime;

public class NotificationDTO {

    private String message;               // Bildirim mesajı
    private NotificationMessageType notificationType;  // Bildirim türü (örneğin: SUCCESS, ERROR)
    private LocalDateTime timestamp;      // Bildirim zamanı

    // Parametresiz constructor
    public NotificationDTO() {}

    // Parametreli constructor
    public NotificationDTO(String message, NotificationMessageType notificationType, LocalDateTime timestamp) {
        this.message = message;
        this.notificationType = notificationType;
        this.timestamp = timestamp;
    }

    // Getter ve Setter metodları

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationMessageType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationMessageType notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    // Detayları döndüren metot
    public String getDetails() {
        return "Mesaj: " + message + "\nTür: " + notificationType + "\nZaman: " + timestamp;
    }

    // toString metodu, nesnenin içeriğini string formatında döner
    @Override
    public String toString() {
        return "Bildirim: " + message + " | Tür: " + notificationType + " | Zaman: " + timestamp;
    }
}

