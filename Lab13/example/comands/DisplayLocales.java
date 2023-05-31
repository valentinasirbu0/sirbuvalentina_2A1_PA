package com.example.comands;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales {
    public static void execute() {
        ResourceBundle messages = ResourceBundle.getBundle("Messages", Locale.getDefault());
        System.out.println(messages.getString("locales"));
        Locale[] availableLocales = Locale.getAvailableLocales();
        for (Locale locale : availableLocales) {
            System.out.println(locale.getDisplayName());
        }
    }
}
