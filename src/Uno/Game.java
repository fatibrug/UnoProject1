package Uno;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.SwitchPoint;
import java.util.*;
import java.io.PrintStream;

public class Game {

    private final Scanner input;
    private final PrintStream output;
    private boolean exit = false;
    private List<Player> players = new ArrayList<>();
    private Carddeck drawDeck = new Carddeck();
    private DiscardDeck discardDeck = new DiscardDeck();
    private int round = 1;
    private int session = 1;
    private boolean isClockwise = true; // check if the direction has been changed. It should change only once
    private boolean skipped = false; // the skip card should only make one player skip. Once skipped, it can only be skipped again when another skip card is played
    private int currentPlayerIndex = 0;
    private Player currentPlayer;
    private boolean drawn = false; //avoid the user inputting "draw" twice in a row
    private boolean isChallenged = false;// when pull 4 card played, only one challenge chance
    private boolean hasCheated = false;
    private boolean isColorSelected = false;
    private boolean isSkippedOrInvalid = false;
    private HashMap<String, Integer> playerPoint;
    private Color currentColor = null;
    private Color previousColor = null;

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
            DiscardPilebecomesDrawPileIfDrawPileEmpty();
        } while (!roundOver());
        updateState();
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

    // initialize the game:
// generate the drawdeck with 108 cards
// create 4 players and shuffle the players to pick the first player to start the game
// 7 cards for each players
// pick the last card from the draw pile to put on the discard pile
// If the top card of discard pile is a draw 4 action card, the card will be returned to the draw pile.
// The draw pile will be reshuffled and a new top card from the drawn from the draw pile.
    private void initialize() {
        drawDeck.generateDeck();
        createPlayers();
        //Collections.shuffle(players); //Just removed for easier debug
        System.out.println(players);
        currentPlayer = players.get(0);
        createHands();
        discardDeck.getNewCard(drawDeck.drawACard());
        currentColor = (Color) getTopCard().color;
        showLastDiscardedCard();
        previousColor = currentColor;
        checkIfDraw4TurnUpAtTheBeginning();

    }

    //The game player loop: It starts from the 0 position of the player list and loop with the nextPlayer method
    private void playerLoop() {
        //The game begin with the index 0 unless the first card is the skip or pull2 or pull 4 card.
        //if the current player didn't have to draw 2 or 4 cards, he can play a valid card. Otherwise, he has to draw cards and skip his/her turn.
//        if (!suspend() && !ifDrawActionCards()) {
//            currentPlayer = players.get(currentPlayerIndex);
//
//        } else currentPlayer = nextPlayer();

        if(getTopCard().number == 11) { // SUSPEND
         suspend();
         currentPlayer = nextPlayer();

        } else
            if(getTopCard().number == 14) { // +4
                if(!isSkippedOrInvalid) {
                    colorSelection();
                }

                challenged();
                if (isChallenged) {
                    draw4Penalty();
                    isChallenged = false;
                }
                else {
                    for (int i = 0; i < 4; i++) {
                        nextPlayer().hand.add(drawDeck.drawACard());

                    }
//                    currentPlayer.showHand();
                    nextPlayer().showHand();
                }
                if(isColorSelected) {
                    currentPlayer = nextPlayer();
//                    isColorSelected = false;
                }
                if (hasCheated) currentPlayer = nextPlayer();
            }
        //} else currentPlayer = players.get(currentPlayerIndex);

        else if(getTopCard().number == 12) { // +2
            draw2Penalty();
//            currentPlayer = nextPlayer();

        }

        else if(getTopCard().number == 13) { // COLOR SELECTION
                if(!isSkippedOrInvalid) {
                    colorSelection();
                }
            if(isColorSelected) {
                currentPlayer = nextPlayer();
//                isColorSelected = false;
            }
//            currentPlayer = nextPlayer();
        }
                output.println("**************************************************************");
                System.out.println("It is your turn to play, " + currentPlayer.name + "!");
                showLastDiscardedCard();
                readUserInput();
                if (currentPlayer.hand.size() == 1) {
                    checkUnoNoDeclaredPenalty();
                }
//        isChallenged = false; // if next draw 4 appears, the current player will be asked to challenge again
                drawn = false;// drawn back to false for the next player


        if(getTopCard().number != 13 && getTopCard().number != 14) {
            currentPlayer = nextPlayer();
            isColorSelected = false;
        }

//      else currentPlayer = players.get(currentPlayerIndex);

//        if(ifDrawActionCards())
//                currentPlayer = previousPlayer();
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

    // create hands for all players, each with 7 cards from the draw pile
    private void createHands() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < 7; j++) {
                players.get(i).hand.add(drawDeck.drawACard());
            }
        }
    }

    // return the top card on the discard pile
    private Card getTopCard() {

        return discardDeck.topCard();
    }

    private Card getPreviousCard() {
        int size = discardDeck.getDeck().size();
        return discardDeck.getDeck().get(size - 2);
    }

    //show the last card on the discard pile that player shall refer to.
    private void showLastDiscardedCard() {
        output.println("The last card on Discard pile is: " + getTopCard().toString() + "         The current color is " + currentColor.name());
    }

    //read user input of the current user before the play method. The bot user input is null.
    private void readUserInput() {
        String inputAction = currentPlayer.inputAction(getTopCard(), currentColor);
        if (inputAction.equals("draw")) {
            if (!drawn) {
                // if the input action id "draw" , the current player draws a card from the drawPile and add to the hand of the current player if he/she has not drawn a card before
                currentPlayer.hand.add(drawDeck.drawACard());
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
            } else { //
                // if skip, then the next player becomes the current player
                output.println("I have no valid card to play and will skip!");
                isSkippedOrInvalid = true;
            }
        } else if (inputAction.chars().allMatch(Character::isDigit) && Integer.parseInt(inputAction) > 0 && Integer.parseInt(inputAction) <= currentPlayer.hand.size()) {
            // the default case is when a valid index number is given as input, then play method will be called.
            //the return value of the play method will be the card to be added to the discard pile
            isSkippedOrInvalid = false;
            Card currentTopCard = getTopCard();
            Card card = currentPlayer.play(inputAction);

            discardDeck.getNewCard(card);
//            if (!card.color.equals(currentTopCard.color) && card.number != currentTopCard.number) {
            //if (!card.color.equals(colorSelection()) && card.number != currentTopCard.number) {
//            String c1 = card.color.name();
//            String c2 = currentColor.name();

            if (!card.color.name().equals(currentColor.name()) && card.number != currentTopCard.number) {
                if (card.number != 13 && card.number != 14) {
                    //check if card is valid. If not valid, the player will take back his last played card from the discard pile and draw a penalty card from the draw pile.
                    currentPlayer.hand.add(getTopCard());
                    discardDeck.removeCard(getTopCard());
                    currentPlayer.hand.add(drawDeck.drawACard());
                    output.println("The move is invalid! You shall take back your card and get a penalty card. Here are your cards in hand now: ");
                    output.println(currentPlayer.hand);
                    isSkippedOrInvalid = true;
                }


            }
            currentColor = (Color) card.color;
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


    private Player previousPlayer() {
        if (isOrderClockwise()) {
            currentPlayerIndex = currentPlayerIndex - 1;

            if (currentPlayerIndex < 0)
                currentPlayerIndex = players.size() - 1;
        } else {
            currentPlayerIndex = currentPlayerIndex + 1;
            if (currentPlayerIndex > players.size() - 1)
                currentPlayerIndex = 0;
        }
        Player previousPlayer = players.get(currentPlayerIndex);
        return previousPlayer;
    }

    //    here are the methods to update the game state
    private void updateState() {
        updateWinnerPoints();
    }

    private boolean roundOver() {
        for (Player p : players) {
            if (p.hand.size() == 0) {
                round++;
                return true;
            }
        }
        return false;
    }

    //    method to calculate the points gained by the winner in each round
    private void updateWinnerPoints() {
        Player winner = null;
        if (roundOver()) {
            for (Player p : players) {
                if (p.hand.size() == 0) {
                    winner = p;
                }
            }
            System.out.println(winner.name + " has won.");
            winner.gainPoints(totalCardPoints());
        }
    }

    //    method to calculate all the card values in the player hands.
    private int totalCardPoints() {
        int totalCardPoints = 0;
        for (Player p : players) {
            totalCardPoints += p.cardValueinHand();
        }
        return totalCardPoints;
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

    // print the current state of the game
    private void printState() {
        System.out.println("Session: " + session + ", Round: " + round);
        System.out.println(totalCards());
    }


    // calculate the total number of cards in the game-It should be always 108
    private int totalCards() {
        int sumPlayerHands = 0;
        for (int i = 0; i < 4; i++) {
            sumPlayerHands += players.get(i).hand.size();
        }
        return sumPlayerHands + drawDeck.getCount() + discardDeck.getCount();
    }


    //below will be the mothods to implement the game rules:

    //The retour card will change the player loop order to be counter clock wise.
    private boolean isOrderClockwise() {
        //TODO Make logic to check some variable
        // it should be changed when the reverse card is thrown
        if (getTopCard().number == 10) {
            if (isClockwise)
                isClockwise = false;
            else isClockwise = true;
        }
        return isClockwise;
    }



    private void draw2Penalty() {
        if (getTopCard().number == 12) {
            for (int i = 0; i < 2; i++) {
                currentPlayer.hand.add(drawDeck.drawACard());
            }
        }
    }

    //    In case of challenge, if the current player has cheated, he should draw 4 cards. If not, the next player will draw 6 cards.
    private void draw4Penalty() {
        if (!hasCheated) {
            output.println("Player (" + currentPlayer + ") didnt cheat, you will get a penalty and draw 6 cards!");
            for (int i = 0; i < 6; i++) {
                nextPlayer().hand.add(drawDeck.drawACard());

            }
            nextPlayer().showHand();
        } else {
            output.println("Player (" + currentPlayer + ") cheated, he will get a penalty and draw 4 cards!");
            for (int i = 0; i < 4; i++) {
                currentPlayer.hand.add(drawDeck.drawACard());

            }
            currentPlayer.showHand();
        }
    }


    // check if the draw4 card turns up at the beginning of the game. if yes, the card will be put back to the draw pile and the draw pile will be reshuffled.
    private void checkIfDraw4TurnUpAtTheBeginning() {
        if (getTopCard().number == 14) {
            drawDeck.addCard(discardDeck.drawACard());
            drawDeck.shuffle();
            discardDeck.getNewCard(drawDeck.drawACard());
        }
    }

    //    requirement 31: when the pull 4 played, the next player can verify if the current player has cheated or not. The current player needs to show his hand
    private void cheated() {
                boolean checkCheating = false;
                previousPlayer().showHand();
                for (Card c : previousPlayer().hand) {
                    if (c.number == getPreviousCard().number || c.color.name().equals(previousColor.name())) {
                        checkCheating = true;
                    }
                }

        if(checkCheating)
            hasCheated = true;
        else
            hasCheated = false;
    }


    private void challenged() {
        if (getTopCard().number == 14 && !isChallenged) {
            output.println("Would you like to challenge, " + nextPlayer().name + "? " + "Please enter 'Y' or 'N'.");
            String changeChoice = input.next();

            if (changeChoice.equalsIgnoreCase("Y")) {
                isChallenged = true;
                cheated();

            }
            else isChallenged = false;
        }


    }


    //if the draw pile runs out, the discard pile will be shuffled and become the new draw pile. The first card will be placed on the discard pile
    private void DiscardPilebecomesDrawPileIfDrawPileEmpty() {
        if (drawDeck.isEmpty()) {
            drawDeck.deckSwap(discardDeck.getDeck());
        }
    }

    //in case of ta suspend card, the current player's turn will be skipped
    private boolean suspend() {
        if (getTopCard().number == 11) {
            if (!skipped) {
                System.out.println("Oops, you have to skip your turn because of the suspend card!");
                skipped = true;
            } else skipped = false;

        }
        return skipped;
    }

    //    check if the player forgets to declare UNO in time. If not, he/she will get a penalty card
    private void checkUnoNoDeclaredPenalty() {
        boolean unoNoDeclaredPenalty = false;
        if (currentPlayer.unoDeclare()) {
            unoNoDeclaredPenalty = false;
        }
        else unoNoDeclaredPenalty = true;
       if (unoNoDeclaredPenalty) {
            currentPlayer.hand.add(drawDeck.drawACard());
        }
    }

    // this method decide the color that the game rule will take to judge if the played card is valid or not.
    private void colorSelection() {
//        output.println("This is color selection function: top card number, "+getTopCard().number);

        boolean invalidColor = false;
        if (getTopCard().number != 13 && getTopCard().number != 14) {
            currentColor = (Color) getTopCard().color;

        } else {
            do {
                output.println(currentPlayer.name + " --> You may select a different color to play: 'R' for red, 'B' for blue, 'G' for green and 'Y' for yellow");
                Scanner input = new Scanner(System.in);
                String c = "";
                String[] colorStrings = {"R", "Y", "G", "B"};
                if(currentPlayer.getClass().equals(HumanPlayer.class)){
                    c = input.next();
                }
                else if(currentPlayer.getClass().equals(Bot.class)) {
                    c = colorStrings[(int)Math.floor(Math.random()*4)];
                }
                else if(currentPlayer.getClass().equals(SmartBot.class)){
                    c= ((SmartBot)currentPlayer).smartBotChoosesColor();
                }

                if (c.equalsIgnoreCase("R")) {
                    previousColor = (Color) getPreviousCard().color;
                    currentColor = Color.RED;
                    output.println("Color changed to red / previous color was " + previousColor);
                    invalidColor = false;
                    isColorSelected = true;
                } else if (c.equalsIgnoreCase("B")) {
                    previousColor = (Color) getPreviousCard().color;
                    currentColor = Color.BLUE;
                    output.println("Color changed to blue / previous color was " + previousColor);
                    invalidColor = false;
                    isColorSelected = true;
                } else if (c.equalsIgnoreCase("G")) {
                    previousColor = (Color) getPreviousCard().color;
                    currentColor = Color.GREEN;
                    output.println("Color changed to green / previous color was " + previousColor);
                    invalidColor = false;
                    isColorSelected = true;
                } else if (c.equalsIgnoreCase("Y")) {
                    previousColor = (Color) getPreviousCard().color;
                    currentColor = Color.YELLOW;
                    output.println("Color changed to yellow / previous color was " + previousColor);
                    invalidColor = false;
                    isColorSelected = true;
                } else {
                    output.println("The input color is not valid, please select a valid color!");
                    invalidColor = true;
                }
//                output.println("Color selected" + colorSelected + ", invalid color: " + invalidColor);
            } while (invalidColor);
        }

    }

}
