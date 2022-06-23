package Uno;

public class Bot extends Player{

    public Bot(String name) {
        super(name);

    }

    @Override
    public String toString() {
        return "Bot " + name;
    }

    @Override
    public void play() {

    }
}
