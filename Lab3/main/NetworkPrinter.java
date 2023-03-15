package main.java.algorithms;

import main.java.model.Node;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class NetworkPrinter {
    static public void printBlocks(Map<Node, Node> blocks) {
        System.out.println("\nBlocks: ");
        for (Map.Entry<Node, Node> data : blocks.entrySet()) {
            System.out.println(data.getKey() + "---" + data.getValue());
        }
    }

    static public void printArticulationPoints(Set<Node> articulationPoints) {
        System.out.println("\nArticulation points: ");
        for (Node node : articulationPoints) {
            System.out.println(node);
        }
    }

    static public void printNetwork(Network network) {
        System.out.println("\n");
        Collections.sort(network.getNodeMap());
        for (Node node : network.getNodeMap()) {
            System.out.println(node);
        }
    }

}
