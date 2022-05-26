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
    public Card play(String s) {
        Card discardedCard = hand.get(0);
        System.out.println("Card played: "+discardedCard);
        hand.remove(0);
        return discardedCard;
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
    public String inputAction() {
        return "0";
    }

    @Override
    public boolean unoDeclare() {
        return false;
    }
}
