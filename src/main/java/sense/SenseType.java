package sense;

public enum SenseType {

    NONE("None"),
    DEFAULT("Default"),
    COLOR("Color"),
    SMELL("Smell");

    private final String name;

    SenseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
