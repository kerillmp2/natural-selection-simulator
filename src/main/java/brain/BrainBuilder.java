package brain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import creature.CreatureAction;
import gene.GeneParsed;
import gene.GeneUtils;
import sense.SenseOrgan;
import sense.SenseParamType;
import sense.SenseType;
import util.Pair;

public class BrainBuilder {

    public static Brain buildFromGene(String gene) {
        return BrainTemplate.buildFromGene(gene);
    }

    private static class BrainTemplate {
        private final Map<SenseType, SenseOrgan> senseRouter;
        private final Map<CreatureAction, OutputCell> actionsToOutputs;

        private BrainTemplate(Map<SenseType, SenseOrgan> senseRouter, Map<CreatureAction, OutputCell> actionsToOutputs) {
            this.senseRouter = senseRouter;
            this.actionsToOutputs = actionsToOutputs;
        }

        public static Brain buildFromGene(String gene) {
            BrainTemplate brainTemplate = new BrainTemplate(new HashMap<>(), new HashMap<>());
            GeneParsed geneParsed = GeneUtils.parseGene(gene);

            for (SenseType senseType : geneParsed.inputLayers().keySet()) {
                brainTemplate.withSense(senseType, geneParsed.inputLayers().get(senseType), geneParsed.outputLayers().get(senseType));
            }

            return brainTemplate.build(gene);
        }

        public BrainTemplate withSense(
                SenseType senseType,
                Map<SenseParamType, Double> inputLayerSensitivity,
                Map<Pair<SenseParamType, CreatureAction>, Double> outputLayerSensitivity)
        {
            Map<SenseParamType, List<? extends BrainCell>> inputCellRouter = new HashMap<>();
            Map<BrainCell, Double> inputCellFlows = new HashMap<>();

            inputLayerSensitivity.forEach((senseParamType, paramSensitivity) -> {
                Map<BrainCell, Double> middleCellFlows = new HashMap<>();

                outputLayerSensitivity.forEach((pair, value) -> {
                    SenseParamType senseParam = pair.first();
                    if (senseParam == senseParamType) {
                        CreatureAction action = pair.second();
                        addOutput(action);
                        OutputCell outputCell = actionsToOutputs.get(action);
                        middleCellFlows.put(outputCell, value);
                    }
                });

                BrainCell middleCell = new BrainCell(middleCellFlows);
                inputCellRouter.put(senseParamType, List.of(middleCell));
                inputCellFlows.put(middleCell, inputLayerSensitivity.getOrDefault(senseParamType, 0.0));
            });

            InputCell inputCell = new InputCell(inputCellRouter, inputCellFlows, senseType);
            this.senseRouter.put(senseType, new SenseOrgan(senseType, List.of(inputCell)));
            return this;
        }

        private void addOutput(CreatureAction creatureAction) {
            if (!actionsToOutputs.containsKey(creatureAction)) {
                this.actionsToOutputs.put(creatureAction, new OutputCell(creatureAction));
            }
        }

        public Brain build(String gene) {
            return new Brain(gene, senseRouter, new ArrayList<>(actionsToOutputs.values()));
        }
    }
}
