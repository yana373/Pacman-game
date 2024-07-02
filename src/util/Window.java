package util;

import event.DotEvent;
import event.Event;
import event.SuperDotEvent;
import logic.Controller;
import logic.GhostController;
import logic.PacmanController;
import model.Cell;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Window extends JFrame {
    public static final int CELL_SIZE_PXLS = 32;
    private int widthPxls;
    private int heightPxls;
    private int borderPxls;
    private JLabel label;
    private PacmanController pacmanController;
    private FieldPrinter fieldPrinter;
    public Window(PacmanController pacmanController, GhostController[] ghostControllers) {
        super();

        this.pacmanController = pacmanController;

        borderPxls = CELL_SIZE_PXLS;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();
        widthPxls = pacmanController.getField().getWidth() * CELL_SIZE_PXLS + 2 * borderPxls;
        heightPxls = pacmanController.getField().getHeight() * CELL_SIZE_PXLS + 2 * borderPxls;
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(widthPxls, heightPxls));
        pack();
        int realWidth = getContentPane().getWidth();
        int realHeight = getContentPane().getHeight();
        int widthDifference = widthPxls - realWidth; // размер меню
        int heightDifference = heightPxls - realHeight;
        int frameWidth = widthPxls + widthDifference; // размер картинки + меню
        int frameHeight = heightPxls + heightDifference;
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        pack();

        setLocation(d.width / 2 - frameWidth / 2, d.height / 2 - frameHeight / 2);

        Container container = getContentPane();

        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.BOTTOM);
        label.setPreferredSize(new Dimension(widthPxls, borderPxls));
        Font font = new Font("Verdana", Font.BOLD, 20);
        label.setFont(font);
        label.setForeground(Color.white);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        container.add(label, BorderLayout.PAGE_START);

        fieldPrinter = new FieldPrinter(pacmanController, ghostControllers, widthPxls, heightPxls,
                borderPxls, CELL_SIZE_PXLS);
        container.add(fieldPrinter);

        addKeyListener(new KeyboardListener(pacmanController));
    }

    public void repaint() {
        int score = pacmanController.getGameTracker().getScore();
        fieldPrinter.repaint();
        label.setText(Integer.toString(score));
    }
}