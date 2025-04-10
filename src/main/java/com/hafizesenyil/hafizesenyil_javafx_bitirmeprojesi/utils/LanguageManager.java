package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils;

/*
******* 📌 Kullanım Senaryosu *********
1. Uygulama başladığında, varsayılan olarak Türkçe dilindeki metinler ResourceBundle ile yüklenir.
2. Eğer kullanıcı dil değiştirmek isterse, LanguageManager.changeLanguage() metodunu
   kullanarak uygulamanın dilini değiştirebilir (örneğin, İngilizce'ye geçmek için).
3. JavaFX UI bileşenleri (label, button vb.) LocalizedResourceBinding.getStringBinding()
   ile bağlanarak, dil değiştirildiğinde metinler otomatik olarak güncellenir.
 */

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * LanguageManager, uygulamanın dilini yönetir ve dil değişikliği yapıldığında
 * dil kaynaklarını (ResourceBundle) günceller.
 * Uygulama diline göre UI bileşenlerinin otomatik olarak güncellenmesini sağlar.
 */
public class LanguageManager {

    // LocalizedResourceBinding sınıfını kullanarak dil kaynaklarını tutan binding nesnesi.
    private static final LocalizedResourceBinding binding = new LocalizedResourceBinding();

    // Statik blok: Uygulama ilk başlatıldığında varsayılan dil Türkçe olarak ayarlanır.
    static {
        // Varsayılan olarak Türkçe dilinde ResourceBundle'ı yükler
        Locale defaultLocale = new Locale("tr");  // "tr" Türkçe dilini temsil eder
        ResourceBundle bundle = ResourceBundle
                .getBundle("com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.messages", defaultLocale);

        // Yüklenen kaynakları binding'e set eder
        binding.setResources(bundle);
    }

    /**
     * @return LanguageManager tarafından yönetilen LocalizedResourceBinding nesnesini döndürür.
     * Bu binding, dil kaynaklarıyla ilgili işlemleri sağlar.
     */
    public static LocalizedResourceBinding getBinding() {
        return binding;
    }

    /**
     * Dil değişimi yaparak UI bileşenlerinin metinlerini günceller.
     * @param locale Yeni dil (örneğin, Locale.ENGLISH için İngilizce).
     */
    public static void changeLanguage(Locale locale) {
        // Verilen locale için uygun ResourceBundle'ı yükler
        ResourceBundle bundle = ResourceBundle.getBundle("com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.messages", locale);

        // Yeni dil kaynaklarını binding'e set eder
        binding.setResources(bundle);
    }
}
