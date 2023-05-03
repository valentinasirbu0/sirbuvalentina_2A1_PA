package org.example.dataSets;

import org.example.DAO.AlbumDAO;
import org.example.DAO.ArtistDAO;
import org.example.DAO.GenreDAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVData {
    public CSVData() {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\Valea\\Desktop\\java\\_8\\albumlist.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String line;
        var artists = new ArtistDAO();
        var genres = new GenreDAO();
        var albums = new AlbumDAO();

        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] fields = line.split(",");
            artists.create(fields[3]);
            genres.create(fields[4]);
            albums.create(java.lang.Integer.parseInt(fields[1]), removeSpecialChars(fields[2]), fields[3], fields[4]);
        }
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String removeSpecialChars(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } else if (i > 0 && Character.isLetterOrDigit(input.charAt(i-1))) {
                output.append("_");
            }
        }
        return output.toString();
    }
}
