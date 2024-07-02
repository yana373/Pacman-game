package logic;

import model.Cell;
import model.Field;
import model.Pacman;
import model.Point;

public class PacmanController extends Controller implements Runnable{
    private Point curPoint;
    private GameTracker gameTracker;
    private Pacman pacman;
    private Direction prevDirection;
    private int curCountDots;
    public PacmanController(Pacman pacman, Field field, GameTracker gameTracker){
        super(field);
        this.pacman = pacman;
        curPoint = new Point(field.getStartPacmanPoint());
        prevDirection = Direction.UP;
        this.gameTracker = gameTracker;
        gameTracker.setPacmanInfo(curPoint, pacman.getKillingAbility());
        curCountDots = 0;
    }
    public void move(Direction direction) {
        prevDirection = direction;
    }
    public Direction getPrevDirection(){
        return prevDirection;
    }
    public Point getCurPoint() {
        return curPoint;
    }
    public void setKillingAbility(boolean killingAbility){
        pacman.setKillingAbility(killingAbility);
    }
    public Pacman getPacman(){
        return pacman;
    }

    @Override
    public void run() {
        // Если передвижение было осуществлено
        boolean wasMove = super.move(prevDirection, curPoint);
        Cell cell = super.getField().getCell(curPoint.getX(), curPoint.getY());
        // Если пакмана убили
        if (!gameTracker.pacmanTracking(curPoint, pacman.getKillingAbility())){
            pacman.decrementLivesCount();
            curPoint = new Point(getField().getStartPacmanPoint());
            gameTracker.painting();
        }
        else if (wasMove){
            if (cell.getEvent() != null) {
                cell.getEvent().event();
                cell.setEvent(null);
            }
        }
    }
    public void increaseScore(int addScore){
        gameTracker.increaseScore(addScore);
    }
    public void increaseCurCountDots(){
        curCountDots++;
    }
    public int getCurDotsCount(){
        return curCountDots;
    }
    public GameTracker getGameTracker(){
        return gameTracker;
    }
}
