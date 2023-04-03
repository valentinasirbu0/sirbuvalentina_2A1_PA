package org.example.commands;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exceptions.CatalogLoadException;
import org.example.model.Catalog;

import java.io.File;
import java.io.IOException;

public class LoadCatalog {
    private static String path = "C:\\Users\\Valea\\Desktop\\java\\_5\\catalog.json";

    public static Catalog loadCatalog() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Catalog catalog = null;
        try {
            catalog = objectMapper.readValue(new File(path), Catalog.class);
        } catch (IOException e) {
            throw new CatalogLoadException("Failed to load catalog from file: " + path, e);
        }
        return catalog;
    }
}
