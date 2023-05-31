package com.example.app;

import com.example.comands.DisplayLocales;
import com.example.comands.Info;
import com.example.comands.SetLocale;

import java.util.Locale;
import java.util.Scanner;

public class LocaleExplore {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("Input command: ");
            String command = scanner.nextLine();

            String[] commandParts = command.split(" ");
            String commandName = commandParts[0].toLowerCase();

            switch (commandName) {
                case "displaylocales" -> DisplayLocales.execute();
                case "setlocale" -> {
                    if (commandParts.length > 1) {
                        String languageTag = commandParts[1];
                        SetLocale.execute(languageTag);
                    } else {
                        System.out.println("Invalid command");
                    }
                }
                case "info" -> {
                    if (commandParts.length > 1) {
                        String languageTag = commandParts[1];
                        Info.execute(languageTag);
                    } else {
                        Info.execute(Locale.getDefault().toLanguageTag());
                    }
                }
                case "exit" -> running = false;
                default -> System.out.println("Invalid command");
            }
        }

        scanner.close();
    }
}
