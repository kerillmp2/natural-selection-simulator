package map.objects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import action.Action;
import creature.Creature;
import sense.Sense;
import sense.SenseParamType;

public class SenseContainer extends MapObject {

    private final List<Sense> senseList;

    private SenseContainer(int xCoordinate, int yCoordinate, List<Sense> senseList) {
        super(xCoordinate, yCoordinate, Color.WHITE, 50);
        this.senseList = senseList;
    }

    public static SenseContainer empty(int x, int y) {
        return new SenseContainer(x, y, new ArrayList<>());
    }

    public void addSense(Sense sense) {
        this.senseList.add(sense);
    }

    /*@Override
    public Color getColor() {
        Color currentColor = super.getColor();
        int red = currentColor.getRed();
        int green = currentColor.getGreen();
        int blue = currentColor.getBlue();
        int redColorBooster = 0;
        int greenColorBooster = 0;
        int blueColorBooster = 0;
        int redColorReducer = 0;
        int greenColorReducer = 0;
        int blueColorReducer = 0;
        for (Sense sense : senseList) {
            redColorBooster += sense.getParam(SenseParamType.RED_COLOR_BOOST) * 5;
            greenColorBooster += sense.getParam(SenseParamType.GREEN_COLOR_BOOST) * 5;
            blueColorBooster += sense.getParam(SenseParamType.BLUE_COLOR_BOOST) * 5;
            redColorReducer += sense.getParam(SenseParamType.RED_COLOR_REDUCE) * 5;
            greenColorReducer += sense.getParam(SenseParamType.GREEN_COLOR_REDUCE) * 5;
            blueColorReducer += sense.getParam(SenseParamType.BLUE_COLOR_REDUCE) * 5;
        }
        red = Math.min(255, Math.max(0, red - greenColorBooster - blueColorBooster - redColorReducer));
        green = Math.min(255, Math.max(0, green - redColorBooster - blueColorBooster - greenColorReducer));
        blue = Math.min(255, Math.max(0, blue - redColorBooster - greenColorBooster - blueColorReducer));

        return new Color(red, green, blue);
    }*/

    @Override
    public Action getCurrentAction() {
        return (performer, map) -> {
            List<MapObject> mapObjects = map.getObjectsOnPosition(performer.getXCoordinate(), performer.getYCoordinate());
            mapObjects.forEach(obj -> {
                if (obj instanceof Creature creature) {
                    senseList.forEach(creature::fillSense);
                }
            });
        };
    }
}
