package pack1;


@SuppressWarnings("CanBeFinal")
public class Turtle extends FroggerItem {
    public static final int ONE_TURTLE = 0, TWO_TURTLE = 1, THREE_TURTLE = 2; // tipo
    public static final int ALWAYS_UP = 4, UP = 0, HALF_UP = 1, DOWN = 2, HALF_DOWN = 3; // modo
    private int mode, timer = 0, type, rate;


    public Turtle(double speed, int type, int direction, double x, double y) {
        super(speed, type, direction, x, y);
        // definir modo e taxa com base na dificuldade
        if (FroggerGame.difficulty == 0 || FroggerGame.difficulty == 1) {
            mode = ALWAYS_UP;
            rate = 0; // porque não importa, já que está sempre pra cima
        } else if (FroggerGame.difficulty == 2) {
            mode = (int) (Math.random() * 4);
            rate = 200;
        } else {
            mode = (int) (Math.random() * 4);
            rate = 100;
        }
        this.type = type;
    }

    /**
     * Retorna a largura com base no tamanho da plataforma da tartaruga.
     *
     * @return largura da imagem com base no tipo de tartaruga, ONE_TURTLE, etc
     */
    public int getWidth() {
        switch (type) {
            case ONE_TURTLE:
                return 40;
            case TWO_TURTLE:
                return 80;
            case THREE_TURTLE:
                return 120;
        }
        return 0;
    }

    void update() {
        super.update();
        timer++;
        if (mode != ALWAYS_UP) { // só mude se a tartaruga não estiver sempre levantada
            if (mode == DOWN && timer == rate) {
                mode = HALF_UP;
                timer = 0;
            }
            if (mode == HALF_UP && timer == rate) {
                mode = UP;
                timer = 0;
            }
            if (mode == UP && timer == rate) {
                mode = HALF_DOWN;
                timer = 0;
            }
            if (mode == HALF_DOWN && timer == rate) {
                mode = DOWN;
                timer = 0;
            }
        }
    }


    int getMode() {
        return mode;
    }
}
