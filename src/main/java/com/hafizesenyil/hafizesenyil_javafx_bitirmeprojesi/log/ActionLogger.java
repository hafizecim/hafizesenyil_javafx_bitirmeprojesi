package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.log;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.paint.Color; // Bu import şart

public class ActionLogger {
    private static final String LOG_FOLDER = "logs";
    private static final String LOG_FILE = LOG_FOLDER + "/actions.log";

    /*
    public enum LogType {
        INFO, WARNING, ERROR, SUCCESS, BUTTON
    }
*/
    public enum LogType {
        INFO(Color.GRAY),
        WARNING(Color.ORANGE),
        ERROR(Color.RED),
        SUCCESS(Color.GREEN),
        BUTTON(Color.BLUE);

        private final Color fxColor;

        LogType(Color fxColor) {
            this.fxColor = fxColor;
        }

        public Color getFxColor() {
            return fxColor;
        }
    }
    public static void log(String username, String message, LogType type) {
        try {
            // Klasör kontrolü
            File logDir = new File(LOG_FOLDER);
            if (!logDir.exists()) {

                boolean dirCreated = logDir.mkdirs();
                System.out.println("📁 logs klasörü oluşturuldu mu? " + dirCreated);
            }

            // Dosya kontrolü
            File logFile = new File(LOG_FILE);
            if (!logFile.exists()) {

                boolean fileCreated = logFile.createNewFile();
                System.out.println("📄 Log dosyası oluşturuldu mu? " + fileCreated);
            }

            // Tarih formatı
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String formattedTime = LocalDateTime.now().format(formatter);

            // Log yaz
            try (FileWriter writer = new FileWriter(logFile, true)) {
                writer.write(type + "|" + username + "|" + message + "|Zaman: " + formattedTime + "\n");
                System.out.println("📁 Log yazıldı: " + logFile.getAbsolutePath());

            }

        } catch (IOException e) {
            System.out.println("❌ Log yazılamadı:");
            e.printStackTrace();
        }
    }
}

