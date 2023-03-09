public class Airport extends Location{
    private int NbOfTerminals;

    public Airport(String name, double x, double y) {this.name=name;this.X=x;this.Y=y;}

    public Airport(int nbOfTerminals) {NbOfTerminals = nbOfTerminals;}

    public Airport(String name, double x, double y, int nbOfTerminals) {super(name, x, y);NbOfTerminals = nbOfTerminals;}

    public Airport() {System.out.println("City created");}

    public int getNbOfTerminals() {return NbOfTerminals;}

    public void setNbOfTerminals(int nbOfTerminals) {this.NbOfTerminals = nbOfTerminals;}

    @Override
    public String toString() {return "\n" + "City{" + "population=" + NbOfTerminals + ", name='" + name + '\'' + ", X=" + X + ", Y=" + Y + '}';}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
