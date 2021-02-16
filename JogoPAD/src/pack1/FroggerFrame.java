package pack1;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("WeakerAccess")
public class FroggerFrame extends JFrame {

    public FroggerFrame() {

        super("Frogger");

        // Define o botão Fechar para sair do programa
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // faz com que a janela não possa ser redimensionada
        setResizable(false);
        // Criação da Janela
        pack();
        // Criação do Painel
        FroggerPanel p = new FroggerPanel();
        // obtém as inserções das Frames
        Insets frameInsets = getInsets();
        // calcula o tamanho do painel
        int frameWidth = p.getWidth()
                + (frameInsets.left + frameInsets.right);
        int frameHeight = p.getHeight()
                + (frameInsets.top + frameInsets.bottom);
        // define o tamanho das Frames
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        // desativa as opções de layout
        setLayout(null);
        // adiciona o painel ao frame
        add(p);
        // ajusta a janela para atender ao seu novo tamanho preferido
        pack();
        // mostra o frame
        setVisible(true);

    }


}