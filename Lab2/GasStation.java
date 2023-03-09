public class GasStation extends Location{
    private int GasPrice;

    public GasStation(String name, double x, double y) {this.name=name;this.X=x;this.Y=y;}

    public GasStation() {System.out.println("City created");}

    public int getGasPrice() {return GasPrice;}

    public void setGasPrice(int gasPrice) {this.GasPrice = gasPrice;}

    @Override
    public String toString() {return "\n" + "City{" + "population=" + GasPrice + ", name='" + name + '\'' + ", X=" + X + ", Y=" + Y + '}';}

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
