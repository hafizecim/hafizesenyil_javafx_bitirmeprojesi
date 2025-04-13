package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.NotebookDTO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//
// NoteEkleFormController, kullanıcı tarafından yeni bir not oluşturulmasını veya mevcut notun düzenlenmesini sağlar.
// Bu form bir dialog (popup pencere) olarak açılır.
//
// Bu sınıf, "Yeni Not Ekle" formunun kontrolünü sağlıyor.
// note-ekle-form.fxml dosyasıyla bire bir bağlı çalışıyor.
//

public class NoteEkleFormController {

    // =============== FXML UI Öğeleri ===============
    @FXML private TextField titleField;         // Not başlığı girilen alan
    @FXML private TextArea contentArea;         // Not açıklaması yazılan alan
    @FXML private ComboBox<String> categoryBox; // Kategori seçimi
    @FXML private CheckBox pinnedCheck;         // Sabitlenmiş mi?
    @FXML private Label dateLabel;              // Oluşturulma tarihi (salt-okunur)

    // =============== Değişkenler ===============
    private NotebookDTO note;    // Not nesnesi
    private boolean saved = false; // Kaydedildi mi?

    // =============== Not set etme (yeni veya düzenleme modu) ===============
    public void setNote(NotebookDTO note) {
        this.note = note != null ? note : new NotebookDTO();    // Eğer null ise yeni not oluştur
        categoryBox.getItems().setAll("İş", "Kişisel", "Okul"); // Kategori seçeneklerini yükle

        // Tarihi bugünün tarihi olarak ayarla
        LocalDateTime now = LocalDateTime.now();
        dateLabel.setText(now.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")));

        // Düzenleme moduysa mevcut bilgileri alanlara yaz
        if (note != null && note.getTitle() != null) {
            titleField.setText(note.getTitle());
            contentArea.setText(note.getContent());
            categoryBox.setValue(note.getCategory());
            pinnedCheck.setSelected(note.isPinned());
        }
    }

    // Kaydedildi mi bilgisi (dışarıdan erişim)
    public boolean isSaved() {
        return saved;
    }

    // Not nesnesini dışarıya döndür
    public NotebookDTO getNote() {
        return note;
    }

    // =============== 💾 Notu Kaydet ===============
    @FXML
    private void saveNote() {
        // Zorunlu alan kontrolü
        if (titleField.getText().isBlank() || categoryBox.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Başlık ve kategori boş olamaz!").show();
            return;
        }

        // Verileri DTO'ya aktar
        note.setTitle(titleField.getText().trim());
        note.setContent(contentArea.getText().trim());
        note.setCategory(categoryBox.getValue());
        note.setPinned(pinnedCheck.isSelected());
        note.setCreatedDate(LocalDateTime.now());

        saved = true; // Kaydedildi işaretle

        ((Stage) titleField.getScene().getWindow()).close(); // Pencereyi kapat
    }

    // =============== ❌ İptal ===============
    @FXML
    private void cancel() {
        ((Stage) titleField.getScene().getWindow()).close(); // Pencereyi kapat
    }
}
