import parser.Parser;

import java.util.Scanner;

public class MainA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        System.out.println(parser.parse(scanner.nextLine()));
    }
}
