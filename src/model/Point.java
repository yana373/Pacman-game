package model;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        setX(x);
        setY(y);
    }

    public Point(Point point) {
        this(point.getX(), point.getY());
    }

    public Point(){
        x = -1;
        y = -1;
    }
    public void setX(int x){
        if (x >= 0) this.x = x;
    }
    public int getX(){
        return x;
    }
    public void setY(int y){
        if (y >= 0) this.y = y;
    }
    public int getY(){
        return y;
    }
}
