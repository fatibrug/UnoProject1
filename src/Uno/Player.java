package Uno;

import java.util.ArrayList;

public class Player {
    protected String name;
    protected static int playerNr=0;
    protected ArrayList<Card> hand= new ArrayList<>();
    protected int points;

    public Player(String name) {
        this.name = name;
        playerNr=playerNr++;
    }


}
