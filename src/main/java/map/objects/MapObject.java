package map.objects;

import java.awt.Color;

import action.Action;

public abstract class MapObject {

    private int xCoordinate;
    private int yCoordinate;
    private int turnPriority;
    private Color color;

    public MapObject(int xCoordinate, int yCoordinate, Color color, int turnPriority) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.color = color;
        this.turnPriority = turnPriority;
    }

    public abstract Action getCurrentAction();

    public void moveTo(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getTurnPriority() {
        return turnPriority;
    }
}
