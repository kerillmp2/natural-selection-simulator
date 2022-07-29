package brain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import creature.CreatureAction;
import sense.Sense;
import sense.SenseOrgan;
import sense.SenseType;
import util.RandomController;

public class Brain {

    private final String gene;
    private final Map<SenseType, SenseOrgan> senseRouter;
    private final List<OutputCell> outputCells;

    public Brain(String gene, Map<SenseType, SenseOrgan> senseRouter, List<OutputCell> outputCells) {
        this.gene = gene;
        this.senseRouter = senseRouter;
        this.outputCells = outputCells;
    }

    public void computeSense(Sense sense) {
        SenseType senseType = sense.type();
        if (senseRouter.containsKey(senseType)) {
            senseRouter.get(senseType).distributeSenseToCells(sense);
        }
    }

    public CreatureAction getActionFromOutputCells() {
        Collections.shuffle(outputCells);
        CreatureAction chosenAction = outputCells.get(0).getAction();
        outputCells.sort(Comparator.comparingDouble(OutputCell::getCurrentValue));
        outputCells.get(0).multiplyCurrentValue(20);
        //System.out.println(outputCells);
        double limitValue = RandomController.randomDouble(0, outputCells.stream().mapToDouble(OutputCell::getCurrentValue).sum());
        double currentValue = 0;
        for (OutputCell outputCell : outputCells) {
            currentValue += outputCell.getCurrentValue();
            if (limitValue < currentValue) {
                chosenAction = outputCell.getAction();
                break;
            }
        }
        outputCells.forEach(OutputCell::resetCurrentValue);
        //System.out.println(chosenAction.getName());
        return chosenAction;
    }

    public String getScalp() {
        StringBuilder scalp = new StringBuilder();
        for (SenseType senseType : senseRouter.keySet()) {
            scalp.append(senseType.getName()).append(":\n");
            SenseOrgan senseOrgan = senseRouter.get(senseType);
            senseOrgan.inputCells().forEach(cell -> scalp.append(cell.getScalp()));
        }
        return scalp.toString();
    }

    public String getGene() {
        return gene;
    }
}
