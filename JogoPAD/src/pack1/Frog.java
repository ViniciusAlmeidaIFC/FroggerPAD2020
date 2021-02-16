package pack1;


@SuppressWarnings("SameParameterValue")
public class Frog extends FroggerItem {

    public static final int UP = 0, DOWN = 1;

    public Frog(double x, double y) {
        super(40, 0, UP, x, y);
        FroggerGame.startLifeTime = System.nanoTime();
        FroggerGame.newLife = true;
    }

    public int getWidth() {
        return 40;
    }

}
