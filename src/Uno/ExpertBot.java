package Uno;

public class ExpertBot extends Player {

    public ExpertBot(String name) {
        super();
    }

    @Override
    public void showHand() {
        System.out.println("Player " + name + " : Here are the cards in hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + hand.get(i));
        }
    }

    @Override
    public void resetBotHasDrawn() {

    }

    @Override
    public String toString() {
        return "ExpertBot " + name;
    }

    @Override
    public Card play(String s) {
        return null;
    }

    @Override
    public String inputAction(Card topCard, Color currentColor) {
        return null;
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
        return "N";
    }

    @Override
    public String chooseColor() {
        return null;
    }
}
