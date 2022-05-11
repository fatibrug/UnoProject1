package Uno;

import java.util.ArrayList;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class App {

    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private List<Player> players = new ArrayList<>();
    private Carddeck cardsInGame = new Carddeck();

    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public void Run() {
        initialize();
//        printState();

        while (!exit) {
//            readUserInput();
//            updateState();
//            printState();
        }
    }

//    private void readUserInput() {
//      inputName();
//      inputGroesse();
//    }

    private void initialize() {
        cardsInGame.generateDeck();
        createPlayers();
        System.out.println(players);
        createHands();
        for (int i = 0; i < 4; i++) {
            System.out.println(players.get(i).hand);
        }
        System.out.println(cardsInGame.deck.size());
    }


    private void createPlayers() {
        int i = 0;
        ArrayList<String> names = new ArrayList<>();
        do {
            System.out.println("Please choose your username!");
            String userName = input.next();
            if (!names.contains(userName)) {
                players.add(new Player(userName));
                System.out.println("Welcome to the game, "+"player"+players.size()+ " " +userName+"!");
                names.add(userName);
                i++;
            } else {
                output.println("The name already exists!");
            }
//            System.out.println("would you like to play against a bot? Choose ");
        } while (true && i < 4);
    }


    private void createHands() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < 7; j++) {
                players.get(i).hand.add(cardsInGame.deck.get(0));
                cardsInGame.deck.remove(0);
            }
        }
    }
}


