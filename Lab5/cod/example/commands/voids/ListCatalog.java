package org.example.commands.voids;

import freemarker.cache.ClassTemplateLoader;
import org.example.exceptions.*;
import org.example.model.Catalog;
import org.example.Main;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.*;

public class ListCatalog implements ListCommand {
    private Catalog catalog;

    public ListCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() {
        Configuration configuration = new Configuration();
        try {
            configuration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/templates"));
            Template template = null;
            try {
                template = configuration.getTemplate("catalog.ftl");
            } catch (IOException e) {
                throw new LoadTemplateException("Failed to load template exception", e);
            }
            Writer writer = new OutputStreamWriter(System.out);
            try {
                template.process(catalog, writer);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new ReadTemplateException("Failed to read exception", e);
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new ListCatalogException("Failed to list catalog ", e);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}