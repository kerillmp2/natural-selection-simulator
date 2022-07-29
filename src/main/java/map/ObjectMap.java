package map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import creature.Creature;
import creature.CreaturePool;
import map.objects.MapObject;
import map.objects.SenseContainer;
import util.Pair;
import util.RandomController;

public class ObjectMap {

    private final int width;
    private final int height;
    private final List<MapObject> mapObjects;
    private final Map<Pair<Integer, Integer>, List<MapObject>> objectMap;
    private final Map<Pair<Integer, Integer>, SenseContainer> senseContainers;

    public ObjectMap(int width, int height, List<MapObject> mapObjects, Map<Pair<Integer, Integer>, List<MapObject>> objectMap, Map<Pair<Integer, Integer>, SenseContainer> senseContainers) {
        this.width = width;
        this.height = height;
        this.mapObjects = mapObjects;
        this.objectMap = objectMap;
        this.senseContainers = senseContainers;
    }

    public static ObjectMap withParams(int width, int height) {
        return new ObjectMap(width, height, new ArrayList<>(), new HashMap<>(), new HashMap<>());
    }

    public void clear() {
        this.mapObjects.clear();
        this.objectMap.clear();
        this.senseContainers.clear();
    }

    public void updateObjectMap() {
        objectMap.clear();
        mapObjects.forEach(mapObject -> objectMap.computeIfAbsent(
                new Pair<>(mapObject.getXCoordinate(), mapObject.getYCoordinate()),
                p -> new ArrayList<>())
                .add(mapObject)
        );
    }

    public List<MapObject> getTurnOrderWithoutCreatures() {
        return mapObjects.stream()
                .filter(obj -> !(obj instanceof Creature))
                .sorted(Comparator.comparingInt(MapObject::getTurnPriority))
                .collect(Collectors.toList());
    }

    public List<MapObject> getTurnOrderOfSenseCells() {
        return senseContainers.values().stream()
                .sorted(Comparator.comparingInt(MapObject::getTurnPriority)).collect(Collectors.toList());
    }

    public List<MapObject> getCreaturesTurnOrder() {
        return mapObjects.stream()
                .filter(obj -> (obj instanceof Creature))
                .sorted(Comparator.comparingInt(MapObject::getTurnPriority))
                .collect(Collectors.toList());
    }

    public void clearSenseContainers() {
        senseContainers.clear();
    }

    public void addObject(MapObject mapObject) {
        mapObjects.add(mapObject);
        objectMap.computeIfAbsent(
                new Pair<>(mapObject.getXCoordinate(), mapObject.getYCoordinate()),
                p -> new ArrayList<>()
        ).add(mapObject);
    }

    public void addCreaturesFromPool(CreaturePool creaturePool, int xFrom, int xTo, int yFrom, int yTo) {
        List<Creature> creatures = creaturePool.getCurrentCreatures();
        for (Creature creature : creatures) {
            creature.moveTo(RandomController.randomInt(xFrom, xTo), RandomController.randomInt(yFrom, yTo));
            addObject(creature);
        }
    }

    public void removeObject(MapObject mapObject) {
        mapObjects.remove(mapObject);
        objectMap.computeIfAbsent(
                new Pair<>(mapObject.getXCoordinate(), mapObject.getYCoordinate()),
                p -> new ArrayList<>()
        ).remove(mapObject);
    }

    public SenseContainer getSenseContainerOnPosition(int x, int y) {
        return senseContainers.computeIfAbsent(new Pair<>(x, y), p -> SenseContainer.empty(p.first(), p.second()));
    }

    public List<MapObject> getObjectsOnPosition(int x, int y) {
        return objectMap.getOrDefault(new Pair<>(x, y), List.of());
    }

    public int countCreatures() {
        return (int) mapObjects.stream().filter(o -> o instanceof Creature).count();
    }

    public List<MapObject> getMapObjects() {
        return mapObjects;
    }

    public Map<Pair<Integer, Integer>, List<MapObject>> getObjectMap() {
        return objectMap;
    }

    public Map<Pair<Integer, Integer>, SenseContainer> getSenseContainers() {
        return senseContainers;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
