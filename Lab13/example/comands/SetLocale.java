package com.example.comands;

import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    public static void execute(String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        Locale.setDefault(locale);
        ResourceBundle messages = ResourceBundle.getBundle("Messages", Locale.getDefault());
        System.out.println(messages.getString("locale.set") + " " + locale.getDisplayName());
    }
}
