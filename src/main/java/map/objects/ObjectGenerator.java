package map.objects;

import java.awt.Color;
import java.util.HashMap;

import map.ObjectMap;
import sense.Sense;
import sense.SenseParamType;
import sense.SenseType;
import util.RandomController;

public class ObjectGenerator {

    public static void generateGrass(ObjectMap objectMap, int value, double probability) {
        int width = objectMap.getWidth();
        int height = objectMap.getHeight();
        Sense sense = new Sense(SenseType.SMELL, new HashMap<>())
                .withParam(SenseParamType.GREEN_COLOR_BOOST, value)
                .withParam(SenseParamType.GRASS_SMELL_UP, value)
                .withParam(SenseParamType.GRASS_SMELL_DOWN, value)
                .withParam(SenseParamType.GRASS_SMELL_LEFT, value)
                .withParam(SenseParamType.GRASS_SMELL_RIGHT, value);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (RandomController.roll(probability)) {
                    objectMap.addObject(new Food(x, y, Color.GREEN, value, FoodType.GRASS, sense));
                }
            }
        }
    }

    public static void generateGrass(ObjectMap objectMap, int x, int y, int value) {
        Sense sense = new Sense(SenseType.SMELL, new HashMap<>())
                .withParam(SenseParamType.GREEN_COLOR_BOOST, value)
                .withParam(SenseParamType.GRASS_SMELL_UP, value)
                .withParam(SenseParamType.GRASS_SMELL_DOWN, value)
                .withParam(SenseParamType.GRASS_SMELL_LEFT, value)
                .withParam(SenseParamType.GRASS_SMELL_RIGHT, value);
        objectMap.addObject(new Food(x, y, Color.GREEN, value, FoodType.GRASS, sense));
    }
}
