package org.example;
import java.util.*;

public class SharedMemory {
    private final Queue<Token> tokens;

    public SharedMemory(int n) {
        List<Token> allTokens = new ArrayList<>();
        for (int i = 0; i < n * n * n; i++) {
            allTokens.add(new Token(i));
        }
        Collections.shuffle(allTokens);
        tokens = new LinkedList<>(allTokens);
    }

    public synchronized List<Token> extractTokens(int nrTokens) {
        List<Token> extracted = new ArrayList<>();
        for (int i = 0; i < nrTokens && !tokens.isEmpty(); i++) {
            extracted.add(tokens.poll());
        }
        return extracted;
    }

    @Override
    public String toString() {
        return "SharedMemory{" +
                "tokens=" + tokens +
                '}';
    }
}

