package logic;

import model.Field;
import model.Ghost;
import model.Pacman;
import util.*;
import util.Window;

import java.awt.*;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    private final int ghostsCount;
    private int curLevel;
    private final Color[] ghostsColors = new Color[] {Color.CYAN, Color.RED, Color.GREEN};
    private final int levelsCount;
    private Window window;
    private Ghost[] ghosts;
    private GhostController[] ghostControllers;
    private GhostChangeDirection ghostChangeDirection;
    private PacmanController pacmanController;
    private Pacman pacman;
    private volatile GameState gameState;
    public Game(){
        levelsCount = 2;
        curLevel = 1;
        ghostsCount = 3;
    }

    // Меняет уровни и запускает процесс игры. True - игра пройдена, false - проиграл
    public Boolean startGame(){
        for (int i = 1; i <= levelsCount; i++) {
            FieldGenerator fieldGenerator = getFieldGenerator();
            if (!processGame(fieldGenerator)){
                return false;
            }
            curLevel++;
        }
        return true;
    }

    // Отвечает за переход на новый уровень (отрисовывает новое поле)
    private FieldGenerator getFieldGenerator(){
        switch (curLevel) {
            case 1 -> {
                return new FirstLevel();
            }
            case 2 -> {
                return new SecondLevel();
            }
            default -> {
                return null;
            }
        }
    }

    // Управление игрой. True - уровень пройдена, false - не пройден
    private synchronized Boolean processGame(FieldGenerator fieldGenerator){
        GameTracker gameTracker = new GameTracker(ghostsCount);

        // Создание персонажей и поля
        pacman = new Pacman();
        ghosts = new Ghost[ghostsCount];
        for (int i = 0; i < ghostsCount; i++) {
            ghosts[i] = new Ghost(ghostsColors[i], i);
        }
        Field field = fieldGenerator.generate();

        // Управление пакманом
        pacmanController = new PacmanController(pacman, field, gameTracker);

        // Управление призраками
        ghostControllers = new GhostController[ghostsCount];
        for (int i = 0; i < ghostsCount; i++) {
            ghostControllers[i] = new GhostController(ghosts[i], field, gameTracker);
        }
        ghostChangeDirection = new GhostChangeDirection(ghostControllers);

        // Задание отрисовки
        window = new Window(pacmanController, ghostControllers);

        // Отслеживание отрисовки
        gameTracker.setWindow(window);

        // Запускаем привидения бегать
        if (curLevel > 1) {
            setWindowVisible();
            startCharacters();
        }

        // Заполнение поля
        Timer timer = new Timer();
        fieldGenerator.fill(field, pacmanController, timer, ghostControllers);

        // Ввод с клавиатуры
        while (true) {
            gameState = getGameState(fieldGenerator);
            switch (gameState) {
                case OTHER -> {
                }
                case WON -> {
                    return true;
                }
                case LOST -> {
                    return false;
                }
            }
        }
    }

    // Отслеживает состояние игры
    public GameState getGameState(FieldGenerator fieldGenerator) {
        if (pacman.getLivesCount() <= 0) {
            setWindowVisible(false);
            return GameState.LOST;
        }
        if (pacmanController.getCurDotsCount() == fieldGenerator.getDotsCount()) {
            window.dispose();
            return GameState.WON;
        }
        return GameState.OTHER;
    }

    public void startCharacters(){
        // запускаем привидения бегать
        ScheduledExecutorService[] schedulers = new ScheduledExecutorService[ghostsCount];
        for (int i = 0; i < ghostsCount; i++) {
            schedulers[i] = Executors.newSingleThreadScheduledExecutor();
            schedulers[i].scheduleAtFixedRate(ghostControllers[i], 0, ghosts[i].getSpeed(), TimeUnit.MILLISECONDS);
        }
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(ghostChangeDirection, 0, ghostChangeDirection.getChangeFrequency(),
                TimeUnit.MILLISECONDS);

        // запускаем пакмана бегать
        ScheduledExecutorService pacmanScheduler = Executors.newSingleThreadScheduledExecutor();
        pacmanScheduler.scheduleAtFixedRate(pacmanController, 0, pacman.getSpeed(), TimeUnit.MILLISECONDS);
    }

    public void setWindowVisible() { //remove
        setWindowVisible(true);
    }
    public void setWindowVisible(boolean visibility){
        window.setVisible(visibility);
    }
}
