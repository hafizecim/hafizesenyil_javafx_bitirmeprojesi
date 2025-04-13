package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dao.UserDAO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.UserDTO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils.ERole;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils.FXMLPath;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils.SceneHelper;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils.SpecialColor;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * LoginController.java
 * Kullanıcının kullanıcı adı veya e-posta ve şifre ile giriş yapmasını sağlar.
 * Giriş başarılı olursa kullanıcı SessionManager üzerinden sisteme alınır ve rolüne göre ilgili panele yönlendirilir.
 * Giriş başarısız olursa hata mesajı gösterilir.
 * Ayrıca Enter tuşu ile login işlemini başlatır ve kayıt ekranına geçiş yapılmasını sağlar.
 */

public class LoginController {
    private UserDAO userDAO;

    public LoginController() {
        userDAO = new UserDAO();
    }

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void specialOnEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    @FXML
    public void login() {

        // Giriş alanlarındaki verileri al
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Veritabanında kullanıcıyı e-posta veya kullanıcı adına göre arar ve şifreyi doğrular
        Optional<UserDTO> optionalLoginUserDTO = userDAO.loginUser(username, password);

        // Eğer kullanıcı bulunduysa giriş başarılıdır
        if (optionalLoginUserDTO.isPresent()) {
            UserDTO userDTO = optionalLoginUserDTO.get();

            // 🔐 Giriş yapan kullanıcı SessionManager ile oturuma alınır
            SessionManager.setCurrentUser(userDTO);

            // Kullanıcıya başarılı giriş mesajı gösterilir
            showAlert("Başarılı", "Giriş Başarılı: " + userDTO.getUsername(), Alert.AlertType.INFORMATION);

            // Kullanıcının rolüne göre farklı ekranlara yönlendirilir
            if (userDTO.getRole() == ERole.ADMIN) {
                openAdminPane();
            } else {
                openUserHomePane();
            }

            // Giriş başarısızsa uyarı verilir
        } else {
            showAlert("Başarısız", "Giriş bilgileri hatalı", Alert.AlertType.ERROR);
        }
    }

    private void openUserHomePane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.USER_HOME));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Kullanıcı Paneli");
            stage.show();
        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Kullanıcı paneline yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Kullanıcı ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }



    private void openAdminPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.ADMIN));
            Parent parent = fxmlLoader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Admin Panel");
            stage.show();
        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Admin Sayfasına yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Admin ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void switchToRegister(ActionEvent actionEvent) {
        try {
            // 1.YOL
            /*
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXMLPath.REGISTER));
            Parent parent = fxmlLoader.load();
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle("Kayıt Ol");
            stage.show();
             */
            // 2.YOL
            SceneHelper.switchScene(FXMLPath.REGISTER, usernameField, "Kayıt Ol");
        } catch (Exception e) {
            System.out.println(SpecialColor.RED + "Register Sayfasına yönlendirme başarısız" + SpecialColor.RESET);
            e.printStackTrace();
            showAlert("Hata", "Kayıt ekranı yüklenemedi", Alert.AlertType.ERROR);
        }
    }

    public static class MainController {

        // Header bölümündeki değişkenler
        @FXML
        private Label headerLabel;
        @FXML
        private Button darkModeButton;
        @FXML
        private Button notificationButton;
        @FXML
        private Button backupButton;
        @FXML
        private Button restoreButton;
        @FXML
        private Button notebookButton;
        @FXML
        private Button profileButton;
        @FXML
        private Button logoutButton;

        // Menü bölümündeki değişkenler
        @FXML
        private Menu menuFile;
        @FXML
        private MenuItem menuItemExit;

        @FXML
        private Menu menuUser;
        @FXML
        private MenuItem menuItemAddUser;
        @FXML
        private MenuItem menuItemUpdateUser;
        @FXML
        private MenuItem menuItemDeleteUser;

        @FXML
        private Menu menuKdv;
        @FXML
        private MenuItem menuItemAddKdv;
        @FXML
        private MenuItem menuItemUpdateKdv;
        @FXML
        private MenuItem menuItemDeleteKdv;

        @FXML
        private Menu menuOther;
        @FXML
        private MenuItem menuItemCalculator;
        @FXML
        private MenuItem menuItemNotebook;

        @FXML
        private Menu menuHelp;
        @FXML
        private MenuItem menuItemAbout;

        // Kullanıcı ve KDV ile ilgili butonlar
        @FXML
        private Label userTitleLabel;
        @FXML
        private Button btnAddUser;
        @FXML
        private Button btnUpdateUser;
        @FXML
        private Button btnDeleteUser;
        @FXML
        private Button btnPrintUser;

        @FXML
        private Button btnAddKdv;
        @FXML
        private Button btnUpdateKdv;
        @FXML
        private Button btnDeleteKdv;

        // Footer ve dil butonu
        @FXML
        private Label kdvTitleLabel;
        @FXML
        private Label footerLabel;
        @FXML
        private Button languageButton;

        // Diğer işlevsellikler burada yer alabilir
    }
}

