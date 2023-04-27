package org.example;

import java.util.*;

public class SharedMemory {
    private final List<Token> listOfTokens = new LinkedList<>();

    public SharedMemory(int n) {
        for (int i = 0; i < n; i++) {
            listOfTokens.add(new Token(i));
        }
        Collections.shuffle((List<?>) listOfTokens);
    }

    public synchronized List<Token> extractTokens(int howMany) {
        List<Token> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (listOfTokens.isEmpty()) {
                break;
            }
            Random rand = new Random();
            extracted.add(listOfTokens.get(rand.nextInt(listOfTokens.size())));
        }
        return extracted;
    }
}