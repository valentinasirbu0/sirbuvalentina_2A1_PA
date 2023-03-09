import java.util.Arrays;

public class Problem {
    private Location[] locations;
    private Road[] roads;
    Problem(){System.out.println("Problem created");}

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }

    public Road[] getRoads() {
        return roads;
    }

    public void setRoads(Road[] roads) {
        this.roads = roads;
    }

    public Location[] getLocations() {return locations;}

    @Override
    public String toString() {return "Problem{" + "\n" + "locations=" + Arrays.toString(locations) + "\n" + "roads=" + Arrays.toString(roads) + '}';}

    public Road getRoads(int i) {return roads[i];}

    public Location getLocation(int i){return locations[i];}

    public boolean isProblemValid(Problem problem) {
        if (problem == null) { return false;}
        if (problem.getLocations() == null || problem.getRoads() == null) {return false;}
        if (problem.getLocations().length == 0 || problem.getRoads().length == 0) {return false;}
        return true;
    }
}
