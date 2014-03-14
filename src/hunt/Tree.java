package hunt;

import java.util.Random;

public class Tree implements FieldItem {

    private Position position;
    private Random rnd;
    private HuntField field;
    private final char type = 'T';

    Tree(HuntField f) {
        field = f;
        rnd = new Random(); 
        Position pos = getRandomPos(f);
        while(!f.setItem(this, pos)){
            pos = getRandomPos(f);
        }
        position = pos; 
    }

    @Override
    public boolean fired() {
        return false;
    }

    @Override
    public char getType() {
        return type;
    }
    
    private Position getRandomPos(HuntField f) {
        int x = (int) (rnd.nextDouble() * f.getXLength());
        int y = (int) (rnd.nextDouble() * f.getYLength());
        Position pos = new Position(x, y);
        return pos;
    }
}
