package pack1;


import java.util.ArrayList;

@SuppressWarnings({"SameParameterValue", "CanBeFinal", "WeakerAccess"})
public class FroggerGame {

    public static final int PLAYING = 0, DEAD = 1, PLAYER_WINS = 2;
    public static int MAX_LIFE_TIME = 50;
    public static boolean newLife = false;
    public static long startLifeTime = 0;
    public static int difficulty;
    private boolean reachedMiddle;
    private Frog player;
    private LogLane[] logLanes;
    private CarLane[] carLanes;
    private TurtleLane[] turtleLanes;
    private LilyPad[] lilyPadses;
    private int status, lives;

    public FroggerGame(int difficulty) {
        status = FroggerGame.PLAYING;
        reachedMiddle = false;
        lives = 3;
        //Dificuldade -----------
        this.difficulty = difficulty;
        if (difficulty == 0) difficulty = 1; // as coisas ainda se movem no modo de deus.
        switch (difficulty) {
            case 1:
                MAX_LIFE_TIME = 50;
                break;
            case 2:
                MAX_LIFE_TIME = 30;
                break;
            case 3:
                MAX_LIFE_TIME = 20;
        }
        //player -------
        player = new Frog(320, 500);
        //lilly pads------------------------
        lilyPadses = new LilyPad[4];
        lilyPadses[0] = new LilyPad(75, 30);
        lilyPadses[1] = new LilyPad(254, 30);
        lilyPadses[2] = new LilyPad(433, 30);
        lilyPadses[3] = new LilyPad(612, 30);
        //log e carros pistas -------------
        carLanes = new CarLane[5];
        logLanes = new LogLane[3];
        turtleLanes = new TurtleLane[2];

        carLanes[0] = new CarLane(difficulty * 2, Lane.RIGHT, 300); // a velocidade é baseada na dificuldade.
        carLanes[1] = new CarLane(difficulty * 1.5, Lane.LEFT, 340);
        carLanes[2] = new CarLane((double) difficulty * 0.7, Lane.RIGHT, 380);
        carLanes[3] = new CarLane(difficulty, Lane.LEFT, 420);
        carLanes[4] = new CarLane(difficulty * 2, Lane.RIGHT, 460);

        logLanes[0] = new LogLane(difficulty, Lane.RIGHT, 70);
        logLanes[1] = new LogLane(difficulty, Lane.RIGHT, 140);
        logLanes[2] = new LogLane(difficulty, Lane.LEFT, 180);

        //pistas tartarugas ---------------------
        turtleLanes[0] = new TurtleLane(difficulty * 1.3, Lane.LEFT, 100);
        turtleLanes[1] = new TurtleLane(difficulty * 1.3, Lane.RIGHT, 220);

        for (int t = 0; t < 1000; t++) // chama a atualização em todas as pistas antes de carregar o jogo
            update();
    }

    void update() {
        for (CarLane carLane : carLanes) carLane.update();
        for (LogLane logLane : logLanes) logLane.update();
        for (TurtleLane turtleLane : turtleLanes) turtleLane.update();
        // mova o sapo quando ele estiver em uma plataforma móvel
        if (player.getY() == 60 || player.getY() == 140) //se é uma pista da direita
            player.setX(player.getX() + difficulty); // mova o sapo na mesma velocidade que o log.
        else if (player.getY() == 180) // se é uma pista da esquerda
            player.setX(player.getX() - difficulty); //mova o sapo na mesma velocidade que o log.

        if (player.getY() == 100)  //pista da tartaruga ligada à esquerda
            player.setX(player.getX() - (difficulty * 1.3));
        else if (player.getY() == 220)
            player.setX(player.getX() + (difficulty * 1.3));

        //mantenha o jogador na tela o tempo todo
        if (player.getX() < 0)
            player.setX(0);
        if (player.getX() > 660)
            player.setX(660);

        //ganhe o jogo se todos os lilypads estiverem cheios
        if (lilyPadses[0].isFrog() && lilyPadses[1].isFrog() && lilyPadses[2].isFrog() && lilyPadses[3].isFrog())
            status = PLAYER_WINS;

        if (player.getY() == 260 && !reachedMiddle) { //se atingiu o meio do campo de jogo
            reachedMiddle = true;
        }

        runChecks();
    }

