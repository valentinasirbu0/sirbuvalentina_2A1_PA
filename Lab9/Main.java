package org.example;

import org.example.JDBC.JdbcDAOFactory;
import org.example.JPA.JpaDAOFactory;

public class Main {
    public static void main(String[] args) {
        //TestClass.testClass();
        //CSVData data = new CSVData();

        String persistenceType = "jpa"; // or "jdbc"
        DAOFactory factory;
        if (persistenceType.equals("jpa")) {
            factory = new JpaDAOFactory();
        } else {
            factory = new JdbcDAOFactory();
        }
        factory.setData();


    }
}