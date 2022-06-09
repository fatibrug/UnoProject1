package Uno;

import java.util.Objects;

public class Card {
    protected int number;
    protected Enum color = Color.BLACK;
    protected int value;

/*
 the card with number 0 to 9: value=number.
 The retour card: number=10, value=20.
 The suspend card:number= 11, value=20.
 The pull 2 card: number=12, value=20.
 The color selection card: number=13, value=50.
 The draw 4 color selection card : number=14, value=50.
*/


    public Card(int number, Enum color) {
        this.number = number;
        this.color = color;
        if (number >= 0 && number < 10) {
            value = number;
        } else if (number >= 10 && number < 13) {
            value = 20;
        } else if (number == 13 || number == 14) {
            value = 50;
        } else System.out.println("invalid number, please select a new number!");
    }

    @Override
    public String toString() {
        String cardPrint = "";
        if (number >= 0 && number < 10) {
            if (color == Color.RED) {
                cardPrint = "R" + number;
            } else if (color == Color.BLUE) {
                cardPrint = "BL" + number;
            } else if (color == Color.GREEN) {
                cardPrint = "G" + number;
            } else if (color == Color.YELLOW) {
                cardPrint = "Y" + number;
            }
        } else if ((number == 10)) {
            cardPrint = "Retour " + color;
        } else if (number == 11) {
            cardPrint = "Suspend " + color;
        } else if (number == 12) {
            cardPrint = "Pull2 " + color;
        } else if (number == 14) {
            cardPrint = "Draw 4";
        } else if (number == 13) {
            cardPrint = "Color Selection";
        }
        return cardPrint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number && Objects.equals(color, card.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, color);
    }
}

