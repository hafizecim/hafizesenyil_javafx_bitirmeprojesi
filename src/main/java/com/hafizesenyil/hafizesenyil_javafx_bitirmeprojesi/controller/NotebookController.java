package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.controller;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.NotebookDTO;
import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


// NotebookController, not defteri arayüzünü kontrol eden sınıftır.
// Kullanıcıların not oluşturma, silme, düzenleme ve filtreleme işlemleri burada yönetilir.
//
// Bu sınıf notebook.fxml ile bağlantılı çalışır.


public class NotebookController {

    // =============== FXML UI Öğeleri ===============
    @FXML private TextField searchField;                // Başlık aramak için alan
    @FXML private ComboBox<String> categoryFilter;      // Kategori filtresi (İş, Okul, Kişisel)
    @FXML private CheckBox pinnedFilter;                // Sadece sabitlenmiş notları gösterme seçeneği
    @FXML private ListView<NotebookDTO> noteListView;   // Notların listelendiği liste görünümü

    // =============== Veriler ===============
    private final ObservableList<NotebookDTO> notebookList = FXCollections.observableArrayList(); // Uygulamadaki tüm notları tutar
    private final UserDTO currentUser = new UserDTO(); // Demo kullanıcı (giriş sistemi entegre edilince değiştirilebilir)

    // =============== Ekran Açıldığnda Başlangıçta çalışan metod ===============
    @FXML
    public void initialize() {
        // Filtre kategorilerini tanımla
        categoryFilter.getItems().addAll("İş", "Kişisel", "Okul");

        // Örnek veriler ekle
        notebookList.addAll(
                new NotebookDTO(1L, "JavaFX Notları", "ListView kullanımı ve olaylar.", LocalDateTime.now(), null, "İş", false, currentUser),
                new NotebookDTO(2L, "Yapılacaklar", "Sunum hazırlanacak.", LocalDateTime.now(), null, "Kişisel", true, currentUser)
        );


        // Notları(Listeyi) ListView'e bağla
        noteListView.setItems(notebookList);

        // ListView öğelerini kişiselleştir (başlık, kategori, tarih, 📌 simgesi)
        noteListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(NotebookDTO note, boolean empty) {
                super.updateItem(note, empty);
                if (empty || note == null) {
                    setText(null);
                } else {
                    String pinIcon = note.isPinned() ? "📌 " : "";
                    setText(pinIcon + note.getTitle() + " [" + note.getCategory() + "] - " + note.getCreatedDate().toLocalDate());
                }
            }
        });
    }

    // =============== ➕ Yeni Not Ekleme ===============
    @FXML
    private void addNote() {
        try {
            // Not ekleme formunu yükle
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hafizesenyil/hafizesenyil_javafx_bitirmeprojesi/view/note-ekle-form.fxml"));
            DialogPane pane = loader.load();

            NoteEkleFormController formController = loader.getController();
            formController.setNote(null); // yeni not oluşturuluyor

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Yeni Not");
            dialog.showAndWait();

            if (formController.isSaved()) {
                NotebookDTO newNote = formController.getNote();
                newNote.setId(System.currentTimeMillis()); // ID otomatik atanıyor
                newNote.setUserDTO(currentUser);           // Demo kullanıcı atanıyor
                notebookList.add(newNote);                 // Listeye ekleniyor
            }

        } catch (Exception e) {
            e.printStackTrace(); // Geliştirici için hata takibi
        }
    }

    // =============== 🗑️ Not Silme (Seçili) ===============//
    @FXML
    private void deleteNote() {
        NotebookDTO selected = noteListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Kullanıcıya silme onayı göster
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Notu silmek istediğinize emin misiniz?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    notebookList.remove(selected); // Not silinir
                }
            });
        }
    }

    // =============== 📝 Not Düzenleme (Seçili) ===============//
    @FXML
    private void editNote() {
        NotebookDTO selected = noteListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        // İçeriği güncellemek için kullanıcıya dialog göster
        TextInputDialog dialog = new TextInputDialog(selected.getContent());
        dialog.setTitle("Not Düzenle");
        dialog.setHeaderText("Başlık: " + selected.getTitle());
        dialog.setContentText("Yeni içerik:");

        dialog.showAndWait().ifPresent(newContent -> {
            // Yeni içerikle güncelle
            selected.setContent(newContent);                     // İçeriği güncelle
            selected.setUpdatedDate(LocalDateTime.now());        // Tarihi güncelle
            noteListView.refresh();                              // Ekranı yenile
        });
    }

    // =============== 🔍 Not Filtreleme ===============
    @FXML
    private void filterNotes() {
        // Kullanıcının kriterlerini al
        String keyword = searchField.getText().toLowerCase().trim(); // Aranan kelime
        String selectedCategory = categoryFilter.getValue();         // Seçilen kategori
        boolean pinnedOnly = pinnedFilter.isSelected();              // Sadece sabitlenenler mi?

        // Listeyi filtrele
        List<NotebookDTO> filtered = notebookList.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(keyword)) // Başlığa göre filtrele
                .filter(note -> selectedCategory == null || selectedCategory.isEmpty() || note.getCategory().equals(selectedCategory)) // Kategoriye göre filtrele
                .filter(note -> !pinnedOnly || note.isPinned()) // Sadece sabitlenmişler isteniyorsa
                .collect(Collectors.toList());

        // Yeni filtrelenmiş listeyi göster
        noteListView.setItems(FXCollections.observableArrayList(filtered));
    }
}
