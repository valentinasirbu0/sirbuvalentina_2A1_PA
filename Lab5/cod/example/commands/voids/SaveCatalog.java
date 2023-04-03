package org.example.commands.voids;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exceptions.SaveCatalogException;
import org.example.model.Catalog;

import java.io.File;
import java.io.IOException;

public class SaveCatalog implements ListCommand {
    private Catalog catalog;
    private String path = "C:\\Users\\Valea\\Desktop\\java\\_5\\catalog.json";

    public SaveCatalog(Catalog catalog) {
        this.catalog = catalog;
        this.path = path;
    }

    @Override
    public void execute() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            objectMapper.writeValue(new File(path), catalog);
        } catch (IOException e) {
            throw new SaveCatalogException("Failed to view document: " , e);
        }
    }
}
