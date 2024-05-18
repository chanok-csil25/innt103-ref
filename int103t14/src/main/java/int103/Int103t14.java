package int103;

import java.io.IOException;
import java.util.Scanner;

public class Int103t14 {

    public static void main(String[] args) throws IOException {
        //demoScannerFromString();
        //demoScannerSystemIn();
        demoScannerExpectedInput();
    }
    public static void demoScannerExpectedInput() throws IOException {
        var sc = new Scanner(System.in);
        sc.useDelimiter("\\n");
        System.out.print("Your name, please: ");
        String name = sc.next();
        System.out.println("Hello, " + name);
        //sc.nextLine();
        double age = 0.0;
        while (true) {
            System.out.print("Your age, please: ");
            if (sc.hasNextDouble()) {
                age = sc.nextDouble();
                break;
            }
            sc.next();
        }
        
        System.out.println("You are " + age + " years old, right?");
        System.out.println("Good Bye.");
    }
    public static void demoScannerFromString() {
        String s = """
             This is the first line.
             12.345656
             100010  x
             39  y
             mx true
             o
                   4
             the end""";
        var sc = new Scanner(s);
        //var sc = new Scanner(new File("abc.txt"));
        //sc.useDelimiter("\\s+");
        int i = 0;
        while (sc.hasNext()) {
            if (sc.hasNextBoolean()) {
                System.out.println(++i + " : boolean = " + sc.nextBoolean());
            } else if (sc.hasNext("\\b.\\b")) {
                System.out.println(++i + " : char = " + sc.next());
            } else if (sc.hasNextInt(2)) {
                System.out.println(++i + " : binary number = " + sc.nextInt(2));
            } else if (sc.hasNextInt()) {
                System.out.println(++i + " : int = " + sc.nextInt());
            } else if (sc.hasNextLong()) {
                System.out.println(++i + " : long = " + sc.nextLong());
            } else if (sc.hasNextDouble()) {
                System.out.println(++i + " : double = " + sc.nextDouble());
            } else if (sc.hasNext()) {
                System.out.println(++i + " : String = " + sc.next());
            }
        }
        System.out.println("END");
    }
    public static void demoScannerSystemIn() throws IOException {
        var sc = new Scanner(System.in);
        System.out.print("Please enter something: ");
        while (sc.hasNextLine()) {
            if (sc.hasNext("\\b[Q|q]\\b")) {
                System.out.println("Good Bye");
                break;
            } else if (sc.hasNextBoolean()) {
                System.out.println("boolean = " + sc.nextBoolean());
            } else if (sc.hasNext("\\b.\\b")) {
                System.out.println("char = " + sc.next());
            } else if (sc.hasNextInt()) {
                System.out.println("int = " + sc.nextInt());
            } else if (sc.hasNextLong()) {
                System.out.println("long = " + sc.nextLong());
            } else if (sc.hasNextDouble()) {
                System.out.println("double = " + sc.nextDouble());
            } else if (sc.hasNext()) {
                System.out.println("String = " + sc.next());
            }
            System.out.print("Please enter something: ");
        }
        System.out.println("Have a good day.");
    }
}
