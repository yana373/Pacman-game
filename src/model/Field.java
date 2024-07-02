package model;

public class Field {
    public static final int DEFAULT_FIELD_SIZE = 10;
    private Point startPacmanPoint;
    private Cell[][] field;
    private Point startGhostsPoint;
    private int width;
    private int height;
    public Field(Point startPacmanPoint, Point startGhostsPoint, int width, int height){
        this.startPacmanPoint = new Point(startPacmanPoint);
        this.startGhostsPoint = new Point(startGhostsPoint);
        // Нормализация высоты
        if (height > 0) this.height = height;
        else this.height = DEFAULT_FIELD_SIZE;
        // Нормализация ширины
        if (width > 0) this.width = width;
        else this.width = DEFAULT_FIELD_SIZE;

        field = new Cell[this.height][this.width];
    }
    public Point getStartGhostsPoint(){
        return startGhostsPoint;
    }
    public Cell getCell(int x, int y) {
        if (isPointCorrect(x, y)) return field[y][x];
        return null;
    }

    public void setCell(Cell cell, int x, int y) {
        field[y][x] = new Cell(cell);
    }
    private boolean isPointCorrect(int x, int y){
        return (x >= 0 && x < width && y >= 0 && y < height);
    }
    public Point getStartPacmanPoint() {
        return startPacmanPoint;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
}
