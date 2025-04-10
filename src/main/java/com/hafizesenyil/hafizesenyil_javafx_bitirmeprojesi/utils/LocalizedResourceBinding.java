package com.hafizesenyil.hafizesenyil_javafx_bitirmeprojesi.utils;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ResourceBundle;

/**
 * LocalizedResourceBinding, ResourceBundle'ı tutan ve JavaFX'teki property binding işlemlerini yöneten bir sınıftır.
 * Bu sınıf, UI elemanları için dil kaynaklarını yönetmeye ve bu kaynaklarla dinamik bağlar (bindings) oluşturulmasına olanak tanır.
 */
public class LocalizedResourceBinding {

    // ResourceBundle'ı tutan ve izlenebilir hale getiren property
    // Bu property, dil kaynaklarını değiştirdiğimizde otomatik olarak UI'yi güncellemeyi sağlar.
    private final ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

    /**
     * ResourceBundle'ı döndürür.
     * @return kaynakları içeren ResourceBundle.
     */
    public ResourceBundle getResources() {
        return resources.get();
    }

    /**
     * ResourceBundle'ı günceller.
     * @param bundle Yeni ResourceBundle nesnesi.
     */
    public void setResources(ResourceBundle bundle) {
        resources.set(bundle);
    }

    /**
     * ResourceBundle property'sinin erişim fonksiyonu.
     * JavaFX'in binding işlemlerine olanak tanır.
     * @return ResourceBundle'ı tutan property.
     */
    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }

    /**
     * Belirli bir anahtar (key) için dil kaynaklarına bağlanan bir StringBinding oluşturur.
     * Bu bağlama ile, UI elemanları dinamik olarak güncellenebilir.
     * @param key Kaynağın anahtarı (key).
     * @return Bağlı olan StringBinding.
     */
    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                // resources property ile bağlanarak, değişimlerde UI'nın otomatik olarak güncellenmesini sağlar
                bind(resources);
            }

            /**
             * Kaynağın (ResourceBundle) değerini döndürür.
             * Eğer kaynak bulunamazsa, "???" ile anahtar döndürülür.
             * @return Kaynağın değeri ya da "???" ile belirtilmiş anahtar.
             */
            @Override
            protected String computeValue() {
                ResourceBundle bundle = getResources();
                if (bundle != null && bundle.containsKey(key)) {
                    return bundle.getString(key);
                } else {
                    return "???" + key + "???"; // Eğer kaynak bulunamazsa, eksik olan kaynak anahtarını gösterir.
                }
            }
        };
    }
}
