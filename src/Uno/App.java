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
        System.out.println("How many Bots would you like to invite in the game?");
        int botCount = input.nextInt();
        int i=0;
            do {
                System.out.println("Please choose a name for the bot!");
                String botName = input.next();
                System.out.println("Please choose a level for the bot from 1 to 3!");
                int botLevel = input.nextInt();
                if (botLevel==1||botLevel==2||botLevel==3) {
                    players.add(new Bot(botName,botLevel));
                    System.out.println("Welcome to the game, "+"player"+players.size()+ " " +botName+"!");
                    i++;
                } else {
                    output.println("Please choose a valid level number!");
                }
            } while (true && i < botCount);

        int j = 0;
        ArrayList<String> playerNames = new ArrayList<>();
        do {
            System.out.println("Please choose your username!");
            String userName = input.next();
            if (!playerNames.contains(userName)) {
                players.add(new Player(userName));
                System.out.println("Welcome to the game, "+"player"+players.size()+ " " +userName+"!");
                playerNames.add(userName);
                j++;
            } else {
                output.println("The name already exists!");
            }
//
        } while (true && j < 4-botCount);
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


