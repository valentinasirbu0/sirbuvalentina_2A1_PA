package org.example.commands.voids;

import org.example.exceptions.DocumentViewException;
import org.example.model.Document;

import java.awt.*;
import java.io.IOException;

public class ViewDocument implements ListCommand {
    private Document doc;

    public ViewDocument(Document doc) {
        this.doc = doc;
    }

    @Override
    public void execute() {
        if (Desktop.isDesktopSupported() && doc != null) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(doc.getFile());
            } catch (IOException e) {
                throw new DocumentViewException("Failed to view document: " , e);
            }
        }
    }
}
