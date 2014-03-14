package hunt;

import java.util.Random;

public class Hunter extends Thread implements FieldItem {

    private final char type = 'H';
    private Random rnd;
    private int hunted;
    private Position position;
    private final HuntField field;
    private boolean alive;

    public Hunter(HuntField f) {
        field = f;
        rnd = new Random();
        alive = true;
        hunted = 0;
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
        return true; //¿Es acertado dispararle a otro cazador?
    }

    @Override
    public char getType() {
        return type;
    }

    @Override
    public void run() {
        /*
         * Espera 0,1 seg y dispara a la pos adyacente
         *  si aciertan el disparo, se mueven al pato y aumenta hunters 
         *  en 1. 
         * sino, vuelven a disparar a la siguiente dirección. 
         */
        int dir = rnd.nextInt(4); //{Norte, Este, Sur, Oeste}
        while (alive) {
            try {
                sleep(200);
            } catch (InterruptedException ex) {
            }
            Position pos = getNextPos(position, dir);
            if (field.shot(pos)) {
                hunted++;
                field.moveItem(this, position, pos);
            }
            dir = (dir + 1) % 4;
        }
    }

    public int hunted() {
        return hunted;
    }

    private Position getRandomPos(HuntField f) {
        int x = (int) (rnd.nextDouble() * f.getXLength());
        int y = (int) (rnd.nextDouble() * f.getYLength());
        Position pos = new Position(x, y);
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