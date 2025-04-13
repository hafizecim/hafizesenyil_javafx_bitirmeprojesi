package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.UserDTO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

/**
 * 👤 Kullanıcı Profil Penceresi Kontrolcüsü
 * Bu sınıf, kullanıcı profili penceresinin kontrolünü sağlar.
 * Giriş yapan kullanıcıyı SessionManager üzerinden alır
 * ve FXML içindeki Label'lara bilgileri yansıtır.
 */
public class ProfileController {

    // FXML üzerinden bağlanan bileşenler
    // Kullanıcı adı Label'ı (FXML üzerinden bağlanır)
    @FXML private Label lblUsername;

    // Kullanıcının e-posta adresini gösterecek Label
    @FXML private Label lblEmail;

    // Kullanıcının sistemdeki rolünü gösterecek Label
    @FXML private Label lblRole;

    // profil resmi yüklemesini
    @FXML
    private ImageView profileImageView;



    /**
     * Ekran açıldığında otomatik olarak çağrılır.
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
        // Profil resmini yükle
        try {
            Image image = new Image(getClass().getResourceAsStream(
                    "/com/hafizesenyil/hafizesenyil_javafx_bitirmeprojesi/images/my-profile.png"));
            profileImageView.setImage(image);

            // ✅ radius tanımlanmalı
            double radius = 60;

            // 🔵 Profil resmini daire şeklinde göstermek için Clip uygula
            Circle clip = new Circle(60, 60, 60); // (x, y, radius)
            profileImageView.setClip(clip);

            // 🌟 Kenarlık ve gölge efekti
            Circle borderCircle = new Circle(radius, radius, radius);
            borderCircle.setStyle("-fx-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0.2, 0, 2);");

            // Profil resmi ve daireyi aynı grup içinde göstermek için Parent olarak ayarlanmalıysa ayrıca yapılabilir.
            // Ancak bu durumda genelde Group ya da StackPane içine alınması gerekir. Alternatif olarak CSS ile yapabiliriz.
        } catch (Exception e) {
            System.out.println("⚠️ Profil resmi yüklenemedi: " + e.getMessage());
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
