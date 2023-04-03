package org.example.commands.voids;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.exceptions.LoadTemplateException;
import org.example.exceptions.RaportException;
import org.example.exceptions.ReadTemplateException;
import org.example.model.Catalog;
import org.example.model.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Raport implements ListCommand {
    private Catalog catalog;
    private List<Map<String, Object>> dataList;
    Map<String, Object> map;

    public Raport(Catalog catalog) {
        this.catalog = catalog;
        arrangeDataIntoMap();
    }

    private void arrangeDataIntoMap() {
        map = new HashMap<>();
        map.put("title", catalog.getName());
        dataList = new ArrayList<>();
        Map<String, Object> dataMap;
        for (Document doc : catalog.getDocs()) {
            dataMap = new HashMap<>();
            dataMap.put("id", doc.getId());
            dataMap.put("title", doc.getTitle());
            dataMap.put("location",doc.getLocation());
            dataMap.put("tags", doc.getTags().toString());
            dataList.add(dataMap);
        }
        map.put("docs", dataList);
    }

    @Override
    public void execute() {
        Configuration config = new Configuration();
        FileReader reader = null;
        try {
            reader = new FileReader(new File("C:\\Users\\Valea\\Desktop\\java\\_5\\_5\\src\\main\\resources\\templates\\raport.ftl"));
        } catch (FileNotFoundException e) {
            throw new ReadTemplateException("Failed to read exception", e);
        }
        Template template = null;
        try {
            template = new Template("Raport", reader, config);
        } catch (IOException e) {
            throw new LoadTemplateException("Failed to load template", e);
        }
        try {
            try {
                template.process(map, new FileWriter(new File("C:\\Users\\Valea\\Desktop\\java\\_5\\raport.html")));
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RaportException("Failed to report", e);
        }
    }
}
