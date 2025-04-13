package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto;

import lombok.*;

import java.time.LocalDateTime;


// NotebookDTO, kullanıcıların oluşturduğu notların taşınması ve yönetilmesi için kullanılan veri transfer nesnesidir.
//
// Uygulama içinde her bir not şu bilgileri içerir:
// - id: Notun benzersiz kimliği
// - title: Notun başlığı
// - content: Notun içeriği
// - createdDate: Notun oluşturulma zamanı
// - updatedDate: Notun son güncellenme zamanı
// - category: Notun ait olduğu kategori (İş, Kişisel, Okul)
// - pinned: Notun sabitlenip sabitlenmediği (öncelikli gösterim için)
// - userDTO: Notu oluşturan kullanıcıya ait bilgiler
//
//Bu sınıf `Lombok` kütüphanesi ile sadeleştirilmiştir:
// - Getter/Setter/Builder/Constructor otomatik oluşturulur.

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotebookDTO {

    private Long id;                       // Her nota özel ID
    private String title;                  // Not başlığı
    private String content;                // Not içeriği
    private LocalDateTime createdDate;     // Oluşturulma tarihi
    private LocalDateTime updatedDate;     // Güncellenme tarihi
    private String category;               // Kategori (İş, Kişisel, Okul vb.)
    private boolean pinned;                // Sabitlenmiş mi?
    private UserDTO userDTO;               // Bu notu oluşturan kullanıcı bilgisi (composition)
}
