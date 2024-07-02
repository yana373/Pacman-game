package util;

import logic.GhostController;
import logic.PacmanController;
import model.Field;

import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

public interface FieldGenerator {
    public Field generate();
    public void fill(Field field, PacmanController pacmanController, Timer timer,
                     GhostController[] ghostControllers);
    public int getDotsCount();
}
