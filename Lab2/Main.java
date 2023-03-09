public class Main {
    public static void main(String[] args) {
        Location[] lc = new Location[5];
        Road[] rd = new Road[5];

        Problem pb = new Problem();
        pb.setLocations(lc);
        pb.setRoads(rd);

        lc[0] = new City();
        lc[0].setName("Iasi");
        lc[0].setX(10.0);
        lc[0].setY(20.0);
        lc[1] = new City("Vaslui", 10.0, 20.0);
        lc[2] = new Airport("Airport1", 25, 30, 10);
        lc[3] = new GasStation("Gas1", 5, 7);
        lc[4] = new Airport("Airport2",15,16);

        rd[0] = new Road("D24", RoadType.EXPRESS, 30, 50, lc[0], lc[1]);
        rd[1] = new Road("A12", RoadType.HIGHWAY, 70, 90, lc[2], lc[3]);
        rd[2] = new Road("A1",RoadType.COUNTRY,100,50,lc[1],lc[4]);
        rd[3] = new Road("SS",RoadType.HIGHWAY,70,45,lc[0],lc[2]);
        rd[4] = new Road("SS",RoadType.HIGHWAY,20,45,lc[0],lc[3]);
        System.out.println(pb);
        System.out.println(pb.isProblemValid(pb));


        Algorithm alg = new DijkstraAlgorithm(pb);
        Solution sol = alg.solve();
        System.out.println(sol);

    }


}













