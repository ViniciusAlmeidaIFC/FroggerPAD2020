package pack1;


public class Car extends FroggerItem {

    public static final int SEMI = 0, LIMO = 1, CAR_1 = 2, CAR_2 = 3;

    public Car(double speed, int type, int direction, double x, double y) {
        super(speed, type, direction, x, y);
    }

    /**
     * Obtém o comprimento com base no tipo de carro
     *
     * @return int do comprimento com base no tipo de carro.
     */
    public int getWidth() {
        if (type == CAR_1)
            return 40;
        if (type == CAR_2)
            return 40;
        if (type == SEMI)
            return 120;
        if (type == LIMO)
            return 80;
        return 0;
    }
}
