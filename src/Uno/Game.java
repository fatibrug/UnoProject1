package Uno;

import java.util.ArrayList;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private List<Player> players = new ArrayList<>();
    private Carddeck drawPile = new Carddeck();
    private List<Card> discardPile = new ArrayList<>();
    private int round = 1;
    private int session = 1;
    private boolean isClockwise = true;
    private int currentPlayerIndex = 0;
    private Player currentPlayer;

    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public void Run() {
        initialize();
        printState();
//        for(int j=0; j<4; j++) {
//            for (int i = 0; i < players.get(j).hand.size(); i++) {
//                System.out.println(players.get(j).name+" Card " + (i + 1) + ": " + players.get(j).hand.get(i));
//            }
//        }
            do {
                playerLoop();
                printState();
            } while (!roundOver());
            //} while (!sessionOver());

//        readUserInput();
//        updateState();

    }

    private void initialize() {
        drawPile.generateDeck();
        createPlayers();
        Collections.shuffle(players);
        System.out.println(players);
        createHands();
        discardPile.add(drawPile.deck.remove(0));
        currentPlayer = players.get(0);
    }


    private void printState() {
        System.out.println(totalCards());

    }

    private void playerLoop() {
        System.out.println("It is your turn to play, " + currentPlayer.name + "!");
        String inputAction= currentPlayer.inputAction();
        if(inputAction.equals("draw")){
            // draw a new card
        }else if(inputAction.equals("help")){
            //show help menu
        }else{
            Card card = currentPlayer.play(inputAction);
            //check if card is null, if null repeat the loop
            discardPile.add(card);
        }
        currentPlayer = nextPlayer();
    }

    private Player nextPlayer() {


            if (isOrderClockwise()) {
                currentPlayerIndex = currentPlayerIndex + 1;


                if (currentPlayerIndex > players.size() - 1)
                    currentPlayerIndex = 0;
            } else {
                currentPlayerIndex = currentPlayerIndex - 1;
                if (currentPlayerIndex < 0)
                    currentPlayerIndex = players.size() - 1;
            }
            Player nextPlayer = players.get(currentPlayerIndex);
            return nextPlayer;



    }


    private boolean isOrderClockwise() {
        //TODO Make logic to check some variable
        // it should be changed when the reverse card is thrown
        return true;
    }

    private boolean roundOver() {
        for (Player p : players) {
            if (p.hand.size() == 0) {
                round++;
                System.out.println(p.name + " has won.");
                return true;
            }
        }
        return false;
    }

    private boolean sessionOver() {
        for (Player p : players) {
            if (p.points >= 500) {
                session++;
                return true;
            }
        }
        return false;
    }


    private int totalCards() {
        int sumPlayerHands = 0;
        for (int i = 0; i < 4; i++) {
            sumPlayerHands += players.get(i).hand.size();
        }
        return sumPlayerHands + drawPile.deck.size() + discardPile.size();
    }

//create 4 players, first select the number of bots (error exception catch used in case if the number is invalid or not a number)
//then select the level of the bot(error exception catch used in case if the number is invalid or not a number)
//then create the human players
    private void createPlayers() {
        boolean countError = false;
        int botCount = 0;
        do{
            try{
                System.out.println("How many Bots would you like to invite in the game?");
                String botCountStr = input.next();
                botCount = Integer.parseInt(botCountStr);

                if(botCount <0 || botCount>4){
                    output.println("The number of bots should be between 1 and 4!");
                    countError = true;
                } else countError = false;

        }catch (Exception e){
            output.println("Please choose a valid number for the total bot players!");
                countError = true;
        }
    }while (countError);

            int i = 0;
            String botName = "";
            boolean levelError = false;
        while (true && i < botCount && botCount<=4) {
            if (!levelError) {
                System.out.println("Please choose a name for the bot!");
                botName = input.next();
            }
            System.out.println("Please choose a level for the bot from 1 to 3!");

            try {
                String botLevelStr = input.next();
                int botLevel = Integer.parseInt(botLevelStr);
                if (botLevel > 0 && botLevel < 4) {
                    levelError = false;
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
                    levelError = true;
                }
            } catch (Exception e) {
               output.println("Please choose a valid level for your bot!");
            }
        }

        int j = 0;
        ArrayList<String> playerNames = new ArrayList<>();
        while (j < 4 - botCount) {
            System.out.println("Please choose the username for the human player!");
            String userName = input.next();
            if (!playerNames.contains(userName)) {
                players.add(new HumanPlayer(userName));
                System.out.println("Welcome to the game, " + "player" + players.size() + " " + userName + "!");
                playerNames.add(userName);
                j++;
            } else {
                output.println("The name already exists!");
            }
        }
    }


    private void createHands() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < 7; j++) {
                players.get(i).hand.add(drawPile.deck.remove(0));
            }
        }
    }
}



