package Uno;

public class Bot extends Player{
    private int level;

    public Bot(String name, int level) {
        super(name);
        this.level = level;
    }

    @Override
    public String toString() {
        return name +  " level" + level;
    }
}
