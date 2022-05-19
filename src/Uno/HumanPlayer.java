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
    public Card play(String indexStr) {
        int cardIndex = 0;
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + hand.get(i));
        }
        cardIndex = Integer.parseInt(indexStr);
            System.out.println("Card played: " + hand.get(cardIndex - 1));
            hand.remove(cardIndex - 1);
            return hand.get(cardIndex - 1);
    }


    public String inputAction(){
        Scanner input = new Scanner(System.in);
        String action = input.nextLine();
        boolean inputError = false;
       do {
            System.out.println(" Please choose the number of the card to discard,The number should be between 1 and "+ hand.size());
            System.out.println("If you have no valid card to play, please enter 'draw' to draw a card");
            System.out.println("If you need help, please enter 'help' for help");

            if(action.compareToIgnoreCase("draw")==0){
                action = "draw";
            }
            else if(action.compareToIgnoreCase("help")==0){
                action = "help";
            } else if (action.chars().allMatch( Character::isDigit ) && Integer.parseInt(action)>0 && Integer.parseInt(action)<=hand.size()){
                String indexStr = action;
                return indexStr;
            }else  {
                System.out.println("Please enter a valid choice of action!");
                inputError = true;
            }
       } while(inputError);
        return action;
    }

}


