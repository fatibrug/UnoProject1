package Uno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        int i = (int) Math.floor(Math.random() * 2);

        System.out.println(i);
        HashMap<String, Integer> colorCount = new HashMap<>();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(10, Color.YELLOW));
        cards.add(new Card(10, Color.RED));
        cards.add(new Card(9, Color.YELLOW));
        cards.add( new Card(10, Color.BLUE));
        cards.add(new Card(10, Color.BLUE));
        cards.add(new Card(10, Color.GREEN));
        cards.add(new Card(10, Color.YELLOW));
        String returnColorCode = "";
        for(Card c: cards){
            String cardColor = c.color.name();
            if(cardColor == "RED"){
                if(colorCount.containsKey("R")){
                    colorCount.put("R", colorCount.get("R")+ 1);
                } else colorCount.put("R", 1);
            }
            else if(cardColor == "GREEN"){
                if(colorCount.containsKey("G")){
                    colorCount.put("G", colorCount.get("G") + 1);
                } else colorCount.put("G", 1);
            }
            else if(cardColor == "YELLOW"){
                if(colorCount.containsKey("Y")){
                    colorCount.put("Y", colorCount.get("Y") + 1);
                } else colorCount.put("Y", 1);
            }
            else if(cardColor == "BLUE"){
                if(colorCount.containsKey("B")){
                    colorCount.put("B", colorCount.get("B") + 1);
                } else colorCount.put("B", 1);
            }
        }
        int max= 0;
        for(Map.Entry<String, Integer> entry: colorCount.entrySet()){

            if(entry.getValue()>max){
                max = entry.getValue();
                returnColorCode = entry.getKey();
            }
        }
        System.out.println( returnColorCode);

    }


    }

