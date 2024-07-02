package model;

public class Pacman{
    private int livesCount;
    public static final int NORMAL_SPEED = 450;
    private int speed;
    public static final int DEFAULT_LIVES_COUNT = 3;
    private boolean killingAbility;
    public Pacman(){
        speed = NORMAL_SPEED;
        livesCount = DEFAULT_LIVES_COUNT;
        killingAbility = false;
    }
    public int getLivesCount() {
        return livesCount;
    }
    public void decrementLivesCount(){
        livesCount--;
    }
    public void setKillingAbility(boolean killingAbility){
        this.killingAbility = killingAbility;
    }
    public boolean getKillingAbility(){
        return killingAbility;
    }
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public int getSpeed(){
        return speed;
    }
    public void setNormalSpeed(){
        this.speed = NORMAL_SPEED;
    }
}
