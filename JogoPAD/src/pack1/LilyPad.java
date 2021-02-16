package pack1;


@SuppressWarnings("SameParameterValue")
public class LilyPad extends FroggerItem {

    private boolean frog;

    public LilyPad(double x, double y) {
        super(0, 0, 0, x, y);
    }

    public int getWidth() {
        return 40;
    }

    public boolean isFrog() {
        return frog;
    }

    public void setFrog(boolean frog) {
        this.frog = frog;
    }

}
