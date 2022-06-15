package Uno;

import java.util.Scanner;

public class Bot extends Player{

    public Bot(String name) {
        super(name);

    }

    @Override
    public String toString() {
        return "Bot " + name;
    }

    @Override
    public void showHand() {
        System.out.println("Player " + name + " : Here are the cards in hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + hand.get(i));
        }
    }

    @Override
    public Card play(String s) {
        int cardIndex = Integer.parseInt(s);
        System.out.println("*************************************************************");
        System.out.println("Card played: " + hand.get(cardIndex-1));
        return hand.remove(cardIndex-1);
    }

    @Override
    public void resetBotHasDrawn() {

    }

    @Override
    public int cardValueinHand() {
        return super.cardValueinHand();
    }

    @Override
    public void gainPoints(int gainedPoints) {
        super.gainPoints(gainedPoints);
    }

    @Override
    public String inputAction(Card topCard, Color currentColor) {return "1";}

    @Override
    public boolean unoDeclare() {
        boolean[] unoDeclareDecision = new boolean[]{true, false};
        return unoDeclareDecision[(int)Math.floor(Math.random()*2)];
    }
}
