package logic;

public class GhostChangeDirection implements Runnable{
    private GhostController[] ghostControllers;
    private int changeFrequency;
    public GhostChangeDirection(GhostController[] ghostControllers) {
        this.ghostControllers = ghostControllers;
        changeFrequency = 1200;
    }
    public int getChangeFrequency() {
        return changeFrequency;
    }

    // Изменяет направление привидения
    @Override
    public void run() {
        for (int i = 0; i < ghostControllers.length; i++) {
            ghostControllers[i].changeDirection();
        }
    }
}
