package test.java;

import main.java.model.Programmer;
import main.java.model.ProgrammingLanguage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

class ProgrammerTest {

    Programmer pr1;

    @BeforeEach
    void Programmer() {

        pr1 = new Programmer("pr1", "01/01/2002");
    }

    @Test
    void getProgrammingLanguageVector_CorrectData_Success() {
        pr1.setProgrammingLanguage(ProgrammingLanguage.Java);
        Assertions.assertEquals(1, pr1.getProgrammingLanguageVector().size(), "Programming language works fine");
        Assertions.assertEquals(ProgrammingLanguage.Java, pr1.getProgrammingLanguageVector().get(0), "Programming language works fine");
        Vector<ProgrammingLanguage> expectedResult = new Vector<>();
        expectedResult.add(ProgrammingLanguage.Java);
        Assertions.assertEquals(expectedResult, pr1.getProgrammingLanguageVector());
    }

}