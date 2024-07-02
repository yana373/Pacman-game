package util;

import event.DotEvent;
import event.Event;
import event.SuperDotEvent;
import logic.GhostController;
import logic.PacmanController;
import model.Cell;
import model.Field;
import model.Point;

import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

public class FirstLevel implements FieldGenerator{
    public static final int WIDTH = 17;
    public static final int HEIGHT = 21;
    public static final int DOTS_COUNT = 150;

    // Генерирует поле
    @Override
    public Field generate() {
        Point startPacmanPoint = new Point(8, 16);
        Point startGhostsPoint = new Point(8, 9);
        return new Field(startPacmanPoint, startGhostsPoint, WIDTH, HEIGHT);
    }

    // Наполняет поле
    @Override
    public void fill(Field field, PacmanController pacmanController, Timer timer,
                     GhostController[] ghostControllers){
        Cell wall = new Cell(false, null);
        Cell cell = new Cell();

        int[][] halfOfField = drawMaze(field, cell, wall);
        drawDots(field, pacmanController, halfOfField);

        // Расстановка супердотов
        field.getCell(0, 1).setEvent(new SuperDotEvent(pacmanController, timer,
                ghostControllers));
        field.getCell(16 , 1).setEvent(new SuperDotEvent(pacmanController, timer,
                ghostControllers));
        field.getCell(0, 16).setEvent(new SuperDotEvent(pacmanController, timer,
                ghostControllers));
        field.getCell(16 , 16).setEvent(new SuperDotEvent(pacmanController, timer,
                ghostControllers));
    }
    public int[][] drawMaze(Field field, Cell cell, Cell wall){
        // 0 - обычная клетка, 1 - стена
        int[][] halfOfField = {
                {0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 1, 0, 1, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1},
                {1, 1, 1, 0, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 0, 1, 0, 0},
                {1, 1, 1, 0, 0, 0, 1, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 1, 0, 0},
                {1, 1, 1, 0, 1, 0, 1, 0, 0},
                {1, 1, 1, 0, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 1, 0, 1, 1, 1, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 0, 1, 1, 1},
                {0, 0, 0, 0, 1, 0, 0, 0, 1},
                {0, 1, 1, 1, 1, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH / 2 + 1; j++) {
                if (halfOfField[i][j] == 0){
                    mirrorCellDrawing(field, j, i, cell);
                }
                else mirrorCellDrawing(field, j, i, wall);
            }
        }

        return halfOfField;
    }
    public void drawDots(Field field, PacmanController pacmanController, int[][] halfOfField){
        DotEvent dotEvent = new DotEvent(pacmanController);
        for (int i = 5; i < HEIGHT - 7; i++) {
            for (int j = 4; j < WIDTH / 2 + 1; j++) {
                halfOfField[i][j] = 1;
            }
        }
        halfOfField[16][8] = 1;

        mirrorDotDrawing(field, 5, 16, dotEvent);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH / 2 + 1; j++) {
                if (halfOfField[i][j] == 0){
                    mirrorDotDrawing(field, j, i, dotEvent);
                }
            }
        }
    }
    @Override
    public int getDotsCount() {
        return DOTS_COUNT;
    }
    private void mirrorCellDrawing(Field field, int x, int y, Cell cell){
        field.setCell(cell, x, y);
        field.setCell(cell, WIDTH - 1 - x, y);
    }
    private void mirrorDotDrawing(Field field, int x, int y, Event event){
        field.getCell(x, y).setEvent(event);
        field.getCell(WIDTH - 1 - x, y).setEvent(event);
    }
}
