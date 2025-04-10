package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils.LanguageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.Locale;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.textProperty().bind(LanguageManager.getBinding().getStringBinding("welcome"));
    }

    @FXML
    private void onLanguageChange() {
        String selected = languageComboBox.getValue();
        if ("English".equals(selected)) {
            LanguageManager.changeLanguage(new Locale("en", "US"));
        } else {
            LanguageManager.changeLanguage(new Locale("tr", "TR"));
        }
    }

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Button exitButton;

    @FXML
    private Label greetingLabel;

    @FXML
    public void initialize() {
        // Etiket ve butonu dil dosyasındaki anahtarlara bağla
        greetingLabel.textProperty().bind(LanguageManager.getBinding().getStringBinding("greeting"));
        exitButton.textProperty().bind(LanguageManager.getBinding().getStringBinding("exit"));
        welcomeText.textProperty().bind(LanguageManager.getBinding().getStringBinding("welcome"));
        // ComboBox'a dil seçeneklerini ekle
        languageComboBox.getItems().addAll("Türkçe", "English");
        languageComboBox.setValue("Türkçe"); // Varsayılan olarak Türkçe göster
    }

}