    public int getStatus() {
        return status;
    }

    public int getLives() {
        return lives;
    }

    public Frog getPlayer() {
        return player;
    }

    public LogLane[] getLogLanes() {
        return logLanes;
    }

    public CarLane[] getCarLanes() {
        return carLanes;
    }

    public LilyPad[] getLilyPadses() {
        return lilyPadses;
    }

    public TurtleLane[] getTurtleLanes() {
        return turtleLanes;
    }

    public long getTimeLeft() {
        long currentTime = System.nanoTime();
        int transversedTime = (int) ((currentTime - startLifeTime) / 1000000000);
        if (newLife) {
            newLife = false;
            startLifeTime = currentTime;
            return getTimeLeft();
        } else
            return MAX_LIFE_TIME - transversedTime;
    }

    /**
     * Pré-forma ações para "matar o jogador", como remover uma vida, renascer, etc.
     */
    public void playerDeath() {
        lives--;
        if (lives == 0) { //se é o Fim de jogo
            status = DEAD;
        }
        if (reachedMiddle) {
            player = new Frog(320, 260); //colocar o jogador de volta no meio
        } else {
            player = new Frog(320, 500); //colocar o jogador de volta no ponto de spawn
        }
    }

    /**
     * Verifica se um carro matou o jogador.
     */
    private void carCheck() {
        for (CarLane carLane : carLanes) {
            ArrayList<FroggerItem> fIOfCarLane = carLane.getFroggerItems();
            //noinspection Convert2streamapi
            for (FroggerItem aFIOfCarLane : fIOfCarLane) {
                if (aFIOfCarLane.getRect().intersects(player.getRect())) { // se o sapo tocar no carro
                    playerDeath();
                }
            }
        }
    }

    private void logCheck() {
        boolean dead = true;
        for (LogLane logLane : logLanes) {
            ArrayList<FroggerItem> fIOfLogLane = logLane.getFroggerItems();
            for (FroggerItem aFIOfLogLane : fIOfLogLane) {
                if (aFIOfLogLane.getRect().intersects(player.getRect())) { // se eles se cruzam, também conhecido como no log
                    dead = false;
                }
            }
        }
        if (dead) playerDeath(); // se ainda estiver marcado para morrer, mate o sapo.
    }

    private void turtleCheck() {
        boolean dead = true;
        for (TurtleLane turtleLane : turtleLanes) {
            ArrayList<FroggerItem> fIOfTurtleLane = turtleLane.getFroggerItems();
            for (FroggerItem aFIOfTurtleLane : fIOfTurtleLane) {
                Turtle temp = (Turtle) aFIOfTurtleLane;
                if (temp.getRect().intersects(player.getRect()) && temp.getMode() != Turtle.DOWN) { // se eles se cruzam, também conhecido como na tartaruga, e a tartaruga não está caída
                    dead = false;
                }
            }
        }
        if (dead) playerDeath(); // se ainda estiver marcado para morrer, mate o sapo.
    }

    public boolean lilyCheck() {
        double playerX = player.getX();
        if (playerX >= 60 && playerX <= 130) {
            lilyPadses[0].setFrog(true);
            return true;
        } else if (playerX >= 240 && playerX <= 310) {
            lilyPadses[1].setFrog(true);
            return true;
        } else if (playerX >= 420 && playerX <= 490) {
            lilyPadses[2].setFrog(true);
            return true;
        } else if (playerX >= 600 && playerX <= 670) {
            lilyPadses[3].setFrog(true);
            return true;
        } else
            return false;
    }

    public void setReachedMiddle(boolean reachedMiddle) {
        this.reachedMiddle = reachedMiddle;
    }

    private void runChecks() {
        if (difficulty == 0) { // não faça verificações se estiver no modo deus.
            return;
        }
        double y = player.getY();
        if (y <= 460 && y >= 300) { // nas pistas de carros
            carCheck();
        } else if (y == 220 || y == 100) { // nas pistas de tartarugas
            turtleCheck();
        } else if (y == 180 || y == 140 || y == 60) { //na pista dos logs.
            logCheck();
        }

    }

}
