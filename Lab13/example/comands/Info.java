package com.example.comands;

import java.util.Locale;
import java.util.ResourceBundle;

public class Info {
    public static void execute(String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", Locale.getDefault());
        ResourceBundle localeData = ResourceBundle.getBundle("Messages", locale);

        System.out.println(messages.getString("info") + " " + locale.getDisplayName() + ":");
        System.out.println("Country: " + localeData.getString("country"));
        System.out.println("Language: " + localeData.getString("language"));
        System.out.println("Currency: " + localeData.getString("currency"));
        System.out.println("Week Days: " + localeData.getString("weekdays"));
        System.out.println("Months: " + localeData.getString("months"));
        System.out.println("Today: " + localeData.getString("today"));
    }
}
