package sense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SenseParamType {
    private static final Map<Integer, SenseParamType> allParamTypes = new HashMap<>();
    public static final SenseParamType NONE = new SenseParamType(SenseType.NONE, "none", ExpandDirection.NONE);
    public static final SenseParamType ENERGY_LEFT = new SenseParamType(SenseType.DEFAULT, "energy_left", ExpandDirection.NONE);
    public static final SenseParamType RED_COLOR_BOOST = new SenseParamType(SenseType.COLOR, "red_color_boost", ExpandDirection.ANY);
    public static final SenseParamType GREEN_COLOR_BOOST = new SenseParamType(SenseType.COLOR,"green_color_boost", ExpandDirection.ANY);
    public static final SenseParamType BLUE_COLOR_BOOST = new SenseParamType(SenseType.COLOR, "blue_color_boost", ExpandDirection.ANY);
    public static final SenseParamType RED_COLOR_REDUCE= new SenseParamType(SenseType.COLOR, "red_color_reduce", ExpandDirection.ANY);
    public static final SenseParamType GREEN_COLOR_REDUCE= new SenseParamType(SenseType.COLOR, "green_color_reduce", ExpandDirection.ANY);
    public static final SenseParamType BLUE_COLOR_REDUCE= new SenseParamType(SenseType.COLOR, "blue_color_reduce", ExpandDirection.ANY);
    public static final SenseParamType GRASS_SMELL_UP = new SenseParamType(SenseType.SMELL, "grass_smell_up", ExpandDirection.DOWN);
    public static final SenseParamType GRASS_SMELL_DOWN = new SenseParamType(SenseType.SMELL, "grass_smell_down", ExpandDirection.UP);
    public static final SenseParamType GRASS_SMELL_LEFT = new SenseParamType(SenseType.SMELL, "grass_smell_left", ExpandDirection.RIGHT);
    public static final SenseParamType GRASS_SMELL_RIGHT = new SenseParamType(SenseType.SMELL, "grass_smell_right", ExpandDirection.LEFT);

    private final int id;
    private final SenseType senseType;
    private final String name;
    private final ExpandDirection expandDirection;

    public SenseParamType(int id, SenseType senseType, String name, ExpandDirection expandDirection) {
        this.id = id;
        this.senseType = senseType;
        this.name = name;
        this.expandDirection = expandDirection;
        if (!allParamTypes.containsValue(this)) {
            allParamTypes.put(id, this);
        }
    }

    public SenseParamType(SenseType senseType, String name, ExpandDirection expandDirection) {
        this(allParamTypes.size(), senseType, name, expandDirection);
    }

    public int getId() {
        return id;
    }

    public SenseType getSenseType() {
        return senseType;
    }

    public String getName() {
        return name;
    }

    public ExpandDirection getExpandDirection() {
        return expandDirection;
    }

    public static Map<Integer, SenseParamType> getAllParamTypes() {
        return allParamTypes;
    }

    public static List<SenseParamType> getParamTypesBySenseType(SenseType senseType) {
        return allParamTypes.values().stream().filter(senseParamType -> senseParamType.getSenseType() == senseType).collect(Collectors.toList());
    }

    public static SenseParamType getById(int id) {
        return allParamTypes.getOrDefault(id, NONE);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SenseParamType other) {
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

    public enum ExpandDirection {
        NONE,
        ANY,
        UP,
        LEFT,
        DOWN,
        RIGHT;

        public static ExpandDirection reverse(ExpandDirection expandDirection) {
            switch (expandDirection) {
                case UP -> {
                    return DOWN;
                }
                case DOWN -> {
                    return UP;
                }
                case LEFT -> {
                    return RIGHT;
                }
                case RIGHT -> {
                    return LEFT;
                }
                case ANY -> {
                    return ANY;
                }
                default -> {
                    return NONE;
                }
            }
        }
    }
}
