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
        boolean set = false; 
        while (!set) {
            pos = getRandomPos(f);
            set = f.setItem(this, pos);
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

    @Override
    public void run() {
        int dir;
        Position pos;
        while (alive) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) { }
            dir = rnd.nextInt(4); //{Norte, Este, Sur, Oeste}
            pos = getNextPos(position, dir);
            if (field.moveItem(this, position, pos)) {
                position = pos;
            }
        }
    }

    private Position getRandomPos(HuntField f) {
        int x = (int) (rnd.nextDouble() * f.getXLength());
        int y = (int) (rnd.nextDouble() * f.getYLength());
        Position pos = new Position(2, 2);
        return pos;
    }

    private Position getNextPos(Position pos, int dir) {
        Position res = pos;
        //{Norte, Este, Sur, Oeste}
        switch (dir) {
            case 0:
                res = new Position(pos.getX() - 1, pos.getY());
                break;
            case 1:
                res = new Position(pos.getX(), pos.getY() + 1);
                break;
            case 2:
                res = new Position(pos.getX() + 1, pos.getY());
                break;
            case 3:
                res = new Position(pos.getX(), pos.getY() - 1);
                break;
        }
        return res;
    }
}