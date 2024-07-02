package util;

import logic.Direction;
import logic.PacmanController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    private PacmanController pacmanController;
    public KeyboardListener(PacmanController pacmanController) {
        this.pacmanController = pacmanController;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> pacmanController.move(Direction.UP);
            case KeyEvent.VK_A -> pacmanController.move(Direction.LEFT);
            case KeyEvent.VK_S -> pacmanController.move(Direction.DOWN);
            case KeyEvent.VK_D -> pacmanController.move(Direction.RIGHT);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> pacmanController.move(Direction.UP);
            case KeyEvent.VK_A -> pacmanController.move(Direction.LEFT);
            case KeyEvent.VK_S -> pacmanController.move(Direction.DOWN);
            case KeyEvent.VK_D -> pacmanController.move(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
