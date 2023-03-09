abstract class Location {
    protected String name;
    protected double X;
    protected double Y;
    Location(){System.out.println("Location created");}

    public Location(String name, double x, double y) {this.name = name;X = x;Y = y;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getX() {return X;}

    public void setX(double x) {X = x;}

    public double getY() {return Y;}

    public void setY(double y) {Y = y;}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
