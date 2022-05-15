package Uno;

import java.util.ArrayList;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private List<Player> players = new ArrayList<>();
    private Carddeck drawPile = new Carddeck();
    private List<Card> discardPile = new ArrayList<>();
    private int round=1;
    private int session=1;
    private boolean isClockwise = true;

    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public void Run() {
        initialize();


        while (!exit) {
//            updateState();
//            printState();
        }
    }



    private void initialize() {
        drawPile.generateDeck();
        createPlayers();
        System.out.println(players);
        createHands();
        discardPile.add(drawPile.deck.remove(0));
        System.out.println(totalCards());
        playSession();
    }

    private void playSession() {
        do {
            playRound();
        } while (!sessionOver());

        //TODO add logic after session is over if needed
    }

    private void playRound() {
        // Round always starts from the first player
        int currentPlayerIndex = 0;

        do {

            players.get(currentPlayerIndex).play();

            if(isOrderClockwise()) {
                currentPlayerIndex = currentPlayerIndex+1;
                if(currentPlayerIndex > players.size()-1)
                     currentPlayerIndex = 0;
            } else {
                currentPlayerIndex = currentPlayerIndex-1;
                if(currentPlayerIndex < 0)
                    currentPlayerIndex = players.size()-1;
            }

        }
        while(!roundOver());

        //TODO add additional logic for round over
        // count points or whatever
    }

    private boolean isOrderClockwise() {
        //TODO Make logic to check some variable
        // it should be changed when the reverse card is thrown
        return isClockwise;
    }

    private boolean roundOver(){
        for(Player p: players){
            if(p.hand.size()==0){
                round++;
                return true;
            }
        }return false;
    }

    private boolean sessionOver(){
        for(Player p: players){
            if(p.points==500){
                session++;
                return true;
            }
        }return false;
    }


    private int totalCards() {
        int sumPlayerHands = 0;
        for (int i = 0; i < 4; i++) {
            sumPlayerHands += players.get(i).hand.size();
        }
        return sumPlayerHands + drawPile.deck.size() + discardPile.size();
    }


    private void createPlayers() {
        System.out.println("How many Bots would you like to invite in the game?");
        int botCount = input.nextInt();
        String botName = "";
        int i = 0;
        boolean error = false;
        do {
            if (!error) {
                System.out.println("Please choose a name for the bot!");
                botName = input.next();
            }
            System.out.println("Please choose a level for the bot from 1 to 3!");
            int botLevel = input.nextInt();
            if (botLevel > 0 && botLevel < 4) {
                error = false;
                if (botLevel == 1) {
                    players.add(new Bot(botName));
                    System.out.println("Welcome to the game, " + "player" + players.size() + " " + botName + "!");

                } else if (botLevel == 2) {
                    players.add(new SmartBot(botName));
                    System.out.println("Welcome to the game, " + "player" + players.size() + " " + botName + "!");

                } else if (botLevel == 3) {
                    players.add(new ExpertBot(botName));
                    System.out.println("Welcome to the game, " + "player" + players.size() + " " + botName + "!");
                }
                i++;
            } else {
                output.println("Please choose a valid level number!");
                error = true;
            }


        }
        while (true && i < botCount);


        int j = 0;
        ArrayList<String> playerNames = new ArrayList<>();
        do {
            System.out.println("Please choose your username!");
            String userName = input.next();
            if (!playerNames.contains(userName)) {
                players.add(new HumanPlayer(userName));
                System.out.println("Welcome to the game, " + "player" + players.size() + " " + userName + "!");
                playerNames.add(userName);
                j++;
            } else {
                output.println("The name already exists!");
            }
//
        } while (true && j < 4 - botCount);
    }


    private void createHands() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < 7; j++) {
                players.get(i).hand.add(drawPile.deck.remove(0));
            }
        }
    }
}


