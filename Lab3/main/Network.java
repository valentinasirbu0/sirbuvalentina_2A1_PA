package main.java.algorithms;

import main.java.model.AbstractNode;
import main.java.model.Node;

import java.util.*;

public class Network {
    private List<AbstractNode> nodeMap = new ArrayList<>();

    private static int time;

    public Network() {

        System.out.println("Network created");
    }

    public void addNode(AbstractNode node) {
        nodeMap.add(node);
    }

    private void dfs(Node entry, Node parent) {
        int nrOfChildren = 0;
        entry.setVisited(true);
        int t = ++time;
        entry.setDfsNumber(t);
        entry.setLowValue(t);

        for (Map.Entry<Node, String> neighbour : entry.getRelationships().entrySet()) {
            for (Node node : nodeMap) {
                if (neighbour.getKey().equals(node)) {
                    if (!node.isVisited()) {
                        nrOfChildren++;
                        dfs(node, entry);
                        entry.setLowValue(Math.min(entry.getLowValue(), node.getLowValue()));
                        if (parent != null && node.getLowValue() >= entry.getDfsNumber()) {
                            entry.setArticulationPoint(true);
                        }
                    } else if (!node.equals(parent)) {
                        entry.setLowValue(Math.min(entry.getLowValue(), node.getDfsNumber()));
                    }
                }
            }
        }

        if (parent == null && nrOfChildren > 1) {
            entry.setArticulationPoint(true);
        }

    }


    public Set<Node> findArticulationPoints() {
        Set<Node> articulationPoints = new HashSet<>();
        for (Node entry : nodeMap) {
            if (!entry.isVisited()) {
                dfs(entry, null);  //if it is not visited,we visit it :)
            }
        }
        for (Node entry : nodeMap) {
            if (entry.isArticulationPoint()) {
                articulationPoints.add(entry);
            }
        }
        return articulationPoints;
    }

    public Map<Node, Node> findBlocks() {
        Map<Node, Node> blocks = new HashMap<>();
        for (Node node1 : nodeMap) {
            for (Node node2 : nodeMap) {
                if (node1 != null && node2 != null && !node1.equals(node2) && !blocks.containsKey(node1) && !blocks.containsKey(node2)) {
                    if (node1.getDegree() == 1 && node2.getDegree() == 1) {
                        if (node1.getRelationships().containsKey(node2) && node2.getRelationships().containsKey(node1)) {
                            //System.out.println(node1.getKey().getName() + "---" + node2.getKey().getName());
                            blocks.put(node1, node2);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    public List<AbstractNode> getNodeMap() {

        return nodeMap;
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodeMap=" + nodeMap +
                '}';
    }

}
