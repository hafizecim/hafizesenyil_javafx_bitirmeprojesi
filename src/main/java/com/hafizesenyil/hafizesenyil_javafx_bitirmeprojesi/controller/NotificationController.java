package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.log.ActionLogger;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NotificationController {
    @FXML
    private ListView<TextFlow> logListView; // Logları listelemek için ana liste

    @FXML
    private TextField searchField; // Kullanıcı adına göre arama yapılacak alan

    @FXML
    private Label logSummaryLabel;

    private static final String LOG_FILE = "logs/actions.log"; // Log dosyasının konumu

    // Log türüne göre uygun renk döndüren yardımcı metot
    private Color getColorFromLogType(String type) {
        switch (type.toUpperCase()) {
            case "ERROR": return Color.RED;
            case "INFO": return Color.GRAY;
            case "SUCCESS": return Color.GREEN;
            case "WARNING": return Color.ORANGE;
            case "BUTTON": return Color.BLUE;
            default: return Color.BLACK;
        }
    }

    // JavaFX Color nesnesini CSS renk formatına çevirir
    private String toWebColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    // Sayfa yüklendiğinde çalışan metot
    @FXML
    public void initialize() {
        // İlk açılışta tüm logları yükle (filtre yok)
        loadLogs(null, false, false);

        // ListView hücrelerini özelleştir (renkli TextFlow'lar desteklenir)
        logListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TextFlow item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || item == null ? null : item);
            }
        });

        // Çift tıklamada log detaylarını göster
        logListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TextFlow selectedItem = logListView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    StringBuilder sb = new StringBuilder();
                    for (javafx.scene.Node node : selectedItem.getChildren()) {
                        if (node instanceof Text) {
                            sb.append(((Text) node).getText());
                        }
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Log Detayı");
                    alert.setHeaderText("Seçili Bildirim:");
                    alert.setContentText(sb.toString());
                    alert.showAndWait();
                }
            }
        });
    }

    // Logları filtreleme, hata türü ve tarihe göre sıralama ile birlikte yükler
    private void loadLogs(String filterUser, boolean onlyErrors, boolean sortByDateDesc) {
        logListView.getItems().clear(); // Mevcut liste temizlenir

        File file = new File(LOG_FILE);
        if (!file.exists()) {
            System.out.println("❗ Log dosyası bulunamadı: " + LOG_FILE);
            return;
        }

        List<String[]> allLogs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                String type = parts[0].trim();
                String user = parts[1].trim();
                if (onlyErrors && !type.equalsIgnoreCase("ERROR")) continue;
                if (filterUser != null && !user.toLowerCase().contains(filterUser.toLowerCase())) continue;

                allLogs.add(parts); // Şartları sağlayan loglar listeye eklenir
            }

            // Tarihe göre sıralama yapılacaksa çalışır
            if (sortByDateDesc) {
                allLogs.sort((a, b) -> {
                    try {
                        String timeA = a[3].replace("Zaman: ", "").trim();
                        String timeB = b[3].replace("Zaman: ", "").trim();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                        Date dateA = sdf.parse(timeA);
                        Date dateB = sdf.parse(timeB);
                        return dateB.compareTo(dateA); // En yeni en üste
                    } catch (ParseException e) {
                        return 0;
                    }
                });
            }

            // Sonuçları ekrana yazdır
            for (String[] parts : allLogs) {
                String type = parts[0].trim();
                String user = parts[1].trim();
                String message = parts[2].trim();
                String time = parts[3].trim();

                Color color = getColorFromLogType(type);
                Text text = new Text(user + " kullanıcısı " + message + " – " + time + "\n");
                text.setFill(color);
                text.setStyle("-fx-fill: " + toWebColor(color) + ";");
                logListView.getItems().add(new TextFlow(text));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 📊 Log Sayısı Göstergesi (Toplam, Hata, Uyarı Sayısı)
        // Sayıları hesapla
        int total = logListView.getItems().size();
        int errorCount = 0;
        int warningCount = 0;

        for (TextFlow flow : logListView.getItems()) {
            for (javafx.scene.Node node : flow.getChildren()) {
                if (node instanceof Text) {
                    String txt = ((Text) node).getText().toUpperCase();
                    if (txt.contains("ERROR")) errorCount++;
                    else if (txt.contains("WARNING")) warningCount++;
                }
            }
        }

        // Ekrana yaz (📊 Log Sayısı Göstergesi (Toplam, Hata, Uyarı Sayısı))
        logSummaryLabel.setText("Toplam: " + total + " | Hatalar: " + errorCount + " | Uyarılar: " + warningCount);

    }

    // Kullanıcı adına göre filtreleme yapılır
    @FXML
    private void FieldLogs() {
        String searchQuery = searchField.getText().trim();
        loadLogs(searchQuery, false, false);
    }

    // Sadece ERROR loglarını göster
    @FXML
    private void onlyErrorsCheck() {
        System.out.println("🪄 Hatalar filtresi tetiklendi");
        loadLogs(null, true, false);
    }

    // Tarihe göre sıralama (en yeni en üstte)
    @FXML
    private void sortByDateCheck() {
        loadLogs(null, false, true);
        System.out.println("⏰ Tarihe göre sıralama tetiklendi");
    }

    // Logları tamamen temizler (dosya ve ekranda)
    @FXML
    private void clearLogs() {
        try {
            File file = new File(LOG_FILE);
            if (file.exists()) {
                new FileWriter(file, false).close();
                System.out.println("🧹 Log dosyası temizlendi: " + LOG_FILE);
                logListView.getItems().clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Logları dışa CSV olarak aktarır
    @FXML
    private void exportLogs() {
        File sourceFile = new File(LOG_FILE);
        if (!sourceFile.exists()) {
            System.out.println("❌ Log dosyası bulunamadı.");
            return;
        }

        File exportFile = new File("logs/exported_logs.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             FileWriter writer = new FileWriter(exportFile)) {

            writer.write("Tür,Kullanıcı,Mesaj,Tarih\n"); // CSV başlık satırı

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                String type = parts[0].trim();
                String user = parts[1].trim();
                String message = parts[2].trim();
                String time = parts[3].replace("Zaman: ", "").trim();

                message = message.contains(",") ? "\"" + message + "\"" : message;

                writer.write(String.format("%s,%s,%s,%s\n", type, user, message, time));
            }

            System.out.println("📤 Loglar başarıyla dışa aktarıldı: " + exportFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}