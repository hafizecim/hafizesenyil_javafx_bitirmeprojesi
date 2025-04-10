package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils;


import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dao.NotificationDAO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.NotificationDTO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.enums.NotificationMessageType;
import javafx.scene.control.Alert;

import java.util.List;

public class NotificationManager {

    // NotificationDAO nesnesi
    private static final NotificationDAO notificationDAO = new NotificationDAO();

    // Bildirimleri gösterme ve kaydetme
    public static void showNotification(String message, NotificationMessageType type) {
        // Bildirimi DAO'ya kaydet
        notificationDAO.addNotification(message, type);

        // Pop-up bildirimini göster
        Alert alert = createAlert(type, message);

        alert.showAndWait();
    }

    // Bildirim türüne göre uygun Alert oluşturma
    private static Alert createAlert(NotificationMessageType type, String message) {
        Alert alert;

        // Türüne göre uygun alert tipi seçiyoruz
        switch (type) {
            case SUCCESS:
                alert = new Alert(Alert.AlertType.INFORMATION);
                break;
            case ERROR:
                alert = new Alert(Alert.AlertType.ERROR);
                break;
            case WARNING:
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            default:
                alert = new Alert(Alert.AlertType.NONE);
                break;
        }

        // Alert penceresinin başlık ve içeriğini ayarlama
        alert.setTitle("Bildirim");
        alert.setHeaderText(null);  // Başlık kısmı boş
        alert.setContentText(message);

        return alert;
    }

    // Tüm bildirimleri alır
    public static List<NotificationDTO> getAll() {
        return notificationDAO.getAllNotifications();
    }
}

