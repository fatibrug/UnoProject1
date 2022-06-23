package Uno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmartBot extends Player{
    private boolean hasDrawn = false;

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

    @Override
    public String chooseColor(){
        HashMap<String, Integer> colorCount = new HashMap<>();
        String returnColorCode = "";
        String cardColor = "";
        for(Card c: hand){
            cardColor = c.color.name();
            if(cardColor.equals("RED")){
                if(colorCount.containsKey("R")){
                    colorCount.put("R", colorCount.get("R") + 1);
                } else colorCount.put("R", 1);
            }
            else if(cardColor.equals("GREEN")){
                if(colorCount.containsKey("G")){
                    colorCount.put("G", colorCount.get("G") + 1);
                } else colorCount.put("G", 1);
            }
            else if(cardColor.equals("YELLOW")){
                if(colorCount.containsKey("Y")){
                    colorCount.put("Y", colorCount.get("Y") + 1);
                } else colorCount.put("Y", 1);
            }
            else if(cardColor.equals("BLUE")){
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
        return returnColorCode;
    }

    @Override
    public Card play(String indexStr) {
//        int cardIndex = 0;
        int cardIndex = Integer.parseInt(indexStr);
        System.out.println("*************************************************************");
        System.out.println("Card played: " + hand.get(cardIndex));
        return hand.remove(cardIndex);
    }

    @Override
    public void resetBotHasDrawn() {
        hasDrawn = false;
    }

    @Override
    public String inputAction(Card topCard, Color currentColor) {
        showHand();
        ArrayList<Card> validCards = new ArrayList<>();
        for (Card c : hand) {
            if (c.number == topCard.number || c.color.name().equals(currentColor.name())|| c.number == 13 ||c.number ==14) {
                validCards.add(c);
            }
        }
        if (validCards.isEmpty()) {
            if (!hasDrawn) {
                hasDrawn = true;
                return "draw";
            } else{
                hasDrawn = false;
                return "skip";
            }

        } else if (validCards.size() == 1 && (validCards.get(0).number == 14 ||validCards.get(0).number == 13)) {
            int indexOf14Or13 = 0;
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).number == 14 || hand.get(i).number == 13) {
                    indexOf14Or13 = i;
                }
            }
            return String.valueOf(indexOf14Or13);
        } else {
            Card bestCardToPlay = new Card(0, Color.BLACK);
            for (Card c : validCards) {
                if (c.number != 14) {
                    if (c.number >= bestCardToPlay.number) {
                        bestCardToPlay = c;
                    }
                }
            }
            int indexOfBestCard = 0;
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).number == bestCardToPlay.number && hand.get(i).color == bestCardToPlay.color) {
                    indexOfBestCard = i;
                }
            }
            return String.valueOf(indexOfBestCard);
        }

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

    @Override
    public String decisionToChallenge() {
        String[] challengeDecision = new String[]{"Y", "N"};
        String challengeChoice = challengeDecision[(int) Math.floor(Math.random() * 2)];
        System.out.println(challengeChoice);
        return challengeChoice;
    }
}
