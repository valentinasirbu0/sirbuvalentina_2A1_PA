package org.example.commands;

import org.example.model.Catalog;
import org.example.model.Document;

import java.io.File;

public class CreateCatalog {

    public static Catalog createCatalog(Catalog catalog) {
        catalog.setName("MyDocuments");
        var article = new Document(new File("C:\\Users\\Valea\\Desktop\\java\\_5\\article1.txt"), "article1", "01", "Iasi");
        article.setTags("cosmos", "school", "flowers");
        catalog.add(article);
        var book1 = new Document(new File("C:\\Users\\Valea\\Desktop\\java\\_5\\book1.txt"), "book1", "02","Vaslui");
        book1.setTags("pretty", "flowers", "stars");
        catalog.add(book1);
        var book2 = new Document(new File("C:\\Users\\Valea\\Desktop\\java\\_5\\book1.txt"), "book2", "03","Chisinau");
        book2.setTags("pretty", "school");
        catalog.add(book2);
        var book3 = new Document(new File("C:\\Users\\Valea\\Desktop\\java\\_5\\book1.txt"), "book3", "04","Straseni");
        book3.setTags("stars", "cosmos");
        catalog.add(book3);
        return catalog;
    }
}
