package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args){

        Main app = new Main();
        try {
            app.testCreateSave();
            app.testLoadView();
        } catch (IOException | InvalidCatalogException e) {
            throw new RuntimeException(e);
        }

    }

    private void testCreateSave() throws IOException {
        Catalog catalog = new Catalog("MyDocuments");
        var article = new Document("article1", "01", "Iasi");
        var book = new Document("book1", "02");
        catalog.add(book);
        catalog.add(article);
        CatalogUtil.save(catalog, "C:\\Users\\Valea\\Downloads\\catalog.json");
    }

    private void testLoadView() throws InvalidCatalogException, IOException {
        Catalog catalog;
        catalog = CatalogUtil.load("C:\\Users\\Valea\\Downloads\\catalog.json");
        CatalogUtil.view(catalog.findById("article1"));
    }
}

/*
extragem taguri cu tika
100 elemente in catalog

*/