package org.example.commands.voids.algorithms;

import org.example.model.Catalog;
import org.example.model.Document;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Statistics {
    private static int range = 1000;
    static Catalog catalog2;

    static public void testAlgorithms() {
        createBigCatalog();

        System.out.println("\n");
        Coloring col = new Coloring(catalog2);
        long startTime = System.nanoTime();
        col.execute();
        long totalTime = System.nanoTime() - startTime;
        System.out.println("DFS : " + totalTime / range + " sec");

        Graph4JColoring graph4J = new Graph4JColoring(catalog2);
        startTime = System.nanoTime();
        graph4J.execute();
        totalTime = System.nanoTime() - startTime;
        System.out.println("Graph4J : " + totalTime / range + " sec");

        JGraphTColoring jGraphT = new JGraphTColoring(catalog2);
        startTime = System.nanoTime();
        jGraphT.execute();
        totalTime = System.nanoTime() - startTime;
        System.out.println("JGraphT : " + totalTime / range + " sec");
        System.out.println("\n");
    }

    private static void createBigCatalog() {
        catalog2 = new Catalog();
        Random random = new Random();
        var docs = IntStream.rangeClosed(0, range - 1)
                .mapToObj(i -> new Document(null, "D" + i, "0" + i, random.nextInt(99 - 1 + 1) + 1, random.nextInt(99 - 1 + 1) + 1))
                .toArray(Document[]::new);

        catalog2.setDocs(List.of(docs));
    }
}

