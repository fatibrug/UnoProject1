package Uno;

import java.net.MalformedURLException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Card play(String indexStr) {
        int cardIndex = 0;
        cardIndex = Integer.parseInt(indexStr);
        System.out.println("*************************************************************");
        System.out.println("Card played: " + hand.get(cardIndex - 1));
        return hand.remove(cardIndex - 1);
    }

    //show the player what cards he/she has in hand
    @Override
    public void showHand() {
        System.out.println("Player " + name + " : Here are the cards in hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + hand.get(i));
        }
    }

    @Override
    public void resetBotHasDrawn() {

    }

    public String inputAction(Card topCard, Color currentColor) {
        showHand();
        boolean inputError = false;
        Scanner input = new Scanner(System.in);
        String action = "";
        do {
            System.out.println("Please choose the number of the card to discard,The number should be between 1 and " + hand.size());
            System.out.println("If you have no valid card to play, please enter 'd' to draw a card or 's' to skip if you have already drawn a card");
            System.out.println("If you need help, please enter 'h' for help");
            action = input.nextLine();
            try {
                if (action.chars().allMatch(Character::isDigit) && Integer.parseInt(action) > 0 && Integer.parseInt(action) <= hand.size()) {
                    return action;
                } else if (action.equals("d")) {
                    action = "draw";
                } else if (action.equals("h")) {
                    action = "help";
                } else if (action.equals("s")) {
                    action = "skip";
                } else {
                    System.out.println("Please enter a valid choice of action!");
                    inputError = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid input!");
                inputError = true;
            }

        } while (inputError);
        return action;
    }

    @Override
    public int cardValueinHand() {
        return super.cardValueinHand();
    }

    @Override
    public void gainPoints(int gainedPoints) {
        super.gainPoints(gainedPoints);
    }

    //    ask the player if they want to decalre "uno"
    public boolean unoDeclare() {
        System.out.println("If you have only one card left, please enter 'uno'! ");
        Scanner input = new Scanner(System.in);
        String action = input.nextLine();
        if (action.compareToIgnoreCase("uno") == 0) {
            return true;
        }
        return false;
    }

    @Override
    public String decisionToChallenge() {
        Scanner input = new Scanner(System.in);
        String challengeChoice = input.next();
        return challengeChoice;
    }

    @Override
    public String chooseColor() {
        Scanner input = new Scanner(System.in);
        String colorChoice = input.next();
        return colorChoice;
    }
}


