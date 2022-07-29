package brain;

import java.util.Map;

import creature.CreatureAction;

public class OutputCell extends BrainCell {

    private final CreatureAction action;
    private double currentValue;

    public OutputCell(CreatureAction action) {
        super(Map.of());
        this.action = action;
        this.currentValue = 0;
    }

    @Override
    public void distributeInputToFlows(double value) {
        this.currentValue += value;
    }

    public void resetCurrentValue() {
        this.currentValue = 0;
    }

    public CreatureAction getAction() {
        return action;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void multiplyCurrentValue(double by) {
        currentValue *= by;
    }

    @Override
    public String getScalp() {
        return "Output cell " + "[V: " + currentValue + "] " + action.getName();
    }

    @Override
    public String toString() {
        return "OutputCell{" +
                "action=" + action.getName() +
                ", currentValue=" + currentValue +
                '}';
    }
}
