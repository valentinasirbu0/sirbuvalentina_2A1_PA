package main.java.model;

public abstract class AbstractNode implements Node, Comparable<Node> {
    protected boolean isVisited;
    protected int dfsNumber;
    protected int lowValue;
    protected boolean isArticulationPoint;

    protected AbstractNode() {
        this.isVisited = false;
        this.dfsNumber = -1;
        this.lowValue = -1;
        this.isArticulationPoint = false;
    }

    public int getDegree() {
        return getRelationships().size();
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getDfsNumber() {
        return dfsNumber;
    }

    public void setDfsNumber(int dfsNumber) {
        this.dfsNumber = dfsNumber;
    }

    public int getLowValue() {
        return lowValue;
    }

    public void setLowValue(int lowValue) {
        this.lowValue = lowValue;
    }

    public boolean isArticulationPoint() {
        return isArticulationPoint;
    }

    public void setArticulationPoint(boolean articulationPoint) {
        isArticulationPoint = articulationPoint;
    }

    @Override
    public int compareTo(Node other) {
        return this.getDegree() - other.getDegree();
    }
}
