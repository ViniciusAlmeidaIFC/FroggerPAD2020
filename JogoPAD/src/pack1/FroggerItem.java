package pack1;


import java.awt.*;

@SuppressWarnings({"CanBeFinal", "WeakerAccess"})
public abstract class FroggerItem {

    public static final int LEFT = 2, RIGHT = 3;
    double speed, x, y;
    int direction, type;
    private Rectangle rect = new Rectangle();

    public FroggerItem(double speed, int type, int direction, double x, double y) {
        this.speed = speed;
        this.type = type;
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        updateRectangle();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        updateRectangle();
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public void updateRectangle() {
        this.rect = new Rectangle((int) x, (int) y, getWidth(), 40);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public abstract int getWidth();

    void update() {
        if (direction == Lane.RIGHT)
            setX(x + speed);
        else if (direction == Lane.LEFT)
            setX(x - speed);
        updateRectangle();
    }
}
