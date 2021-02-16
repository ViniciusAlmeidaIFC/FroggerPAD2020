package pack1;

import java.awt.*;

public class Log extends FroggerItem {
    public static final int SHORT = 0, MEDIUM = 1, LONG = 2;

    public Log(double speed, int type, int direction, double x, double y) {
        super(speed, type, direction, x, y);
    }

    @Override
    public void updateRectangle() {
        this.setRect(new Rectangle((int) x + 30, (int) y, getWidth() - 30, 40));
    }

    /**
     * Obtém a largura do log.
     *
     * @return int com base no comprimento do log.
     */
    public int getWidth() {

        if (type == SHORT)
            return 80;
        if (type == MEDIUM)
            return 120;
        if (type == LONG)
            return 200;
        return 0;
    }
}
