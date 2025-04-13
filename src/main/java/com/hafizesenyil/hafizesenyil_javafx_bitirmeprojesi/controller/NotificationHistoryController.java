package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dao.NotificationDAO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.NotificationDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class NotificationHistoryController {
    @FXML
    private TextArea notificationArea;

    @FXML
    private ListView<String> notificationListView; // Bildirimleri listeleyecek ListView

    @FXML
    private TextArea notificationDetailsTextArea; // Seçilen bildirimin detaylarını gösterecek TextArea

    private ObservableList<String> notificationList = FXCollections.observableArrayList(); // Bildirimler için ObservableList

    private NotificationDAO notificationDAO = new NotificationDAO(); // NotificationDAO sınıfından bir nesne

    public void initialize() {
        loadNotifications(); // Bildirimleri yükle
    }

    // Bildirimleri NotificationDAO'dan al ve listeye ekle
    private void loadNotifications() {
        List<NotificationDTO> notifications = notificationDAO.getAllNotifications(); // Tüm bildirimleri al
        for (NotificationDTO notification : notifications) {
            notificationList.add(notification.getMessage()); // Bildirimin mesajını listeye ekle
        }
        notificationListView.setItems(notificationList); // ListView'e bildirimleri ekle
        setNotificationDetails(); // Bildirim detaylarını gösterme işlevini tetikle
    }

    // Kullanıcı bir bildirim seçtiğinde detaylarını göster
    private void setNotificationDetails() {
        notificationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Seçilen bildirimin detaylarını göster
                NotificationDTO selectedNotification = notificationDAO.getNotificationByMessage(newValue);
                notificationDetailsTextArea.setText(selectedNotification.getDetails());
            }
        });
    }
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) notificationArea.getScene().getWindow();
        stage.close();
    }

    // ******* 🔔 Bildirimler butonu için ************
    @FXML
    private TextArea notificationTextArea;  // Bu, veriyi gösterecek olan TextArea

    // Bildirimleri dosyadan okuma ve ekrana aktarma
    @FXML
    private void showNotifications() {
        String fileName = "notifications.txt";
        StringBuilder notifications = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notifications.append(line).append("\n");  // Her satırı yeni bir satır olarak ekle
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        notificationTextArea.setText(notifications.toString());  // TextArea'ya veriyi aktar
    }
}
