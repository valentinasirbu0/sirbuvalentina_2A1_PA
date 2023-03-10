import java.util.Arrays;

public class Lab1 {
    public static void main(String[] args) {
        Lab1 app = new Lab1();
        app.hello();
        Lab1 lab1 = new Lab1();
        lab1.compulsory();
        lab1.homework(args);
        lab1.bonus(args);
    }

    public static void adjacencyMatrix(int vertices, int[] degree) {
        int[][] result = new int[vertices][vertices];
        Arrays.sort(degree);

        for (int i = 0; i < vertices; i++) {
            if (degree[i] != 1) {
                for (int j = 0; j < vertices; j++) {
                    if (i != j && degree[i] > 0 && degree[j] > 0) {
                        result[i][j] = 1;
                        result[j][i] = 1;
                        degree[i]--;
                        degree[j]--;
                    } else {
                        result[i][j] = 0;
                        result[j][i] = 0;
                    }
                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    void bonus(String[] args) {
        int n = Integer.parseInt(args[1]);
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
            matrix[i][(i + 1) % n] = 1;
        }
        printMatrix(matrix);
        
        matrix = multiplyMatrix(matrix, n);
        printMatrix(matrix);
        int vertices = 4;
        int[] degree = {2, 2, 2, 2};
        adjacencyMatrix(vertices, degree);
    }

    int[][] multiplyMatrix(int[][] matrix, int power) {
        int[][] result = new int[matrix.length][matrix.length];
        if (power == 1) {
            return matrix;
        } else {
            int[][] m1 = new int[matrix.length][];
            int[][] m2 = new int[matrix.length][];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    result[i][j] = 0;
                    if (power % 2 == 0) {
                        m1 = multiplyMatrix(matrix, power / 2);
                        m2 = multiplyMatrix(matrix, power / 2);
                    } else {
                        m1 = multiplyMatrix(matrix, power / 2);
                        m2 = multiplyMatrix(matrix, power / 2 + 1);
                    }
                    for (int k = 0; k < matrix.length; k++) {
                        result[i][j] += m1[i][k] * m2[k][j];
                    }
                }
            }

        }
        return result;
    }

    private void hello() {
        System.out.println("Hello World!");
    }

    void homework(String[] args) {
        int n;
        try {
            n = Integer.parseInt(args[0]);
            System.out.println(args[0] + "Valid integer");
        } catch (NumberFormatException e) {
            System.out.println("Wrong argument, default argument 10");
            n = 10;
        }
        int[][] matrix = new int[n][n];
        if (n > 30_000) {
            long startTime = System.nanoTime();
            matrix = createMatrix(n);
            long endTime = System.nanoTime();
            long timeDifference = endTime - startTime;
            System.out.print(timeDifference);
        }
        matrix = createMatrix(n);
        printMatrix(matrix);
        concatMatrix(matrix);
    }

    void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    int[][] createMatrix(int n) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            int p = 1;
            int j;
            for (j = i; j < n; j++) {
                matrix[i][j] = p++;
            }
            if (j > 0) {
                for (int k = 0; k < i; k++) {
                    matrix[i][k] = p++ % 11;
                }
            }
        }
        return matrix;
    }

    void concatMatrix(int[][] matrix) {
        StringBuilder[] obj = new StringBuilder[matrix.length * 2];

        for (int i = 0; i < matrix.length; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < matrix[i].length; j++) {
                str.append(matrix[i][j]);
            }
            obj[i] = str;
        }
        for (int j = 0; j < matrix.length; j++) {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < matrix[j].length; i++) {
                str.append(matrix[i][j]);
            }
            obj[j + matrix.length] = str;
        }
        for (int i = 0; i < obj.length; i++) {
            System.out.println(obj[i]);
        }
    }

    void compulsory() {
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};
        int n = (int) (Math.random() * 1_000_000);
        n *= 3;
        n += 0b10101;
        n += 0xFF;
        n *= 6;
        int sum;
        while (n > 9) {
            sum = 0;
            while (n > 0) {
                sum += n % 10;
                n = n / 10;
            }
            n = sum;
            //System.out.println("Sum of Digits: " + n);
        }
        System.out.println("Willy-nilly, this semester I will learn " + languages[n]);
    }
}
