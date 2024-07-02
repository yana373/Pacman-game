package util;

import event.DotEvent;
import event.Event;
import event.SuperDotEvent;
import logic.Direction;
import logic.GhostController;
import logic.PacmanController;
import model.Cell;
import model.Point;

import java.awt.*;
import java.util.Arrays;

public class FieldPrinter extends Component{
    private int borderPxls;
    private int widthPxls;
    private int heightPxls;
    private PacmanController pacmanController;
    private GhostController[] ghostControllers;
    private int cellSizePxls;
    public FieldPrinter(PacmanController pacmanController, GhostController[] ghostControllers,
                        int widthPxls, int heightPxls, int borderPxls, int cellSizePxls) {
        this.pacmanController = pacmanController;
        this.ghostControllers = ghostControllers;
        this.widthPxls = widthPxls;
        this.heightPxls = heightPxls;
        this.borderPxls = borderPxls;
        this.cellSizePxls = cellSizePxls;
    }
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, widthPxls, heightPxls - borderPxls);

        for (int i = 0; i < pacmanController.getField().getHeight(); i++) {
            for (int j = 0; j < pacmanController.getField().getWidth(); j++) {
                // Рисование события
                drawEvent(g, j, i);
            }
        }
        // Рисование пакмана
        model.Point pacmanPoint = pacmanController.getCurPoint();
        drawPacman(g, pacmanPoint.getX(), pacmanPoint.getY());
        // Рисование призраков
        for (int k = 0; k < ghostControllers.length; k++) {
            Point ghostPoint = ghostControllers[k].getCurPoint();
            drawGhost(g, k, ghostPoint.getX(), ghostPoint.getY());
        }
        // Рисование событий и стен
        for (int i = 0; i < pacmanController.getField().getHeight(); i++) {
            for (int j = 0; j < pacmanController.getField().getWidth(); j++) {
                // Риcование стены
                Cell cell = pacmanController.getField().getCell(j, i);
                if (cell != null && !cell.isPassable()) {
                    drawWall(g, j, i);
                }
            }
        }
        // Рисование сердец
        drawHearts(g);
        // Рисование границ поля
        g.setColor(Color.MAGENTA);
        g.drawRect(borderPxls, 0, widthPxls - 2 * borderPxls, heightPxls - 2 * borderPxls);
    }
    public void drawWall(Graphics g, int x, int y) {
        g.setColor(Color.MAGENTA);
        g.drawRect(cellSizePxls * x + borderPxls, cellSizePxls * y, cellSizePxls, cellSizePxls);
    }
    public void drawGhost(Graphics g, int ghostNumber, int x, int y) {
        // рисуем тело привидения
        Color ghostColor = ghostControllers[ghostNumber].getGhost().getCurColor();
        g.setColor(ghostColor);
        g.fillRect(cellSizePxls * x + borderPxls, cellSizePxls * y, cellSizePxls, cellSizePxls);

        // рисуем глаза привидения
        g.setColor(Color.WHITE);
        int leftEyeX = cellSizePxls * x + borderPxls + cellSizePxls / 8;
        int leftEyeY = cellSizePxls * y + cellSizePxls / 4;
        int rightEyeX = cellSizePxls * x + borderPxls + 5 * cellSizePxls / 8;
        int rightEyeY = cellSizePxls * y + cellSizePxls / 4;
        g.fillRect(leftEyeX, leftEyeY, cellSizePxls / 4, cellSizePxls / 4);
        g.fillRect(rightEyeX, rightEyeY, cellSizePxls / 4, cellSizePxls / 4);
        drawGhostIris(g, leftEyeX, leftEyeY, rightEyeX, rightEyeY, ghostNumber);
    }
    public void drawGhostIris(Graphics g, int leftEyeX, int leftEyeY, int rightEyeX, int rightEyeY, int ghostNumber){
        switch (ghostControllers[ghostNumber].getGhostDirection()){
            case RIGHT:
                g.setColor(Color.BLUE);
                g.fillRect(leftEyeX + 2 * cellSizePxls / 16, leftEyeY + cellSizePxls / 16, cellSizePxls / 8, cellSizePxls / 8);
                g.fillRect(rightEyeX + 2 * cellSizePxls / 16, rightEyeY + cellSizePxls / 16, cellSizePxls / 8, cellSizePxls / 8);
                break;
            case LEFT:
                g.setColor(Color.BLUE);
                g.fillRect(leftEyeX, leftEyeY + cellSizePxls / 16, cellSizePxls / 8, cellSizePxls / 8);
                g.fillRect(rightEyeX, rightEyeY + cellSizePxls / 16, cellSizePxls / 8, cellSizePxls / 8);
                break;
            case UP:
                g.setColor(Color.BLUE);
                g.fillRect(leftEyeX + cellSizePxls / 16, leftEyeY, cellSizePxls / 8, cellSizePxls / 8);
                g.fillRect(rightEyeX + cellSizePxls / 16, rightEyeY, cellSizePxls / 8, cellSizePxls / 8);
                break;
            case DOWN:
                g.setColor(Color.BLUE);
                g.fillRect(leftEyeX + cellSizePxls / 16, leftEyeY + 2 * cellSizePxls / 16, cellSizePxls / 8, cellSizePxls / 8);
                g.fillRect(rightEyeX + cellSizePxls / 16, rightEyeY + 2 * cellSizePxls / 16, cellSizePxls / 8, cellSizePxls / 8);
                break;
        }
    }
    public void drawPacman(Graphics g, int x, int y){
        g.setColor(Color.YELLOW);
        g.fillOval(cellSizePxls * x + borderPxls, cellSizePxls * y, cellSizePxls, cellSizePxls);
        g.setColor(Color.BLACK);
        int[] arrayX = new int[3];
        int[] arrayY = new int[3];
        Arrays.fill(arrayX, cellSizePxls * x + borderPxls);
        Arrays.fill(arrayY, cellSizePxls * y);
        arrayX[2] += cellSizePxls / 2;
        arrayY[2] += cellSizePxls / 2;
        drawPacmanMouth(g, arrayX, arrayY);
    }
    public void drawPacmanMouth(Graphics g, int[] arrayX, int[] arrayY){
        switch (pacmanController.getPrevDirection()){
            case RIGHT:
                arrayX[0] += cellSizePxls;
                arrayX[1] += cellSizePxls;
                arrayY[1] += cellSizePxls;
                break;
            case LEFT:
                arrayY[1] += cellSizePxls;
                break;
            case DOWN:
                arrayX[1] += cellSizePxls;
                arrayY[0] += cellSizePxls;
                arrayY[1] += cellSizePxls;
                break;
            case UP:
                arrayX[1] += cellSizePxls;
                break;
        }
        Polygon triangle = new Polygon(arrayX, arrayY, 3);
        g.fillPolygon(triangle);
    }
    public void drawEvent(Graphics g, int x, int y){
        Cell cell = pacmanController.getField().getCell(x, y);
        if (cell == null) return;
        Event event = cell.getEvent();
        if (event == null) return;
        int dividor = 1;
        if (event instanceof DotEvent) {
            dividor = 4;
        }
        if (event instanceof SuperDotEvent) {
            dividor = 2;
        }
        int size = cellSizePxls / dividor;
        int xLeftUp = x * cellSizePxls + cellSizePxls / 2 - size / 2 + borderPxls;
        int yLeftUp = y * cellSizePxls + cellSizePxls / 2 - size / 2;
        g.setColor(Color.white);
        g.fillOval(xLeftUp, yLeftUp, size, size);
    }
    public void drawHearts(Graphics g) {
        g.setColor(Color.red);
        int heartsCount = pacmanController.getPacman().getLivesCount();
        int[] arrayX = new int[6];
        int[] arrayY = new int[6];
        Arrays.fill(arrayX, borderPxls);
        Arrays.fill(arrayY, heightPxls - 2 * borderPxls);
        arrayX[1] += cellSizePxls / 3;
        arrayX[2] += cellSizePxls / 2;
        arrayX[3] += cellSizePxls * 2 / 3;
        arrayX[4] += cellSizePxls;
        arrayX[5] += cellSizePxls / 2;

        arrayY[0] += cellSizePxls / 3;
        arrayY[2] += cellSizePxls / 3;
        arrayY[4] += cellSizePxls / 3;
        arrayY[5] += cellSizePxls;

        for (int i = 0; i < heartsCount; i++) {
            Polygon heart = new Polygon(arrayX, arrayY, 6);
            g.fillPolygon(heart);
            for (int j = 0; j < 6; j++) {
                arrayX[j] += cellSizePxls;
            }
        }
    }
}
