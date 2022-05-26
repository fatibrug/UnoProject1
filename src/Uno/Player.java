package Uno;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {
    protected String name;
    protected ArrayList<Card> hand= new ArrayList<>();
    protected int points=0;

    public Player(){
    }

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    public int cardValueinHand(){
        int cardValue=0;
        for(Card c: hand){
            cardValue+=c.value;
        }
        return cardValue;
    }

    public void gainPoints(int gainedPoints){
        points += gainedPoints;
    }


    public abstract boolean unoDeclare();//if the player declares uno or not
    public abstract String inputAction();
    public abstract Card play(String s);


}
