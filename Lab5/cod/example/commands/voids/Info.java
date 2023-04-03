package org.example.commands.voids;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.*;

public class Info implements ListCommand {

    public Info() {
    }

    @Override
    public void execute() {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream(new File("C:\\Users\\Valea\\Desktop\\java\\_5\\raport.html"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ParseContext pcontext = new ParseContext();

        HtmlParser htmlparser = new HtmlParser();
        try {
            htmlparser.parse(inputstream, handler, metadata, pcontext);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();

        for (String name : metadataNames) {
            System.out.println(name + ":   " + metadata.get(name));
        }
    }
}

