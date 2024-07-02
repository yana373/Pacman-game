package logic;

import model.Cell;
import model.Field;
import model.Ghost;
import model.Point;

import java.util.ArrayList;

public class GhostController extends Controller implements Runnable{
    private Point curPoint;
    private Ghost ghost;
    private Direction curDirection;
    private GameTracker gameTracker;
    public GhostController(Ghost ghost, Field field, GameTracker gameTracker){
        super(field);
        this.ghost = ghost;
        curPoint = new Point(field.getStartGhostsPoint());
        curDirection = Direction.LEFT;
        this.gameTracker = gameTracker;
        gameTracker.setGhostInfo(curPoint, ghost.getNumber());
    }
    public Ghost getGhost() {
        return ghost;
    }
    public Point getCurPoint() {return curPoint;}

    // Задает новое направление привидения
    public void changeDirection() {
        int[] availabilityDirections = getAvailabilityDirections();
        ArrayList<Integer> availableDirections = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            if (availabilityDirections[i] == 1)
                availableDirections.add(i);
        }
        int numDirection;
        numDirection = availableDirections.get((int) (Math.random() * (availableDirections.size() - 1) + 0.5));
        curDirection = Direction.values()[numDirection];
    }

    // Возвращает массив с возможными направлениями движения призрака. 1 - возможное направление, 0 - иначе (стена)
    private int[] getAvailabilityDirections() {
        int[] availabilities = {0, 0, 0, 0};
        // Лево
        Point newPoint = new Point(curPoint);
        newPoint.setX(curPoint.getX() - 1);
        Cell cell = getField().getCell(newPoint.getX(), newPoint.getY());
        if (cell != null && cell.isPassable())
            availabilities[0] = 1;
        // Право
        newPoint = new Point(curPoint);
        newPoint.setX(curPoint.getX() + 1);
        cell = getField().getCell(newPoint.getX(), newPoint.getY());
        if (cell != null && cell.isPassable())
            availabilities[1] = 1;
        // Низ
        newPoint = new Point(curPoint);
        newPoint.setY(curPoint.getY() + 1);
        cell = getField().getCell(newPoint.getX(), newPoint.getY());
        if (cell != null && cell.isPassable())
            availabilities[2] = 1;
        // Верх
        newPoint = new Point(curPoint);
        newPoint.setY(curPoint.getY() - 1);
        cell = getField().getCell(newPoint.getX(), newPoint.getY());
        if (cell != null && cell.isPassable())
            availabilities[3] = 1;

        return availabilities;
    }
    public Direction getGhostDirection(){
        return curDirection;
    }

    // Осуществляет передвижение призраков
    @Override
    public void run() {
        super.move(curDirection, curPoint);
        if (!gameTracker.ghostTracking(curPoint, ghost.getNumber())){
            curPoint = new Point(getField().getStartGhostsPoint());
            gameTracker.painting();
            gameTracker.increaseDeadGhostScore();
        }
    }
}
