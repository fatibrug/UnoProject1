package Uno;

public class Bot extends Player{
    private int botlevel;

    public Bot(String name, int botlevel) {
        super(name);
        this.botlevel = botlevel;
    }

    @Override
    public String toString() {
        return name +  " level" + botlevel;
    }
}
