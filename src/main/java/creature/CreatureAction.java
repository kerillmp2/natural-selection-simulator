package creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.Action;
import map.ObjectMap;
import map.objects.Food;
import map.objects.MapObject;
import sense.Sense;
import sense.SenseParamType;
import sense.SenseType;

public class CreatureAction implements Action {

    private static final Map<Integer, CreatureAction> allCreatureActions = new HashMap<>();

    public static final CreatureAction EMPTY_ACTION = new CreatureAction("EMPTY_ACTION", (performer, map) -> {
    });
    public static final CreatureAction MOVE_UP = new CreatureAction("MOVE_UP", (performer, map) -> {
        if (performer instanceof Creature creature) {
            creature.moveTo(creature.getXCoordinate(), creature.getYCoordinate() - 1);
            creature.reduceEnergy(1);
            map.updateObjectMap();
            onActionEnd(creature, map);
        }
    });

    public static final CreatureAction MOVE_DOWN = new CreatureAction("MOVE_DOWN", (performer, map) -> {
        if (performer instanceof Creature creature) {
            creature.moveTo(creature.getXCoordinate(), creature.getYCoordinate() + 1);
            creature.reduceEnergy(1);
            map.updateObjectMap();
            onActionEnd(creature, map);
        }
    });

    public static final CreatureAction MOVE_LEFT = new CreatureAction("MOVE_LEFT", (performer, map) -> {
        if (performer instanceof Creature creature) {
            creature.moveTo(creature.getXCoordinate() - 1, creature.getYCoordinate());
            creature.reduceEnergy(1);
            map.updateObjectMap();
            onActionEnd(creature, map);
        }
    });

    public static final CreatureAction MOVE_RIGHT = new CreatureAction("MOVE_RIGHT", (performer, map) -> {
        if (performer instanceof Creature creature) {
            creature.moveTo(creature.getXCoordinate() + 1, creature.getYCoordinate());
            creature.reduceEnergy(1);
            map.updateObjectMap();
            onActionEnd(creature, map);
        }
    });

    private static void tryToEat(Creature creature, ObjectMap map) {
        List<MapObject> objects = new ArrayList<>(map.getObjectsOnPosition(creature.getXCoordinate(), creature.getYCoordinate()));
        for (MapObject object : objects) {
            if (object instanceof Food food) {
                if (creature.canEat(food)) {
                    map.removeObject(food);
                    creature.addEnergy(food.getValue());
                }
            }
        }
        map.updateObjectMap();
    }

    private static void tryToBeAlive(Creature creature, ObjectMap map) {
        if (creature.getCurrentEnergy() < 0) {
            map.removeObject(creature);
            map.updateObjectMap();
            System.out.println("Creature " + creature.getName() + " died!");
        }
    }

    private static void onActionEnd(Creature creature, ObjectMap map) {
        tryToEat(creature, map);
        creature.fillSense(new Sense(SenseType.DEFAULT, new HashMap<>()).withParam(SenseParamType.ENERGY_LEFT, creature.getCurrentEnergy()));
        tryToBeAlive(creature, map);
    }

    private final int id;
    private final String name;
    private final Action action;

    private CreatureAction(int id, String name, Action action) {
        this.id = id;
        this.name = name;
        this.action = action;
        if (!allCreatureActions.containsValue(this)) {
            allCreatureActions.put(id, this);
        }
    }

    public CreatureAction(String name, Action action) {
        this(allCreatureActions.size(), name, action);
    }

    @Override
    public void resolve(MapObject performer, ObjectMap map) {
        action.resolve(performer, map);
    }

    public static CreatureAction getById(int id) {
        return allCreatureActions.getOrDefault(id, EMPTY_ACTION);
    }

    public static Map<Integer, CreatureAction> getAllCreatureActions() {
        return allCreatureActions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CreatureAction other) {
            return this.name.equalsIgnoreCase(other.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}