package test.java;

import main.java.algorithms.Network;
import main.java.model.Node;
import main.java.model.Programmer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class NetworkTest {

    Network network;
    Programmer programmer2;
    Programmer programmer1;

    @BeforeEach
    void setUp() {
        network = new Network();
        programmer1 = new Programmer("programmer1", "02/02/2002");
        programmer2 = new Programmer("programmer2", "02/02/2002");
        network.addNode(programmer1);
        network.addNode(programmer2);
        programmer1.addRelationship(programmer2, "colleague");
        programmer2.addRelationship(programmer1, "colleague");
    }

    @Test
    void getNodeMap_CorrectData_Success() {
        Network expectedResult = new Network();
        expectedResult.addNode(programmer1);
        expectedResult.addNode(programmer2);
        Assertions.assertEquals(expectedResult.getNodeMap(), network.getNodeMap(), "Node map works fine");
        Assertions.assertEquals(2, network.getNodeMap().size(), "Node map works fine");
    }

    @Test
    void findBlocks_CorrectData_Success() {
        Map<Node, Node> expectedResult = new HashMap<>();
        expectedResult.put(programmer1, programmer2);
        Assertions.assertEquals(expectedResult, network.findBlocks(), "FindBlock works fine");
        Assertions.assertEquals(1, network.findBlocks().size(), "FindBlock works fine");
    }

    @Test
    void findArticulationPoints_CorrectData_Success() {
        Programmer programmer3 = new Programmer("programmer3", "02/02/2002");
        programmer3.addRelationship(programmer2, "colleague");
        programmer2.addRelationship(programmer3, "colleague");
        network.addNode(programmer3);
        Set<Node> expectedResult = new HashSet<>();
        expectedResult.add(programmer2);
        Assertions.assertEquals(expectedResult, network.findArticulationPoints(), "Articulation Points works fine");
        Assertions.assertEquals(1, network.findArticulationPoints().size(), "Articulation Points works fine");
    }

    @Test
    void getNodeMap_WrongData_Failure() {
        Network expectedResult = new Network();
        expectedResult.addNode(programmer1);
        Assertions.assertNotEquals(expectedResult.getNodeMap(), network.getNodeMap(), "Node map works fine");
    }

    @Test
    void findBlocks_WrongData_Failure() {
        Map<Node, Node> expectedResult = new HashMap<>();
        expectedResult.put(programmer2, programmer1);
        Assertions.assertNotEquals(expectedResult, network.findBlocks(), "FindBlock works fine");
        Assertions.assertNotEquals(null, network.findBlocks(), "FindBlock works fine");
    }

    @Test
    void findArticulationPoints_WrongData_Failure() {
        Programmer programmer3 = new Programmer("programmer3", "02/02/2002");
        programmer3.addRelationship(programmer2, "colleague");
        programmer2.addRelationship(programmer3, "colleague");
        network.addNode(programmer3);
        Set<Node> expectedResult = new HashSet<>();
        expectedResult.add(programmer1);
        Assertions.assertNotEquals(expectedResult, network.findArticulationPoints(), "Articulation Points works fine");
        Assertions.assertNotEquals(null, network.findArticulationPoints(), "Articulation Points works fine");
    }

}