package pack1;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("WeakerAccess")
public class FroggerFrame extends JFrame {

    public FroggerFrame() {

        super("Frogger");

        // Define o bot�o Fechar para sair do programa
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // faz com que a janela n�o possa ser redimensionada
        setResizable(false);
        // Cria��o da Janela
        pack();
        // Cria��o do Painel
        FroggerPanel p = new FroggerPanel();
        // obt�m as inser��es das Frames
        Insets frameInsets = getInsets();
        // calcula o tamanho do painel
        int frameWidth = p.getWidth()
                + (frameInsets.left + frameInsets.right);
        int frameHeight = p.getHeight()
                + (frameInsets.top + frameInsets.bottom);
        // define o tamanho das Frames
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        // desativa as op��es de layout
        setLayout(null);
        // adiciona o painel ao frame
        add(p);
        // ajusta a janela para atender ao seu novo tamanho preferido
        pack();
        // mostra o frame
        setVisible(true);

    }


}