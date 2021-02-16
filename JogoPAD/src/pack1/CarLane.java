package pack1;


public class CarLane extends Lane {

    public CarLane(double speed, int direction, int y) {
        super(speed, direction, y);
    }

    void update() {
        super.update();
        int carType = ((int) (Math.random() * 4));
        int length;
        switch (carType) {
            case Car.SEMI: //if semi
                length = 120;
                break;
            case Car.CAR_2: //if limo
                length = 80;
                break;
            default: //else is tipo do carro
                length = 40;
                break;
        }
        if (direction == RIGHT) {
            int location = -120 - (int) (Math.random() * 49) - length;
            if (froggerItems.size() == 0) {
                froggerItems.add(new Car(speed, carType, RIGHT, location, y));
            }
            for (int i = 0; i < froggerItems.size(); i++) {
                if (froggerItems.get(i).getDirection() == Lane.LEFT && froggerItems.get(i).getX() < -20)
                    froggerItems.remove(i);
                if (froggerItems.get(i).getDirection() == Lane.RIGHT && froggerItems.get(i).getX() > 720)
                    froggerItems.remove(i);

            }
            if ((int) froggerItems.get(froggerItems.size() - 1).getX() + froggerItems.get(froggerItems.size() - 1).getWidth() > 0) {

                Car newCar = new Car(speed, carType, RIGHT, location, y);
                newCar.setX(location - newCar.getWidth());
                froggerItems.add(newCar);
            }
        } else if (direction == LEFT) {
            int location = 700 + (int) (Math.random() * 49) + length; // definir localização do carro para spawn
            if (froggerItems.size() == 0) {
                froggerItems.add(new Car(speed, carType, LEFT, location, y));
            }
            for (int i = 0; i < froggerItems.size(); i++) {
                if (froggerItems.get(i).getDirection() == Lane.RIGHT && froggerItems.get(i).getX() > 720)
                    froggerItems.remove(i);
            }
            if ((int) froggerItems.get(froggerItems.size() - 1).getX() + froggerItems.get(froggerItems.size() - 1).getWidth() < 700) {
                froggerItems.add(new Car(speed, carType, LEFT, location, y));
            }
        }
    }
}
