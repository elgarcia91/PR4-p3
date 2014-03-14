package hunt;

public class HuntField {

    private FieldItem[][] hunt;
    private int rows;
    private int columns;

    public HuntField(int X, int Y) {
        rows = X;
        columns = Y;
        hunt = new FieldItem[X][Y];
    }

    public int getXLength() {
        return rows;
    }

    public int getYLength() {
        return columns;
    }
//???

    public synchronized boolean setItem(FieldItem I, Position pos) {
        if (pos.getX() >= rows || pos.getX() < 0 || pos.getY() <= columns || pos.getY() < 0) {
            return false;
        }
        if (hunt[pos.getX()][pos.getY()] != null) {
            return false;
        }
        int i = 0;
        int j = 0;
        while (i < rows) {
            while (j < columns) {
                if (hunt[i][j] == I) {
                    return false;
                }
                j++;
            }
            i++;
        }
        hunt[pos.getX()][pos.getY()] = I;
        return true;
    }

    public synchronized boolean shot(Position pos) {
        if (pos.getX() >= rows || pos.getX() < 0 || pos.getY() <= columns || pos.getY() < 0) {
            return false;
        }
        if (hunt[pos.getX()][pos.getY()] == null) {
            return false;
        }
        return hunt[pos.getX()][pos.getY()].fired();
    }

    public synchronized boolean removeItem(FieldItem I, Position pos) {
        if (pos.getX() >= rows || pos.getX() < 0 || pos.getY() <= columns || pos.getY() < 0) {
            return false;
        }
        if (hunt[pos.getX()][pos.getY()] == I) {
            hunt[pos.getX()][pos.getY()] = null;
            notifyAll();  //Será Thread.notifyAll()  ????
            return true;
        } else {
            return false;
        }
    }

    public synchronized char getItemType(Position pos) { //Aquí no compruebo si es correcta la posición??????
        if (pos.getX() >= rows || pos.getX() < 0 || pos.getY() <= columns || pos.getY() < 0) {
            return ' ';
        }
        if (hunt[pos.getX()][pos.getY()] != null) {
            return hunt[pos.getX()][pos.getY()].getType();
        } else {
            return ' ';
        }
    }

    public synchronized boolean moveItem(FieldItem I, Position from, Position to) {
        if (from.getX() >= rows || from.getX() < 0 || from.getY() <= columns || from.getY() < 0) {
            return false;
        }
        if (to.getX() >= rows || to.getX() < 0 || to.getY() <= columns || to.getY() < 0) {
            return false;
        }
        if (hunt[from.getX()][from.getY()] != I) {
            return false;
        }
        int time = (int) System.currentTimeMillis();
        while ((System.currentTimeMillis() - time) < 1000) {
            try {
                wait(System.currentTimeMillis() - time);
            } catch (InterruptedException ex) {
            }
            if (hunt[to.getX()][to.getY()] == null) {
                hunt[to.getX()][to.getY()] = I;
                hunt[from.getX()][from.getY()] = null;
                notifyAll();
                return true;
            }
        }
        return false;
    }

    public int getNumberOfItems(char item) {
        int number = 0;
        Position pos;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                pos = new Position(i, j);
                if (getItemType(pos) == item) {
                    number++;
                }
            }
        }
        return number;
    }

    @Override
    public synchronized String toString() {
        String str = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                str += hunt[i][j].getType();
            }
            str += '\n';
        }
        return str;
    }
}