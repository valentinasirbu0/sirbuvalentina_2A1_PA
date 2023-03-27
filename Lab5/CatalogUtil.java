package org.example;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class CatalogUtil {

    public static void save(Catalog catalog, String path){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            objectMapper.writeValue(new File(path), catalog);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void view(Document doc) {
        if (Desktop.isDesktopSupported() && doc != null) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(doc.getFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void list(Catalog catalog) {
        System.out.println(catalog);
    }

    public static Catalog load(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Catalog catalog = null;
        try {
            catalog = objectMapper.readValue(new File(path), Catalog.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return catalog;
    }

    @Override
    public String toString() {
        return "CatalogUtil{}";
    }
}