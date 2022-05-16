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
    public Card play() {
        Scanner input = new Scanner(System.in);
        for(int i=0; i<hand.size(); i++){
            System.out.println("Card "+(i+1)+": "+hand.get(i));
        }
        int cardIndex=input.nextInt();
        System.out.println("Card played: "+hand.get(cardIndex-1));
        hand.remove(cardIndex-1);
        return hand.get(cardIndex-1);
    }
}
