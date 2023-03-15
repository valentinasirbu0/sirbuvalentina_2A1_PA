package test.java;

import main.java.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

class PersonTest {

    Person person;

    @BeforeEach
    void Person() {
        person = new Person();
    }

    @Test
    void testName() {
        person.setName("testperson");
        Assertions.assertEquals("testperson", person.getName(), "Name set and get properly");
    }

    @Test
    void getBirthDate_CorrectData_Success() {
        person.setBirthDate("01/01/2002");
        Assertions.assertEquals("01/01/2002", person.getBirthDate(), "Birth date set and get properly");
        Assertions.assertEquals(true, person.getBirthDate().equals("01/01/2002"), "Birth date set and get properly");
    }

    @Test
    void testAddRelationship_CorrectData_Success() {
        Person otherPerson = new Person();
        person.addRelationship(otherPerson, "boss");
        Map<Person,String> expectedResult = new HashMap<>();
        expectedResult.put(otherPerson,"boss");
        Assertions.assertEquals(expectedResult, person.getRelationships(), "Relationships work fine");
    }
}