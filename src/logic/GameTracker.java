package logic;

import model.Point;
import util.Window;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameTracker {
    private Point curPacmanPoint;
    private Point[] curGhostsPoints;
    private boolean pacmanKillingAbility;
    private int score;
    private final static int DEAD_GHOST_SCORE = 200;
    private Window window;
    private Point prevPacmanPoint;
    private Point[] prevGhostsPoints;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    public GameTracker(int ghostsCount){
        curGhostsPoints = new Point[ghostsCount];
        prevGhostsPoints = new Point[ghostsCount];
        curPacmanPoint = new Point();
        prevPacmanPoint = new Point();
        for (int i = 0; i < ghostsCount; i++) {
            curGhostsPoints[i] = new Point();
            prevGhostsPoints[i] = new Point();
        }
        score = 0;
    }
    public void setWindow(Window window) {
        this.window = window;
    }
    public void painting() {
        window.repaint();
    }

    // Отслеживает состояние призраков. True - призрак остался жив, False - иначе
    public boolean ghostTracking(Point curPoint, int ghostNum){
        lock.writeLock().lock();
        try {
            setGhostInfo(curPoint, ghostNum);
        } finally {
            lock.writeLock().unlock();
        }

        boolean isAlive;
        isAlive = !(pacmanKillingAbility && isDead(ghostNum));
        painting();
        return isAlive;
    }

    // Отслеживает состояние пакмана. True - пакман остался жив, False - иначе
    public boolean pacmanTracking(Point curPoint, boolean killingAbility){
        lock.writeLock().lock();
        try {
            setPacmanInfo(curPoint, killingAbility);
        } finally {
            lock.writeLock().unlock();
        }

        boolean isAlive;
        isAlive = true;
        if (!pacmanKillingAbility) {
            for (int i = 0; i < curGhostsPoints.length; i++) {
                if (isDead(i)) {
                    isAlive = false;
                    break;
                }
            }
        }
        painting();
        return isAlive;
    }

    // Отслеживает по координатам состояние персонажа
    private boolean isDead(int ghostNum){
        return curGhostsPoints[ghostNum].getX() == curPacmanPoint.getX()
                && curGhostsPoints[ghostNum].getY() == curPacmanPoint.getY()
                || prevPacmanPoint.getX() == curGhostsPoints[ghostNum].getX()
                && prevPacmanPoint.getY() == curGhostsPoints[ghostNum].getY()
                && prevGhostsPoints[ghostNum].getX() == curPacmanPoint.getX()
                && prevGhostsPoints[ghostNum].getY() == curPacmanPoint.getY();

    }
    public void setPacmanInfo(Point curPoint, boolean killingAbility){
        prevPacmanPoint = new Point(curPacmanPoint);
        curPacmanPoint = new Point(curPoint);
        pacmanKillingAbility = killingAbility;
    }
    public void setGhostInfo(Point curPoint, int ghostNum){
        prevGhostsPoints[ghostNum] = new Point(curGhostsPoints[ghostNum]);
        curGhostsPoints[ghostNum] = new Point(curPoint);
    }
    public int getScore(){
        return score;
    }
    public void increaseScore(int addScore){
        score += addScore;
    }
    public void increaseDeadGhostScore(){
        increaseScore(DEAD_GHOST_SCORE);
    }
}
