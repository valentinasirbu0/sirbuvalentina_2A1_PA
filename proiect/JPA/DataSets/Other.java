package org.example.JPA.DataSets;

public class Other {
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
