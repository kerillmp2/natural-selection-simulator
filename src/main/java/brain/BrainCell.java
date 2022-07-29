package brain;

import java.util.Map;

public class BrainCell {

    private static int ID_COUNTER = 1;

    protected final Map<BrainCell, Double> flows;
    protected final int id;

    public BrainCell(Map<BrainCell, Double> flows) {
        this.flows = flows;
        this.id = ID_COUNTER;
        ID_COUNTER += 1;
    }

    public void distributeInputToFlows(double value) {
        if (value > 0.00001 || value < -0.00001) {
            flows.forEach((cell, weight) -> cell.distributeInputToFlows(value * weight));
        }
    }

    public double getFlow(BrainCell brainCell) {
        return flows.getOrDefault(brainCell, 0.0);
    }

    public void addToFlow(BrainCell brainCell, double value) {
        this.flows.put(brainCell, getFlow(brainCell) + value);
    }

    public int getId() {
        return id;
    }

    public String getScalp() {
        StringBuilder scalp = new StringBuilder();
        for (Map.Entry<BrainCell, Double> flow : flows.entrySet()) {
            BrainCell nextCell = flow.getKey();
            double flowValue = flow.getValue();
            scalp.append(" --[").append(flowValue).append("]--> ").append(nextCell.getScalp()).append("\n");
        }
        return scalp.toString();
    }
}
