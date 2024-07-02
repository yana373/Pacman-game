package model;

import java.awt.*;

public class Ghost{
    private Color curColor;
    private Color normalColor;
    private int speed;
    private int number;
    public Ghost(Color color, int number){
        this.number = number;
        speed = 450;
        this.curColor = color;
        this.normalColor = color;
    }
    public int getNumber(){
        return number;
    }
    public int getSpeed(){
        return speed;
    }
    public void setCurColor(Color curColor){
        this.curColor = curColor;
    }
    public void setNormalColor(){
        this.curColor = normalColor;
    }
    public Color getCurColor() {return curColor;}
}
