package Uno;

import java.util.HashMap;
import java.util.Map;

public class SmartBot extends Player{

    public SmartBot(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "SmartBot " + name;
    }


    @Override
    public void showHand() {
        System.out.println("Player " + name + " : Here are the cards in hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + hand.get(i));
        }
    }

    public String smartBotChoosesColor(){
        HashMap<String, Integer> colorCount = new HashMap<>();
        String returnColorCode = "";
        for(Card c: hand){
            String cardColor = c.color.name();
            if(cardColor == "RED"){
                if(colorCount.containsKey("RED")){
                    colorCount.put("R", colorCount.get("RED" + 1));
                } else colorCount.put("R", 1);
            }
            else if(cardColor == "GREEN"){
                if(colorCount.containsKey("GREEN")){
                    colorCount.put("G", colorCount.get("GREEN" + 1));
                } else colorCount.put("G", 1);
            }
            else if(cardColor == "YELLOW"){
                if(colorCount.containsKey("YELLOW")){
                    colorCount.put("Y", colorCount.get("YELLOW" + 1));
                } else colorCount.put("Y", 1);
            }
            else if(cardColor == "BLUE"){
                if(colorCount.containsKey("BLUE")){
                    colorCount.put("B", colorCount.get("BLUE" + 1));
                } else colorCount.put("B", 1);
            }
        }
        for(Map.Entry<String, Integer> entry: colorCount.entrySet()){
            int max= 0;
            if(entry.getValue()>max){
                max = entry.getValue();
                returnColorCode = entry.getKey();
            }
        }
        return returnColorCode;
    }

    @Override
    public Card play(String s) {
        int cardIndex = Integer.parseInt(s);
        System.out.println("Card played: " + hand.get(cardIndex-1));
        return hand.remove(cardIndex-1);
    }

    @Override
    public String inputAction() {
        return "0";
    }

    @Override
    public int cardValueinHand() {
        return super.cardValueinHand();
    }

    @Override
    public void gainPoints(int gainedPoints) {
        super.gainPoints(gainedPoints);
    }

    @Override
    public boolean unoDeclare() {
        return true;
    }
}
