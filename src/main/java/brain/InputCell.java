package brain;

import java.util.List;
import java.util.Map;

import sense.Sense;
import sense.SenseParamType;
import sense.SenseType;

public class InputCell extends BrainCell {

    protected final SenseType senseType;
    protected final Map<SenseParamType, List<? extends BrainCell>> senseRouter;

    public InputCell(Map<SenseParamType, List<? extends BrainCell>> senseRouter, Map<BrainCell, Double> flows, SenseType senseType) {
        super(flows);
        this.senseRouter = senseRouter;
        this.senseType = senseType;
    }

    public SenseType getSenseType() {
        return senseType;
    }

    public void distributeSenseToFlows(Sense sense) {
        sense.params().forEach((param, value) -> {
            double paramValue = sense.getParam(param);
            this.senseRouter.getOrDefault(param, List.of()).forEach(brainCell -> {
                double weightedValue = paramValue * flows.get(brainCell);
                brainCell.distributeInputToFlows(weightedValue);
            });
        });
    }

    @Override
    public String getScalp() {
        StringBuilder scalp = new StringBuilder();
        for (Map.Entry<SenseParamType, List<? extends BrainCell>> route : senseRouter.entrySet()) {
            SenseParamType senseParamType = route.getKey();
            List<? extends BrainCell> cells = route.getValue();
            cells.forEach(cell -> scalp
                    .append("Input cell [").append(senseParamType.getName()).append("] ").append("-[").append(flows.get(cell)).append("]->\n")
                    .append(cell.getScalp()).append("\n")
            );
        }
        return scalp.toString();
    }
}
