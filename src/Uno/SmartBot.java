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
    public Card play() {
    return null;
    }
}
