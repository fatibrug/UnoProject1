package Uno;

import java.util.ArrayList;

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


    public abstract Card play();


}
