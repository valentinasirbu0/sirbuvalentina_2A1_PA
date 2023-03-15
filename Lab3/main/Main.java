package main.java;

import main.java.algorithms.Network;
import main.java.algorithms.NetworkPrinter;
import main.java.model.*;

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.setBirthDate("10/12/2002");
        person.setName("person1");

        Person otherPerson = new Person();
        otherPerson.setBirthDate("03/05/2001");
        otherPerson.setName("person2");
        person.addRelationship(otherPerson, "best-friend");
        otherPerson.addRelationship(person, "best-friend");

        Programmer programmer1 = new Programmer("programmer1", "02/02/2002");
        programmer1.setProgrammingLanguage(ProgrammingLanguage.Java);
        programmer1.setProgrammingLanguage(ProgrammingLanguage.Javascript);
        programmer1.addRelationship(person, "relative");
        person.addRelationship(programmer1, "relative");

        Person designer1 = new Designer(DesignerType.UI, "designer1", "01/01/2001");
        designer1.addRelationship(programmer1, "brother");
        programmer1.addRelationship(designer1, "brother");
        designer1.addRelationship(person, "relative");
        person.addRelationship(designer1, "relative");

        Person designer2 = new Designer(DesignerType.Game, "designer2", "03/03/2003");
        designer2.addRelationship(designer1, "colleague");
        designer1.addRelationship(designer2, "colleague");

        Company company1 = new Company("company1");
        person.addRelationship(company1, "employee");
        company1.addRelationship(person, "employer");

        //block
        Person designer3 = new Designer(DesignerType.Graphic,"designer3","05/06/2005");
        Company company2 = new Company("company2");
        designer3.addRelationship(company2,"employee");
        company2.addRelationship(designer3,"employer");

        //block
        Programmer programmer2 = new Programmer("programmer2", "02/02/2002");
        Company company3 = new Company("company3");
        programmer2.addRelationship(company3,"employee");
        company3.addRelationship(programmer2,"employer");


        Network network = new Network();
        network.addNode(person);
        network.addNode(otherPerson);
        network.addNode(programmer1);
        network.addNode(designer1);
        network.addNode(designer2);
        network.addNode(company1);
        network.addNode(designer3);
        network.addNode(company2);
        network.addNode(programmer2);
        network.addNode(company3);


        NetworkPrinter.printNetwork(network);
        NetworkPrinter.printArticulationPoints(network.findArticulationPoints());
        NetworkPrinter.printBlocks(network.findBlocks());

    }
}