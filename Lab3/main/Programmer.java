package main.java.model;

import java.util.Vector;

public class Programmer extends Person {

    Vector<ProgrammingLanguage> programmingLanguageVector = new Vector<ProgrammingLanguage>();

    public Programmer() {

        System.out.println("Programmer created");
    }

    public Programmer(String name, String date) {
        this.setName(name);
        this.setBirthDate(date);
    }

    public Vector<ProgrammingLanguage> getProgrammingLanguageVector() {

        return programmingLanguageVector;
    }

    public void setProgrammingLanguage(ProgrammingLanguage language) {

        this.programmingLanguageVector.add(language);
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "name=" + getName() +
                ", programmingLanguageVector=" + programmingLanguageVector +
                ", degree=" + getDegree() +
                '}';
    }
}
