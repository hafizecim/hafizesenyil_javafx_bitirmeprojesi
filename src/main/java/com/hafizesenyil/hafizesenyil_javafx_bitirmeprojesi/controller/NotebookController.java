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
import javafx.scene.layout.Region;


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
        // ✅ Kategori seçimi için null + kategori seçenekleri
        categoryFilter.getItems().add(null); // Bu, promptText'i aktif eder
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
        // 5️⃣ Çift tıklama ile detay göster
        noteListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // çift tıklama
                NotebookDTO selectedNote = noteListView.getSelectionModel().getSelectedItem();
                if (selectedNote != null) {
                    showNoteDetails(selectedNote); // detay gösterme metodunu çağır
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
                    filterNotes(); // ✅ Silindikten sonra filtrelemeyi yeniden uygula
                }
            });
        }
    }


    // =============== 🔁 Tüm filtreleri sıfırla ve listeyi yeniden yükle ===============//
    @FXML
    private void resetFilters() {
        searchField.clear();        // Arama kutusunu temizle
        categoryFilter.getSelectionModel().clearSelection(); // Kategori seçimini temizle

        categoryFilter.setValue(null); // 🔑 Seçili öğeyi null yap (boş seçimi göster)
        categoryFilter.setPromptText("Kategori Seç"); // ✅ PromptText yeniden set ediliyor

        pinnedFilter.setSelected(false); // Sabit filtreyi kaldır
        noteListView.setItems(notebookList); // Tüm notları yeniden yükle
        noteListView.refresh(); // Yenile

    }

    // =============== 📝 Not Düzenleme (Seçili) ===============//
    @FXML
    private void editNote() { // 📝 Seçili notu detaylı olarak düzenler
        NotebookDTO selected = noteListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            // 1️⃣ Not düzenleme formunu aç
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hafizesenyil/hafizesenyil_javafx_bitirmeprojesi/view/note-ekle-form.fxml"));
            DialogPane pane = loader.load();

            // 2️⃣ Form controller'ına mevcut notu gönder (düzenleme modu)
            NoteEkleFormController formController = loader.getController();
            formController.setNote(selected); // not = dolu DTO (düzenleme)

            // 3️⃣ Dialog pencereyi oluştur
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Notu Düzenle");
            dialog.showAndWait();

            // 4️⃣ Kaydedildiyse verileri güncelle
            if (formController.isSaved()) {
                NotebookDTO updatedNote = formController.getNote();

                // Aynı nesne üzerinden güncelleme (ObservableList zaten bu nesneyi tutuyor)
                selected.setTitle(updatedNote.getTitle());
                selected.setContent(updatedNote.getContent());
                selected.setCategory(updatedNote.getCategory());
                selected.setPinned(updatedNote.isPinned());
                selected.setUpdatedDate(LocalDateTime.now());

                noteListView.refresh(); // Görünümü yenile
            }

        } catch (Exception e) {
            e.printStackTrace(); // Hata logu
        }
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


    }// ✅ bu metot burada bitiyor

    // 📋 Seçilen notun detaylarını gösterir
    private void showNoteDetails(NotebookDTO note) {
        StringBuilder content = new StringBuilder();
        content.append("📌 Başlık: ").append(note.getTitle()).append("\n\n");
        content.append("📋 Açıklama:\n").append(note.getContent()).append("\n\n");
        content.append("🗂️ Kategori: ").append(note.getCategory()).append("\n");
        content.append("📎 Sabitlenmiş mi: ").append(note.isPinned() ? "Evet" : "Hayır").append("\n");
        content.append("🕒 Oluşturulma: ").append(note.getCreatedDate()).append("\n");
        if (note.getUpdatedDate() != null) {
            content.append("📝 Güncellenme: ").append(note.getUpdatedDate()).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not Detayları");
        alert.setHeaderText("Seçilen Not");
        alert.setContentText(content.toString());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // Çok satırlı metin için

        alert.showAndWait();
    }
}
