package pack1;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

@SuppressWarnings({"AccessStaticViaInstance", "WeakerAccess"})
public class FroggerPanel extends JPanel implements KeyListener, Runnable {

    private BufferedImage car1_Left, car1_Right, car2_Left, car2_Right, limo_Left, limo_Right, semi_Left, semi_Right, frogUp, frogDown,
            frogLeft, frogRight, hsTurtle, hmTurtle, hlTurtle, sTurtle, mTurtle, lTurtle, sLog, mLog, lLog, lilyPad, frogLife;
    private FroggerGame game;

    public FroggerPanel() {
        setSize(700, 640);

       
        reset();
        Thread pThread;

        try {
            pThread = new Thread(this);
            pThread.start();
           
        } catch (Exception e) {
            System.err.println("Erro ao criar a thread.");
            e.printStackTrace();
            System.exit(-2);
        }

        try {
            car1_Left = ImageIO.read((new File("resource/Car1-Left.png")));
            car1_Right = ImageIO.read((new File("resource/Car1-Right.png")));
            car2_Left = ImageIO.read((new File("resource/Car2-Left.png")));
            car2_Right = ImageIO.read((new File("resource/Car2-Right.png")));
            limo_Left = ImageIO.read((new File("resource/Limo-Left.png")));
            limo_Right = ImageIO.read((new File("resource/Limo-Right.png")));
            semi_Left = ImageIO.read((new File("resource/Semi-Left.png")));
            semi_Right = ImageIO.read((new File("resource/Semi-Right.png")));
            frogUp = ImageIO.read((new File("resource/FrogUp.png")));
            frogDown = ImageIO.read((new File("resource/FrogDown.png")));
            frogLeft = ImageIO.read((new File("resource/FrogLeft.png")));
            frogRight = ImageIO.read((new File("resource/FrogRight.png")));
            hsTurtle = ImageIO.read((new File("resource/HS-Turtle.png")));
            hmTurtle = ImageIO.read((new File("resource/HM-Turtle.png")));
            hlTurtle = ImageIO.read((new File("resource/HL-Turtle.png")));
            sTurtle = ImageIO.read((new File("resource/S-Turtle.png")));
            mTurtle = ImageIO.read((new File("resource/M-Turtle.png")));
            lTurtle = ImageIO.read((new File("resource/L-Turtle.png")));
            sLog = ImageIO.read((new File("resource/S-Log.png")));
            mLog = ImageIO.read((new File("resource/M-Log.png")));
            lLog = ImageIO.read((new File("resource/L-Log.png")));
            lilyPad = ImageIO.read((new File("resource/lilyPad.png")));
            frogLife = ImageIO.read((new File("resource/FrogLife.png")));

        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1); // se o carregamento falhar, encerre o programa.
        }
        addKeyListener(this);
    }

    @Deprecated
    public void keyReleased(KeyEvent e) {
        //Sem uso 
    }

    @Deprecated
    public void keyPressed(KeyEvent e) {
        //Sem uso
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            update();
            repaint();
            try {
                Thread.sleep(35);
            } catch (Exception e) {
                System.err.println("Error Sleeping.");
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        if (game.getStatus() == game.PLAYING) {

            Frog player = game.getPlayer();
            switch (e.getKeyChar()) {
                case 'w':
                    player.setY(player.getY() - 40);
                    if (player.getY() == 20) {
                        if (game.lilyCheck()) { //se pisou em um lilypad
                            player.setX(320);
                            player.setY(500);
                            FroggerGame.newLife = true;
                            game.setReachedMiddle(false); //definir o spaw de volta ao início.
                        } else {
                            player.setY(player.getY() + 40);
                        }
                    }

                    player.setDirection(Frog.UP);
                    break;
                case 's':
                    if ((player.getY() + 40) < getHeight() - 100)
                        player.setY(player.getY() + 40);
                    player.setDirection(Frog.DOWN);
                    break;
                case 'a':
                    player.setX(player.getX() - 20);
                    player.setDirection(Frog.LEFT);
                    break;
                case 'd':
                    player.setX(player.getX() + 20);
                    player.setDirection(Frog.RIGHT);
                    break;
            }
        } else { // morto ou ganhando
            if (e.getKeyChar() == 'n') {
                reset();
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(0, 0, getWidth(), getHeight()); // preencher o fundo
        g.setColor(Color.BLUE); //encher água na parte superior do mapa
        g.fillRect(0, 65, getWidth(), 190);
        // pequenas entradas de água para as lilypads ----------
        g.fillRect(60, 20, 70, 50);
        g.fillRect(240, 20, 70, 50);
        g.fillRect(420, 20, 70, 50);
        g.fillRect(600, 20, 70, 50);
        // linhas brancas da estrada -------------------
        g.setColor(Color.white);
        g.drawLine(0, 300, getWidth(), 300);
        g.drawLine(0, 500, getWidth(), 500);
        //estrada --------------------------------------
        g.setColor(Color.GRAY);
        g.fillRect(0, 301, getWidth(), 199);
        // barra preta inferior ----------------------
        g.setColor(Color.BLACK);
        g.fillRect(0, getHeight() - 100, getWidth(), getHeight());
        // linhas amarelas da estrada ----------------
        g.setColor(Color.yellow);
        for (int y = 341; y < 489; y += 39) {
            for (int x = 10; x < getWidth() - 10; x += 90) {
                g.fillRect(x, y, 60, 4);
            }
        }
        // lilypads ------------------------------
        byte itar = 0; // pequena variável para acompanhar o atual lilypad.
        for (int i = 75; i <= 615; i += 179) {
            if (game.getLilyPadses()[itar].isFrog()) {
                g.drawImage(lilyPad, i, 30, null);
                g.drawImage(frogDown, i, 30, null);
            } else
                g.drawImage(lilyPad, i, 30, null);
            itar++;
        }

        // texto ----------------------------------
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.CENTER_BASELINE, 40));
        g.drawString("Vidas:", 10, getHeight() - 15);
        g.drawString("   Tempo:", 300, getHeight() - 15);

        // tempo restante ----------------
        double timeLeft = game.getTimeLeft();
        if (timeLeft <= 0) // fora do tempo, então mate o jogador
            game.playerDeath();

        if (timeLeft >= 3 * (FroggerGame.MAX_LIFE_TIME / 4)) // mudar a cor da barra com base no tempo restante
            g.setColor(Color.green);
        else if (timeLeft >= (FroggerGame.MAX_LIFE_TIME / 2))
            g.setColor(Color.yellow);
        else if (timeLeft >= (FroggerGame.MAX_LIFE_TIME / 4))
            g.setColor(new Color(225, 0, 0));
        else
            g.setColor(new Color(125, 0, 0));

        g.fillRect(500, getHeight() - 40, (int) ((timeLeft / FroggerGame.MAX_LIFE_TIME) * 170), 20); // desenhar cronômetro com base no tempo restante
        g.drawRect(500, getHeight() - 40, 170, 20); // esboço do cronômetro

        // vidas ----------------------
        switch (game.getLives()) {
            case 3:
                g.drawImage(frogLife, 150, 600, null);
                g.drawImage(frogLife, 180, 600, null);
                g.drawImage(frogLife, 210, 600, null);
                break;
            case 2:
                g.drawImage(frogLife, 150, 600, null);
                g.drawImage(frogLife, 180, 600, null);
                break;
            case 1:
                g.drawImage(frogLife, 150, 600, null);
                break;
            default:
                //do nothing
        }

        // Movendo Objetos ---------------------------------------
        // Carros ------------
        for (CarLane cl : game.getCarLanes()) // todas as pistas de carros
        {
            for (int p = 0; p < cl.froggerItems.size(); p++) // cada carro naquela pista
            {
                if (cl.froggerItems.get(p).getDirection() == Lane.RIGHT && cl.froggerItems.get(p).getType() == Car.CAR_1) {
                    g.drawImage(car1_Right, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                } else if (cl.froggerItems.get(p).getDirection() == Lane.LEFT && cl.froggerItems.get(p).getType() == Car.CAR_1) {
                    g.drawImage(car1_Left, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                } else if (cl.froggerItems.get(p).getDirection() == Lane.RIGHT && cl.froggerItems.get(p).getType() == Car.CAR_2) {
                    g.drawImage(car2_Right, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                } else if (cl.froggerItems.get(p).getDirection() == Lane.LEFT && cl.froggerItems.get(p).getType() == Car.CAR_2) {
                    g.drawImage(car2_Left, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                } else if (cl.froggerItems.get(p).getDirection() == Lane.LEFT && cl.froggerItems.get(p).getType() == Car.LIMO) {
                    g.drawImage(limo_Left, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                } else if (cl.froggerItems.get(p).getDirection() == Lane.RIGHT && cl.froggerItems.get(p).getType() == Car.LIMO) {
                    g.drawImage(limo_Right, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                } else if (cl.froggerItems.get(p).getDirection() == Lane.LEFT && cl.froggerItems.get(p).getType() == Car.SEMI) {
                    g.drawImage(semi_Left, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                } else if (cl.froggerItems.get(p).getDirection() == Lane.RIGHT && cl.froggerItems.get(p).getType() == Car.SEMI) {
                    g.drawImage(semi_Right, (int) cl.froggerItems.get(p).getX(), (int) cl.froggerItems.get(p).getY(), null);
                }
            }
        }
        //logs ----------------------------------
        for (LogLane lL : game.getLogLanes()) // todas as pistas de log 
        {
            for (int p = 0; p < lL.froggerItems.size(); p++) // cada log naquela pista
            {
                if (lL.froggerItems.get(p).getType() == Log.SHORT) {
                    g.drawImage(sLog, (int) lL.froggerItems.get(p).getX(), (int) lL.froggerItems.get(p).getY(), null);
                } else if (lL.froggerItems.get(p).getType() == Log.MEDIUM) {
                    g.drawImage(mLog, (int) lL.froggerItems.get(p).getX(), (int) lL.froggerItems.get(p).getY(), null);
                } else if (lL.froggerItems.get(p).getType() == Log.LONG) {
                    g.drawImage(lLog, (int) lL.froggerItems.get(p).getX(), (int) lL.froggerItems.get(p).getY(), null);
                }
            }
        }
        //turtles -------------------------
        for (TurtleLane turtleLane : game.getTurtleLanes()) // todas as pistas de tartarugas
        {
            for (int p = 0; p < turtleLane.froggerItems.size(); p++) // cada tartaruga naquela pista
            {
                if (turtleLane.froggerItems.get(p).getType() == Turtle.ONE_TURTLE) {
                    Turtle temp = (Turtle) (turtleLane.froggerItems.get(p));
                    if (temp.getMode() == Turtle.UP || temp.getMode() == Turtle.ALWAYS_UP) {
                        g.drawImage(sTurtle, (int) turtleLane.froggerItems.get(p).getX(), (int) turtleLane.froggerItems.get(p).getY(), null);
                    } else if (temp.getMode() == Turtle.HALF_DOWN || temp.getMode() == Turtle.HALF_UP) {
                        g.drawImage(hsTurtle, (int) turtleLane.froggerItems.get(p).getX(), (int) turtleLane.froggerItems.get(p).getY(), null);
                    }
                } else if (turtleLane.froggerItems.get(p).getType() == Turtle.TWO_TURTLE) {
                    Turtle temp = (Turtle) (turtleLane.froggerItems.get(p));
                    if (temp.getMode() == Turtle.UP || temp.getMode() == Turtle.ALWAYS_UP) {
                        g.drawImage(mTurtle, (int) turtleLane.froggerItems.get(p).getX(), (int) turtleLane.froggerItems.get(p).getY(), null);
                    } else if (temp.getMode() == Turtle.HALF_DOWN || temp.getMode() == Turtle.HALF_UP) {
                        g.drawImage(hmTurtle, (int) turtleLane.froggerItems.get(p).getX(), (int) turtleLane.froggerItems.get(p).getY(), null);
                    }
                } else if (turtleLane.froggerItems.get(p).getType() == Turtle.THREE_TURTLE) {
                    Turtle temp = (Turtle) (turtleLane.froggerItems.get(p));
                    if (temp.getMode() == Turtle.UP || temp.getMode() == Turtle.ALWAYS_UP) {
                        g.drawImage(lTurtle, (int) turtleLane.froggerItems.get(p).getX(), (int) turtleLane.froggerItems.get(p).getY(), null);
                    } else if (temp.getMode() == Turtle.HALF_DOWN || temp.getMode() == Turtle.HALF_UP) {
                        g.drawImage(hlTurtle, (int) turtleLane.froggerItems.get(p).getX(), (int) turtleLane.froggerItems.get(p).getY(), null);
                    }
                }
            }
        }
        // desenhar sapo --------------------------
        switch (game.getPlayer().getDirection()) { // desenha o sapo baseado na direção
            case Frog.UP:
                g.drawImage(frogUp, (int) game.getPlayer().getX(), (int) game.getPlayer().getY(), null);
                break;
            case Frog.DOWN:
                g.drawImage(frogDown, (int) game.getPlayer().getX(), (int) game.getPlayer().getY(), null);
                break;
            case Frog.LEFT:
                g.drawImage(frogLeft, (int) game.getPlayer().getX(), (int) game.getPlayer().getY(), null);
                break;
            case Frog.RIGHT:
                g.drawImage(frogRight, (int) game.getPlayer().getX(), (int) game.getPlayer().getY(), null);
                break;
        }

        // desenhar o jogo sobre as telas --------------------------
        if (game.getStatus() == game.DEAD) {
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, 700, 640); // cobertura da tela
            g.setColor(Color.black);
            g.drawString("Game Over", 30, 300);
            g.drawString("Press n to restart.", 30, 340);
        } else if (game.getStatus() == game.PLAYER_WINS) {
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, 700, 640); // cobertura da tela
            g.setColor(Color.black);
            g.drawString("Parabéns! Você Ganhou!", 30, 300);
            g.drawString("Pressione n para jogar novamente!", 30, 340);
        }
    }

    private void update() {
        game.update();
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    private void reset() {
        int difficulty;
        do {
            String diff = JOptionPane.showInputDialog(null, "Escolha a dificuldade: 1- Fácil, 2- Medium e 3- Difícil. 0- Modo Deus!");
            try {
                difficulty = Integer.parseInt(diff);
                if (difficulty < 0 || difficulty > 3) {
                    JOptionPane.showMessageDialog(null, "Dificuldade Inválida, tente novamente!");
                } else
                    break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Eu não entendo o que você forneceu. Tente novamente.", "Difficulty error", JOptionPane.ERROR_MESSAGE);
            }
        } while (true);
        this.game = new FroggerGame(difficulty);
    }
}
