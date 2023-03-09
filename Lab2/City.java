public class City extends Location{
    private int population;

    public City(String name, double x, double y) {this.name=name;this.X=x;this.Y=y;}

    public City() {System.out.println("City created");}

    public int getPopulation() {return population;}

    public void setPopulation(int population) {this.population = population;}

    @Override
    public String toString() {return "\n" + "City{" +  "population=" + population + ", name='" + name + '\'' + ", X=" + X + ", Y=" + Y + '}';}

    @Override
    public boolean equals(Object obj) {return super.equals(obj);}
}






