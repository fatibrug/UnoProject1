package Uno;

public class SmartBot extends Player{

    public SmartBot(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "SmartBot " + name;
    }

    @Override
    public Card play(String s) {
    return null;
    }

    @Override
    public String inputAction() {
        return null;
    }
}
