package event;

import logic.Controller;
import logic.PacmanController;

public class DotEvent implements Event {
    private PacmanController pacmanController;
    public static final int DEFAULT_ADDED_SCORE = 10;
    public DotEvent(PacmanController pacmanController){
        this.pacmanController = pacmanController;
    }

    // Отвечает за событие при съедении пакманом маленькой точки
    @Override
    public void event() {
        pacmanController.increaseScore(DEFAULT_ADDED_SCORE);
        pacmanController.increaseCurCountDots();
    }
}
