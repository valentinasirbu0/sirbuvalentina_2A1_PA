package main.java.model;

import java.util.Map;

public interface Node {
    String getName();

    Map<Node, String> getRelationships();

    int getDegree();

    int getDfsNumber();

    int getLowValue();

    boolean isArticulationPoint();

    boolean isVisited();

    void setDfsNumber(int dfsNumber);

    void setLowValue(int lowValue);

    void setArticulationPoint(boolean isArticulationPoint);

    void setVisited(boolean visited);

}
