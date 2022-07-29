package controller;

import java.util.List;

import map.ObjectMap;
import map.PixelMap;
import map.objects.MapObject;

public class TurnController {
    public static void processTurn(PixelMap pixelMap, ObjectMap objectMap) {
        List<MapObject> turnOrder = objectMap.getTurnOrderWithoutCreatures();
        turnOrder.forEach(mapObject -> mapObject.getCurrentAction().resolve(mapObject, objectMap));

        turnOrder = objectMap.getTurnOrderOfSenseCells();
        turnOrder.forEach(mapObject -> mapObject.getCurrentAction().resolve(mapObject, objectMap));

        turnOrder = objectMap.getCreaturesTurnOrder();
        turnOrder.forEach(mapObject -> mapObject.getCurrentAction().resolve(mapObject, objectMap));

        pixelMap.drawObjectMap(objectMap);
        objectMap.clearSenseContainers();
    }
}
