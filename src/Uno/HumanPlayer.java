package Uno;

import java.util.Scanner;

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
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + hand.get(i));
        }
            try {
                cardIndex = Integer.parseInt(indexStr);
                if (cardIndex < 1 && cardIndex > hand.size()) {
                    System.out.println("The number should be between 1 and " + hand.size());
                    return null;
                } else {
                    System.out.println("Card played: " + hand.get(cardIndex - 1));
                    hand.remove(cardIndex - 1);

                }
            } catch (Exception e) {
                System.out.println("Please choose a valid number for the card");
                return null;
            }
        return hand.get(cardIndex - 1);
    }

    public String inputAction(){
        Scanner input = new Scanner(System.in);
        System.out.println(" Please choose the number of the card to discard!");
        String indexStr = input.nextLine();

        return indexStr;
    }

}


