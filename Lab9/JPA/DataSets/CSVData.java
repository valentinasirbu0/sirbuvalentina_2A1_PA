package org.example.JPA.DataSets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.JPA.DAOTests.AlbumDAOTest;
import org.example.JPA.DAOTests.AlbumGenreTest;
import org.example.JPA.DAOTests.ArtistsDAOTest;
import org.example.JPA.DAOTests.GenreDAOTest;
import org.example.JPA.model.Genre;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVData {
    public CSVData() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Valea\\Desktop\\java\\_8\\albumlist.csv"));
             CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                var artist = ArtistsDAOTest.ArtistTest(csvRecord.get("Artist"));
                var genre = GenreDAOTest.GenreTest(csvRecord.get("Genre"));
                var album = AlbumDAOTest.AlbumTest(Integer.valueOf(csvRecord.get("Year")), removeSpecialChars(csvRecord.get("Album")), artist);
                for (Genre g : genre) {
                    AlbumGenreTest.AlbumGenreTest(album, g);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String removeSpecialChars(String input) {
        input = input.trim().replaceAll("\\s+", " ");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } else if (i > 0 && Character.isLetterOrDigit(input.charAt(i - 1))) {
                output.append("_");
            }
        }
        return output.toString();
    }
}
