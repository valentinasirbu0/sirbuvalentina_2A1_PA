package org.example.commands.voids.algorithms;

import org.example.commands.voids.ListCommand;
import org.example.model.Catalog;
import org.example.model.Document;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Coloring implements ListCommand {
    private Set<Integer> colors = new HashSet<>();
    private Catalog catalog;

    public Coloring(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() {
        if (catalog.getDocs().size() == 1) {
            catalog.getDocs().get(0).setColour(0);
        } else {
            catalog.getDocs().get(0).setColour(0);
            colors.add(0);
            color(catalog.getDocs().get(1));
        }
        printSolution();
    }

    private void color(Document document) {
        if (document.getColour() == -1) {
            Set<Integer> neighbourColors = findNeighbourColors(document);
            Set<Integer> difference = new HashSet<>(colors);
            difference.removeAll(neighbourColors);
            if (difference.isEmpty()) {
                document.setColour(colors.size());
                colors.add(colors.size());
            } else {

                Random rand = new Random();
                int index = rand.nextInt(difference.size());
                document.setColour((Integer) difference.toArray()[index]);
            }
        }
        if (hasConnections(document)) {
            for (Document d : catalog.getDocs()) {
                if (existConnection(d, document) && d.getColour() == -1) {
                    color(d);
                }
            }
        } else {
            boolean found = false;
            while (!found && !allColored()) {
                for (Document d : catalog.getDocs()) {
                    if (d.getColour() == -1) {
                        found = true;
                        color(d);
                    }
                }
            }
        }
    }

    private Set findNeighbourColors(Document document) {
        Set<Integer> neighbourColors = new HashSet<>();
        for (Document d : catalog.getDocs()) {
            if (existConnection(document, d) && d.getColour() != -1) {
                neighbourColors.add(d.getColour());
            }
        }
        return neighbourColors;
    }

    private boolean allColored() {
        for (Document d : catalog.getDocs()) {
            if (d.getColour() == -1) {
                return false;
            }
        }
        return true;
    }

    private boolean hasConnections(Document d) {
        for (String tag : d.getTags()) {
            for (Document d2 : catalog.getDocs()) {
                if (!d2.equals(d) && d2.getTags().contains(tag)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean existConnection(Document d1, Document d2) {
        for (String tag1 : d1.getTags()) {
            for (String tag2 : d2.getTags()) {
                if (tag1.equals(tag2) && tag1 != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printSolution() {
        for (int i = 0; i < catalog.getDocs().size(); i++) {
            System.out.print(catalog.getDocs().get(i).getTitle() + " is colored " + catalog.getDocs().get(i).getColour() + " ; ");
        }
        System.out.println();
    }

}

