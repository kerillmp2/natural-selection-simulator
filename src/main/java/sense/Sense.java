package sense;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Sense(SenseType type, Map<SenseParamType, Double> params) {

    public double getParam(SenseParamType senseParamType) {
        return params.getOrDefault(senseParamType, 0.0);
    }

    public Sense withParam(SenseParamType senseParamType, double value) {
        Sense newSense = new Sense(type, new HashMap<>(params));
        newSense.params.put(senseParamType, value);
        return newSense;
    }

    public Sense expand(int expandValue, List<SenseParamType.ExpandDirection> expandDirections) {
        Sense newSense = this.withParam(new SenseParamType(SenseType.NONE, "expanded", SenseParamType.ExpandDirection.NONE), expandValue);
        for (Map.Entry<SenseParamType, Double> param : params.entrySet()) {
            SenseParamType paramType = param.getKey();
            double paramValue = param.getValue();
            for (SenseParamType.ExpandDirection expandDirection : expandDirections) {
                if (paramType.getExpandDirection() == expandDirection || paramType.getExpandDirection() == SenseParamType.ExpandDirection.ANY) {
                    newSense = newSense.withParam(paramType, paramValue - expandValue);
                    break;
                }
            }
        }
        return newSense;
    }

    public boolean hasType(SenseType senseType) {
        return this.type == senseType;
    }
}
