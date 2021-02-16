package pack1;

public class TurtleLane extends Lane {
    public TurtleLane(double speed, int direction, int y) {
        super(speed, direction, y);
    }

    void update() {
        super.update();
        int carType = (int) (Math.random() * 3);
        int length = 0;
        switch (carType) {
            case Turtle.ONE_TURTLE:
                length = 40;
                break;
            case Turtle.TWO_TURTLE:
                length = 80;
                break;
            case Turtle.THREE_TURTLE:
                length = 120;
                break;
        }
        if (direction == RIGHT) {
            int location = -120 - (int) (Math.random() * 49) - length;
            if (froggerItems.size() == 0) {
                froggerItems.add(new Turtle(speed, (int) (Math.random() * 4), RIGHT, location, y));
            }
            for (int i = 0; i < froggerItems.size(); i++) {
                if (froggerItems.get(i).getDirection() == Lane.LEFT && froggerItems.get(i).getX() < -20)
                    froggerItems.remove(i);
                if (froggerItems.get(i).getDirection() == Lane.RIGHT && froggerItems.get(i).getX() > 720)
                    froggerItems.remove(i);

            }
            if ((int) froggerItems.get(froggerItems.size() - 1).getX() + froggerItems.get(froggerItems.size() - 1).getWidth() > 0) {

                Turtle newTurtle = new Turtle(speed, carType, RIGHT, location, y);
                newTurtle.setX(location - newTurtle.getWidth());
                froggerItems.add(newTurtle);
            }
        } else if (direction == LEFT) {
            int location = 700 + (int) (Math.random() * 49) + length; // definir localização do carro para desovar
            if (froggerItems.size() == 0) {
                froggerItems.add(new Turtle(speed, (int) (Math.random() * 4), LEFT, location, y));
            }
            for (int i = 0; i < froggerItems.size(); i++) {
                if (froggerItems.get(i).getDirection() == Lane.RIGHT && froggerItems.get(i).getX() > 720)
                    froggerItems.remove(i);
            }
            if ((int) froggerItems.get(froggerItems.size() - 1).getX() + froggerItems.get(froggerItems.size() - 1).getWidth() < 700) {
                froggerItems.add(new Turtle(speed, carType, LEFT, location, y));
            }
        }
    }
}
