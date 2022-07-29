package gene;

import java.util.Map;

import creature.CreatureAction;
import sense.SenseParamType;
import sense.SenseType;
import util.Pair;

public record GeneParsed(Map<SenseType, Map<SenseParamType, Double>> inputLayers,
                         Map<SenseType, Map<Pair<SenseParamType, CreatureAction>, Double>> outputLayers) {
}
