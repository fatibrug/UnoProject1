package Uno;

public class Card {
    protected int number;
    protected Enum color= Color.BLACK;
    protected  int point;

// the card with number 0 to 9: point=number.
// The retour card: number=10, point=20.
// The suspend card:number= 11, point=20.
// The pull 2 card: number=12, point=20.
// The draw 4 color selection card : number=13, point=50.
//  The color selection card: number=14, point=50.


    public Card(int number, Enum color) {
        this.number = number;
        this.color = color;
        if(number>=0 && number<10){
            point= number;
        }else if(number>=10 && number<13){
            point=20;
        }else if(number==13|| number==14){
            point=50;
        }else System.out.println("invalid number, please select a new number!");
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", color=" + color +
                '}';
    }
}
