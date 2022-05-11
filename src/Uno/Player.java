package Uno;

import java.util.ArrayList;

public class Player {
    protected String name;
    protected ArrayList<Card> hand= new ArrayList<>();
    protected int points=0;

    public Player(){
    }

    public Player(String name) {
        this.name = name;
    }


}
