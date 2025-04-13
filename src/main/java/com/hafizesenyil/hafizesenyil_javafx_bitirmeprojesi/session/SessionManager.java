package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.session;

import com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.dto.UserDTO;

/**
 * SessionManager.java
 * Bu sınıf, oturum yönetimi sağlar. Giriş yapan kullanıcı bilgisi burada tutulur.
 * Tüm uygulama boyunca kullanıcıya ait bilgilere erişmek için kullanılır.
 */
public class SessionManager {

    // Şu an giriş yapmış olan kullanıcı
    private static UserDTO currentUser;

    /**
     * Giriş yapan kullanıcıyı belleğe alır.
     * @param user Giriş yapmış kullanıcı nesnesi
     */
    public static void setCurrentUser(UserDTO user) {
        currentUser = user;
    }

    /**
     * O an oturumda olan kullanıcıyı getirir.
     * @return UserDTO nesnesi
     */
    public static UserDTO getCurrentUser() {
        return currentUser;
    }

    /**
     * Oturumu sıfırlar (örneğin çıkış yapıldığında).
     */
    public static void clearSession() {
        currentUser = null;
    }
}

