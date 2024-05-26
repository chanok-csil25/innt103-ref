package int103;

import java.io.*;
import java.util.Scanner;


public class Int103w14 {

    public static void main(String[] args) throws IOException {
        //demoPassword();
        demoScannerFromString();
    }
    public static void demoScannerFromString() throws IOException {
        String src = """
            This is the first line.
            This is the second line.
            false 12.345678 86534232564765 5657567868978735342535
            x y 1000111 mm a the end""";
        var sc = new Scanner(src);
        sc.useDelimiter("\\s+");
        while (sc.hasNext()) {
            if (sc.hasNextBoolean()) {
                boolean b = sc.nextBoolean();
                System.out.println("boolean: " + b);
            } else if (sc.hasNextInt(2)) {
                System.out.println("binary value: " + sc.next());
            } else if (sc.hasNextInt()) {
                System.out.println("int: " + sc.nextInt());
            } else if (sc.hasNextLong()) {
                System.out.println("long: " + sc.nextLong());
            } else if (sc.hasNextDouble()) {
                System.out.println("double: " + sc.nextDouble());
            } else if (sc.hasNext("\\b.\\b")) {
                char c = sc.next().charAt(0);
                System.out.println("char: " + c);
            } else 
                System.out.println("String: " + sc.next());
        }
    }
    public static void demoPassword() {
        String password;
        var cons = System.console();
        if (cons != null) {
            System.out.print("Password: ");
            password = new String(cons.readPassword());
        } else {
            System.out.println("[public]");
            System.out.print("Password: ");
            var sc = new Scanner(System.in);
            sc.useDelimiter("\\n");
            password = sc.next();
        }
        System.out.println("Your password is :[" + password + "]");
    }
}
