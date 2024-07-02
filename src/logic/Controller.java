package logic;

import model.Cell;
import model.Field;
import model.Point;

public class Controller {
    private Field field;
    public Controller(Field field){
        this.field = field;
    }

    // Отслеживает передвижение персонажей. True - было передвижение в другую клетку, False - не было
    protected boolean move(Direction direction, Point curPoint){
        boolean isDirectionChanged = false;

        switch (direction) {
            case RIGHT:
                isDirectionChanged = moveToRight(curPoint);
                break;
            case LEFT:
                isDirectionChanged = moveToLeft(curPoint);
                break;
            case DOWN:
                isDirectionChanged = moveToDown(curPoint);
                break;
            case UP:
                isDirectionChanged = moveToUp(curPoint);
                break;
        }

        return isDirectionChanged;
    }
    private boolean moveToRight(Point curPoint) {
        boolean isDirectionChanged = false;

        int futureX = curPoint.getX() + 1;
        int futureY = curPoint.getY();
        Cell futureCell = field.getCell(futureX, futureY);
        if (futureCell != null && futureCell.isPassable()) {
            curPoint.setX(futureX);
            isDirectionChanged = true;
        }

        return isDirectionChanged;
    }
    private boolean moveToLeft(Point curPoint) {
        boolean isDirectionChanged = false;

        int futureX = curPoint.getX() - 1;
        int futureY = curPoint.getY();
        Cell futureCell = field.getCell(futureX, futureY);
        if (futureCell != null && futureCell.isPassable()) {
            curPoint.setX(futureX);
            isDirectionChanged = true;
        }

        return isDirectionChanged;
    }
    private boolean moveToDown(Point curPoint) {
        boolean isDirectionChanged = false;

        int futureX = curPoint.getX();
        int futureY = curPoint.getY() + 1;
        Cell futureCell = field.getCell(futureX, futureY);
        if (futureCell != null && futureCell.isPassable()) { //&& второй не проверяет
            curPoint.setY(futureY);
            isDirectionChanged = true;
        }

        return isDirectionChanged;
    }
    private boolean moveToUp(Point curPoint) {
        boolean isDirectionChanged = false;

        int futureX = curPoint.getX();
        int futureY = curPoint.getY() - 1;
        Cell futureCell = field.getCell(futureX, futureY);
        if (futureCell != null && futureCell.isPassable()) {
            curPoint.setY(futureY);
            isDirectionChanged = true;
        }

        return isDirectionChanged;
    }
    public Field getField() {
        return field;
    }
}
