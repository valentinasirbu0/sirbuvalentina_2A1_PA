package org.example;

import org.example.commands.*;
import org.example.commands.voids.*;
import org.example.commands.voids.algorithms.Coloring;
import org.example.commands.voids.algorithms.Statistics;
import org.example.model.Catalog;

public class Main {
    public static void main(String[] args) {

        Catalog catalog = new Catalog();
        catalog = CreateCatalog.createCatalog(catalog);
        ListCommand save = new SaveCatalog(catalog);
        ListCommand list = new ListCatalog(catalog);
        ListCommand raport = new Raport(catalog);
        ListCommand view = new ViewDocument(catalog.findById("01"));
        ListCommand color = new Coloring(catalog);
        ListCommand info = new Info();


        AddCommand menu = new AddCommand(save, list, view, raport, color, info);
        menu.save();

        catalog = LoadCatalog.loadCatalog();
        //menu.view();
        //menu.list();

        menu.raport();
        menu.info();

        menu.color();
        Statistics.testAlgorithms();
    }

}
