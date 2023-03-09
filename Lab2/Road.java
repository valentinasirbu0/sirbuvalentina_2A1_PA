import java.lang.Math;
public class Road {
    private String name;
    private RoadType type;
    private double length;
    private float speed;
    private Location a;
    private Location b;

    public Location getA() {
        return a;
    }

    public void setA(Location a) {
        this.a = a;
    }

    public Location getB() {
        return b;
    }

    public void setB(Location b) {
        this.b = b;
    }

    public RoadType getType() {
        return type;
    }

    public void setType(RoadType type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Road(String name, RoadType type,double length, float speed, Location a, Location b) {
        double euclidDistance = Math.sqrt((Math.pow(a.getX()-b.getX(), 2))+(Math.pow(a.getY()-b.getY(),2)));
        if(euclidDistance <= length){
            this.name = name;
            this.type = type;
            this.length = length;
            this.speed = speed;
            this.a=a;
            this.b=b;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\n" + "Road{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", length=" + length +
                ", speed=" + speed +
                ", a=" + a.name +
                ", b=" + b.name +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
