package org.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JavaClassAnalyzer {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide the path to the .class file as an argument.");
            return;
        }

        String classFilePath = args[0];
        try {
            Class<?> clazz = Class.forName(getClassName(classFilePath));
            System.out.println("Loaded class: " + clazz.getName());
            System.out.println("Package: " + clazz.getPackage().getName());

            analyzeMethods(clazz);
            invokeTestMethods(clazz);
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load the specified class: " + e.getMessage());
        }
    }

    private static String getClassName(String classFilePath) {
        String className = classFilePath.replace(".class", "").replace("/", ".");
        int packageSeparatorIndex = className.lastIndexOf(".");
        if (packageSeparatorIndex != -1) {
            return className.substring(packageSeparatorIndex + 1);
        }
        return className;
    }

    private static void analyzeMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        System.out.println("Methods:");
        for (Method method : methods) {
            System.out.println("Name: " + method.getName());
            System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
            System.out.println("Return Type: " + method.getReturnType().getSimpleName());

            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                System.out.println("Annotation: " + annotation.annotationType().getSimpleName());
            }
            System.out.println();
        }
    }

    private static void invokeTestMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getSimpleName().equals("Test")) {
                    if (Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 0) {
                        try {
                            System.out.println("Invoking test method: " + method.getName());
                            method.invoke(null);
                            System.out.println("Test method invocation successful.\n");
                        } catch (Exception e) {
                            System.out.println("Failed to invoke test method: " + method.getName());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Skipping test method: " + method.getName() +
                                " (Non-static or has parameters)");
                    }
                }
            }
        }
    }
}

//javac JavaClassAnalyzer.java
//java JavaClassAnalyzer /path/to/YourClass.class
