package Uno;

import java.util.ArrayList;

public class DiscardDeck extends Carddeck{
    protected ArrayList<Card> deck= new ArrayList<>();

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void getNewCard(Card c){
        deck.add(c);
    }

    public Card topCard(){
        return deck.get(deck.size()-1);
    }

    @Override
    public int getCount() {
        return deck.size();
    }

    @Override
    public Card drawACard() {
        return deck.remove(deck.size()-1);
    }

        @Override
    public void removeCard(Card c) {
        deck.remove(c);
    }
}
