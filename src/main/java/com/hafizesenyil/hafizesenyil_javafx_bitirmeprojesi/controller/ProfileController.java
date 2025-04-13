package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.UserDTO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * ProfileController.java
 * Bu sınıf, kullanıcı profili penceresinin kontrolünü sağlar.
 * Giriş yapan kullanıcıyı SessionManager üzerinden alır
 * ve FXML içindeki Label'lara bilgileri yansıtır.
 */
public class ProfileController {

    // Kullanıcı adı Label'ı (FXML üzerinden bağlanır)
    @FXML private Label lblUsername;

    // Kullanıcının e-posta adresini gösterecek Label
    @FXML private Label lblEmail;

    // Kullanıcının sistemdeki rolünü gösterecek Label
    @FXML private Label lblRole;

    /**
     * Pencere yüklendiğinde çalışır.
     * Giriş yapan kullanıcıyı oturumdan alır ve etiketlere bilgilerini yerleştirir.
     */
    @FXML
    public void initialize() {
        UserDTO user = SessionManager.getCurrentUser();

        if (user != null) {
            lblUsername.setText(user.getUsername());
            lblEmail.setText(user.getEmail());
            lblRole.setText(user.getRole().name());
        }
    }

    /**
     * Kapat butonuna tıklanınca bu metot çalışır.
     * Aktif olan pencereyi kapatır.
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
