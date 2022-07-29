package sense;

import java.util.List;

import brain.InputCell;

public record SenseOrgan(SenseType senseType, List<InputCell> inputCells) {

    public void distributeSenseToCells(Sense sense) {
        if (sense.hasType(this.senseType)) {
            inputCells.forEach(cell -> cell.distributeSenseToFlows(sense));
        }
    }

    public void addInputCell(InputCell inputCell) {
        this.inputCells.add(inputCell);
    }
}
