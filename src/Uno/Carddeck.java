package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Carddeck {
    protected ArrayList<Card> deck= new ArrayList<>();


    public void addCard(Card c){
        deck.add(c);
    }

    public void removeCard(Card c){
        deck.remove(c);
    }

    public Card drawACard(){
        return deck.remove(deck.size()-1);
    }

    public Card[] drawACard(int count){
        Card[] cardsToDraw = new Card[count];
        for(int i=0; i<count; i++){
            cardsToDraw[i]= drawACard();
        }
        return cardsToDraw;
    }

    public void generateDeck(){
       Enum[] e= new Enum[]{Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN};

       for(int i=0; i<4;i++){
           for(int j=0; j<2; j++){
               for(int k=1; k<13; k++){
                   Card c=new Card(k,e[i]);
                   deck.add(c);
               }

           }
       }
       for(int i=0; i<4; i++){
           Card c1= new Card(0,e[i]);
           Card c2= new Card(13,Color.BLACK);
           Card c3= new Card(14,Color.BLACK);
           deck.add(c1);
          deck.add(c2);
          deck.add(c3);
       }
       shuffle();
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }


    @Override
    public String toString() {
        return deck.size() +": Carddeck{" +
                "deck=" + deck +
                '}';
    }

    public int getCount() {
        return deck.size();
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public void deckSwap(ArrayList<Card> discardPile){
            for(int i=0; i<discardPile.size(); i++){
                deck.add(discardPile.remove(i));
            }
            Collections.shuffle(deck);
            discardPile.add(drawACard());
        }
}
