package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class NotificationController {

    @FXML
    private ListView<TextFlow> logListView;

    private static final String LOG_FILE = "logs/actions.log";

    private Color getColorFromLogType(String type) {
        switch (type.toUpperCase()) {
            case "ERROR":
                return Color.RED;
            case "INFO":
                return Color.GRAY;
            case "SUCCESS":
                return Color.GREEN;
            case "WARNING":
                return Color.ORANGE;
            case "BUTTON":
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }

    @FXML
    public void initialize() {
        File file = new File("logs/actions.log");
        if (!file.exists()) {
            System.out.println("❗ Log dosyası bulunamadı!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                String typeText = parts[0].trim().toUpperCase();
                String user = parts[1].trim();
                String message = parts[2].trim();
                String time = parts[3].trim();

                Color color = getColorFromLogType(typeText);  // Renk burada
                Text text = new Text(user + " kullanıcısı " + message + " – " + time + "\n");

                text.setFill(color); // JavaFX rengi
                text.setStyle("-fx-fill: " + toWebColor(color) + ";"); // CSS stili de ekle
                System.out.println("🎨 Renk uygulanıyor: " + color);
                System.out.println("Satır: " + line);

                logListView.getItems().add(new TextFlow(text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hücreler renkli görünsün
        logListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TextFlow item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || item == null ? null : item);
            }
        });
    }

    // JavaFX Color'ı Web formatına çevir (#RRGGBB)
    private String toWebColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
