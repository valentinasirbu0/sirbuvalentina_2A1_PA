package org.example.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public class LyricsExtractor {
    public static String lyrics;

    public static void extractLinesFromURL(JSONObject jsonObject) {
        JSONArray textArray = jsonObject.getJSONObject("result").getJSONObject("track").getJSONArray("sections").getJSONObject(1).getJSONArray("text");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        Object[] jsonArrayObject = new Object[textArray.length()];
        for (int i = 0; i < textArray.length(); i++) {
            jsonArrayObject[i] = textArray.getString(i);
        }

        String prettyJson;
        try {
            prettyJson = objectWriter.writeValueAsString(jsonArrayObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        lyrics = formatLyrics(prettyJson);
    }

    public static String formatLyrics(String lyrics) {
        String cleanedLyrics = lyrics.replaceAll("\"|\\[|\\]", "").trim();
        String[] sections = cleanedLyrics.split(", ");
        StringBuilder formattedLyrics = new StringBuilder();

        for (int i = 0; i < sections.length; i++) {
            formattedLyrics.append(sections[i]);
            if (i < sections.length - 1) {
                formattedLyrics.append("\n");
            }
        }

        //System.out.println(formattedLyrics);
        return formattedLyrics.toString();
    }
}
