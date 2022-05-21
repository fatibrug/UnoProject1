package Uno;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.PrintStream;

public class Game {

    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private List<Player> players = new ArrayList<>();
    private Carddeck gameDeck = new Carddeck();
    private List<Card> drawPile = gameDeck.deck;
    private List<Card> discardPile = new ArrayList<>();
    private int round = 1;
    private int session = 1;
    private boolean isClockwise = true;
    private int currentPlayerIndex = 0;
    private Player currentPlayer;
    private boolean drawn = false; //avoid the user inputting "draw" twice in a row

    public Game(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    public void Run() {
        initialize();
        printState();
        do {
            playerLoop();
            printState();
        } while (!roundOver());
        //      updateState();
        //} while (!sessionOver());


    }

    //method to print out the hands of the 4 players for debugging purposes
    private void printPlayerHand() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < players.get(j).hand.size(); i++) {
                System.out.println(players.get(j).name + " Card " + (i + 1) + ": " + players.get(j).hand.get(i));
            }
        }
    }

    private void initialize() {
        gameDeck.generateDeck();
        createPlayers();
        Collections.shuffle(players);
        System.out.println(players);
        createHands();
        discardPile.add(drawPile.remove(0));
    }

    //The game player loop: It starts from the 0 position of the player list and loop with the nextPlayer method
    private void playerLoop() {
        currentPlayer = players.get(currentPlayerIndex);
        ifDrawActionCards();
        //if the current player didn't have to draw 2 or 4 cards, he can play a valid card. Otherwise, he has to draw cards and skip his/her turn.
        if (ifDrawActionCards() != true) {
            output.println("**************************************************************");
            System.out.println("It is your turn to play, " + currentPlayer.name + "!");
            showLastDiscardedCard();
            readUserInput();
            drawn = false;// drawn back to false for the next player
        }
        currentPlayer = nextPlayer();
    }

    //create 4 players, first select the number of bots (error exception catch used in case if the number is invalid or not a number)
//then select the level of the bot(error exception catch used in case if the number is invalid or not a number)
//then create the human players
    private void createPlayers() {
        boolean countError = false;
        int botCount = 0;
        do {
            try {
                System.out.println("How many Bots would you like to invite in the game?");
                String botCountStr = input.next();
                botCount = Integer.parseInt(botCountStr);

                if (botCount < 0 || botCount > 4) {
                    output.println("The number of bots should be between 1 and 4!");
                    countError = true;
                } else countError = false;

            } catch (Exception e) {
                output.println("Please choose a valid number for the total bot players!");
                countError = true;
            }
        } while (countError);

        int i = 0;
        String botName = "";
        boolean levelError = false;
        while (true && i < botCount && botCount <= 4) {
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
                players.get(i).hand.add(drawPile.remove(0));
            }
        }
    }

    //show the last card on the discard pile that player shall refer to.
    private void showLastDiscardedCard() {
        output.println("The last card on Discard pile is: " + discardPile.get(discardPile.size()-1).toString());
    }

    //read user input of the current user before the play method. The bot user input is null.
    private void readUserInput() {
        String inputAction = currentPlayer.inputAction();
        if (inputAction.equals("draw")) {
            if (!drawn) {
                // if the input action id "draw" , the current player draws a card from the drawPile and add to the hand of the current player if he/she has not drawn a card before
                currentPlayer.hand.add(drawPile.remove(0));
                drawn = true;
            } else {
                output.println("invalid action! You have already drawn a card/cards");
            }
            readUserInput();
        } else if (inputAction.equals("help")) {
            //read the game rules from the text file in the console.
            readRules();
            System.out.println("I hope you have understood the rules and have decided the next move :), " + currentPlayer.name + "!");
            readUserInput();
        } else if (inputAction.equals("skip")) {
            //the player is not allowed to choose skip input if he has not drawn a card when he has no valid card to play
            if (!drawn) {
                output.println("Invalid input, you cannot skip. Please choose 'd' to draw a card/cards if you have no valid card to play");
            } else
                // if skip, then the next player becomes the current player
                output.println("I have no valid card to play and will skip!");
        } else if (inputAction.chars().allMatch(Character::isDigit) && Integer.parseInt(inputAction) > 0 && Integer.parseInt(inputAction) <= currentPlayer.hand.size()) {
            // the default case is when a valid index number is given as input, then play method will be called.
            //the return value of the play method will be the card to be added to the discard pile
            Card card = currentPlayer.play(inputAction);
            discardPile.add(card);
            if (!card.color.equals(discardPile.get(0).color) || card.number != discardPile.get(0).number) {
                //check if card is valid. If not valid, the player will take back his card and get a penalty card.
                currentPlayer.hand.add(discardPile.remove(discardPile.size()-1));
                currentPlayer.hand.add(drawPile.remove(0));
                output.println("The move is invalid! You shall take back your card and get a penalty card. Here are your cards in hand now: ");
                output.println(currentPlayer.hand);
            }

        }
    }

    //method for finding out who is the next player. When the index is out of bound, the index return to 0 or the last index of the array list.
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

    private void readRules() {
        try {
            File myObj = new File("gamerules.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private void printState() {
        System.out.println(totalCards());
    }


    // method to display the player number and the points
    private HashMap<String, Integer> playerPoint() {
        HashMap<String, Integer> map = new HashMap<>();
        for (Player p : players) {
            map.put(p.name, p.points);
        }
        return map;
    }


    private int totalCards() {
        int sumPlayerHands = 0;
        for (int i = 0; i < 4; i++) {
            sumPlayerHands += players.get(i).hand.size();
        }
        return sumPlayerHands + drawPile.size() + discardPile.size();
    }


    //below will be the mothods to implement the game rules:

    //The retour card will change the player loop order to be counter clock wise.
    private boolean isOrderClockwise() {
        //TODO Make logic to check some variable
        // it should be changed when the reverse card is thrown
        if (discardPile.get(0).number == 10) {
            return false;
        }
        return true;
    }


    //the method below check if a pull 2 or draw 4 cards is on the discard pile. If yes, the current player should draw 2 or 4 cards
    //the boolean return value shall be used in the player loop method
    private boolean ifDrawActionCards() {
        boolean drawAction = false;
        int numberOfCardToDraw = 0;
        if (discardPile.get(0).number == 12 || discardPile.get(0).number == 14) {
            numberOfCardToDraw = drawPile.get(0).number - 10;
            for (int i = 0; i < numberOfCardToDraw; i++) {
                currentPlayer.hand.add(drawPile.remove(0));
                return true;
            }
        }
        return false;
    }

    //if the draw pile runs out, the discard pile will be shuffled and become the new draw pile. The first card will be placed on the discard pile
    private void discardPileBecomesDrawPile(){
        if(drawPile.size()==0){
            for(int i=0; i<discardPile.size(); i++){
                drawPile.add(discardPile.remove(i));
            }
            Collections.shuffle(drawPile);
            discardPile.add(drawPile.remove(0));
        }
    }
}
