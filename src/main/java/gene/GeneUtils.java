package gene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import creature.CreatureAction;
import sense.SenseParamType;
import sense.SenseType;
import util.Pair;

public class GeneUtils {

    public static final int SENSE_PARAM_LEN = 3;
    public static final int NUM_LEN = 3;
    public static final int ACTION_LEN = 3;
    public static final char MIN_CHAR = 'A';
    public static final char MAX_CHAR = 'Z';
    public static final int DIFF = (int) MAX_CHAR - (int) MIN_CHAR;
    private static final double MAX_SENSITIVITY = 1.0;
    private static final List<Integer> POWS = IntStream.rangeClosed(0, NUM_LEN - 1).boxed().map(num -> (int) Math.pow(DIFF, num)).toList();
    public static final int MAX_NUM = POWS.get(POWS.size() - 1) * (DIFF + 1) - 1;

    // PARAM_ID inputSensitivityNum ActionID sensitivity, ActionID sensitivity; EndSymbol
    // AAA      AAA                 AAA      AAA          AAA      AA           $

    public static GeneParsed parseGene(String gene) {
        List<String> geneParts = List.of(gene.split("\\$"));
        Map<SenseType, Map<SenseParamType, Double>> inputLayers = new HashMap<>();
        Map<SenseType, Map<Pair<SenseParamType, CreatureAction>, Double>> outputLayers = new HashMap<>();

        for (String genePart : geneParts) {
            SenseParamType senseParamType = getSenseParamType(genePart);
            SenseType senseType = senseParamType.getSenseType();

            // Input layer sensitivity
            double inputLayerSensitivity = getSensitivity(genePart, SENSE_PARAM_LEN);
            inputLayers.computeIfAbsent(senseType, t -> new HashMap<>()).put(senseParamType, inputLayerSensitivity);

            int currentPosition = SENSE_PARAM_LEN + NUM_LEN;
            while (currentPosition < genePart.length()) {
                if (genePart.charAt(currentPosition) == '$') {
                    break;
                }
                CreatureAction creatureAction = getCreatureAction(genePart, currentPosition);
                currentPosition += ACTION_LEN;
                double outputSensitivity = getSensitivity(genePart, currentPosition);
                currentPosition += NUM_LEN;
                outputLayers
                        .computeIfAbsent(senseType, t -> new HashMap<>())
                        .put(new Pair<>(senseParamType, creatureAction), outputSensitivity);
            }
        }
        return new GeneParsed(inputLayers, outputLayers);
    }

    public static double getSensitivity(String genePart, int position) {
        return ((double) toInt(genePart.substring(position, position + NUM_LEN))) / ((double) MAX_NUM) * MAX_SENSITIVITY;
    }

    public static SenseParamType getSenseParamType(String genePart) {
        int sensePartParamId = toInt(genePart.substring(0, SENSE_PARAM_LEN));
        return SenseParamType.getById(sensePartParamId);
    }

    public static CreatureAction getCreatureAction(String genePart, int from) {
        int actionId = toInt(genePart.substring(from, from + ACTION_LEN));
        return CreatureAction.getById(actionId);
    }

    public static int toInt(char c) {
        if (c < MIN_CHAR || c > MAX_CHAR) {
            throw new IllegalArgumentException("Gene must contain only A-Z characters or $ as separator");
        }
        return (int) c - (int) MIN_CHAR;
    }

    public static int toInt(String s) {
        if (s.length() > NUM_LEN) {
            throw new RuntimeException("Length of gene part can't be more than " + NUM_LEN);
        }
        int currentMulti = 1;
        int sum = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char currentChar = s.charAt(i);
            int currentNum = toInt(currentChar) * currentMulti;
            sum += currentNum;
            currentMulti *= DIFF;
        }
        return sum;
    }

    public static String toStr(int num) {
        if (num > MAX_NUM) {
            throw new RuntimeException("Number must be less than " + (MAX_NUM + 1));
        }
        StringBuilder currentAns = new StringBuilder();
        int remainingValue = num;
        for (int currentPow = POWS.size() - 1; currentPow >= 0; currentPow--) {
            int currentMulti = remainingValue / POWS.get(currentPow);
            char currentChar = (char) (MIN_CHAR + currentMulti);
            currentAns.append(currentChar);
            remainingValue -= currentMulti * POWS.get(currentPow);
        }
        return currentAns.toString();
    }

    public String convertFromBaseToBase(String str, int fromBase, int toBase) {
        return Integer.toString(Integer.parseInt(str, fromBase), toBase);
    }
}
