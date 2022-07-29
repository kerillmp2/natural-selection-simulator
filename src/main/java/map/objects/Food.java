package map.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import action.Action;
import sense.Sense;
import sense.SenseParamType;

public class Food extends MapObject {

    private final int value;
    private final FoodType foodType;
    private final Sense sense;

    public Food(int xCoordinate, int yCoordinate, Color color, int value, FoodType foodType, Sense sense) {
        super(xCoordinate, yCoordinate, color, 1);
        this.value = value;
        this.foodType = foodType;
        this.sense = sense;
    }

    @Override
    public Action getCurrentAction() {
        return (performer, map) -> {
            // Expand smell around food
            // (x - cx)^2 + (y - cy)^2 <= r^2
            for (int x = getXCoordinate() - value; x <= getXCoordinate() + value; x++) {
                for (int y = getYCoordinate() - value; y <= getYCoordinate() + value; y++) {
                    if (x >= 0 && y >= 0 && x < map.getWidth() && y < map.getHeight()) {
                        double length = Math.pow(x - getXCoordinate(), 2) + Math.pow(y - getYCoordinate(), 2);
                        if (length <= Math.pow(value, 2)) {
                            double expandedLen = Math.sqrt(length);

                            List<SenseParamType.ExpandDirection> expandDirections = new ArrayList<>();

                            if (x <= getXCoordinate()) {
                                expandDirections.add(SenseParamType.ExpandDirection.LEFT);
                            } else {
                                expandDirections.add(SenseParamType.ExpandDirection.RIGHT);
                            }
                            if (y <= getYCoordinate()) {
                                expandDirections.add(SenseParamType.ExpandDirection.UP);
                            } else {
                                expandDirections.add(SenseParamType.ExpandDirection.DOWN);
                            }

                            map.getSenseContainerOnPosition(x, y).addSense(sense.expand((int) expandedLen, expandDirections));
                        }
                    }
                }
            }
        };
    }

    public int getValue() {
        return value;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public Sense getSense() {
        return sense;
    }
}
