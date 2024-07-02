package model;

import event.Event;

public class Cell {
    private boolean passability; // Проходимость
    private Event event;
    public Cell(boolean passability, Event event){
        this.event = event;
        this.passability = passability;
    }

    // Клетка со значениями по умолчанию
    public Cell(){
        this(true, null);
    }

    // Конструктор копирования
    public Cell(Cell cell){
        this(cell.isPassable(), cell.getEvent());
    }
    public boolean isPassable(){
        return this.passability;
    }

    public Event getEvent(){
        return this.event;
    }

    public void setEvent(Event event){
        this.event = event;
    }
}
