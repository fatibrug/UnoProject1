package Uno;


import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game app1= new Game(input, System.out);
        app1.Run();


    }
}
