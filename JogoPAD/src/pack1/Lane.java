package pack1;


import java.util.ArrayList;

@SuppressWarnings({"CanBeFinal", "WeakerAccess"})
public class Lane {

    public static final int LEFT = 0, RIGHT = 1;
    int direction;
    double speed, y;
    ArrayList<FroggerItem> froggerItems = new ArrayList<>();

    public Lane(double speed, int direction, int y) {
        this.speed = speed;
        this.direction = direction;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public int getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public ArrayList<FroggerItem> getFroggerItems() {
        return froggerItems;
    }

    void update() {
        //noinspection Convert2streamapi
        for (FroggerItem froggerItem : froggerItems) {
            froggerItem.update();
        }
    }
}
