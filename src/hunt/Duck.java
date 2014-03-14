package hunt;

import java.util.Random;

public class Duck extends Thread implements FieldItem {

    private Position position;
    private Random rnd;
    private boolean alive;
    private HuntField field;
    private final char type = 'D';

    Duck(HuntField f) {
        field = f;
        rnd = new Random();
        alive = true;
        Position pos = getRandomPos(f);
        while (!f.setItem(this, pos)) {
            pos = getRandomPos(f);
        }
        position = pos;
    }

    @Override
    public boolean fired() {
        field.removeItem(this, position);
        alive = false;
        return true;
    }

    @Override
    public char getType() {
        return type;
    }

    public void run() {
        while (alive) {
        }
    }

    private Position getRandomPos(HuntField f) {
        int x = (int) (rnd.nextDouble() * f.getXLength());
        int y = (int) (rnd.nextDouble() * f.getYLength());
        Position pos = new Position(x, y);
        return pos;
    }
}