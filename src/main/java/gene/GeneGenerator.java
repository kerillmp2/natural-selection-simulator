package gene;

import java.util.Arrays;
import java.util.List;

import creature.CreatureAction;
import sense.SenseParamType;
import util.RandomController;

public class GeneGenerator {

    // PARAM_ID inputSensitivityNum ActionID sensitivity, ActionID sensitivity; EndSymbol
    // AAA      AAA                 AAA      AAA          AAA      AA           $

    public static String generateSimpleCreatureGen() {
        return generateSimpleRandomGen(
                List.of(SenseParamType.ENERGY_LEFT, SenseParamType.GRASS_SMELL_UP, SenseParamType.GRASS_SMELL_DOWN, SenseParamType.GRASS_SMELL_LEFT, SenseParamType.GRASS_SMELL_RIGHT),
                List.of(CreatureAction.MOVE_UP, CreatureAction.MOVE_DOWN, CreatureAction.MOVE_LEFT, CreatureAction.MOVE_RIGHT)
        );
    }

    public static String mutateGen(String oldGene, double mutationProbability) {
        StringBuilder newGeneBuilder = new StringBuilder();
        List<String> geneParts = Arrays.stream(oldGene.split("\\$")).toList();
        for (String genePart : geneParts) {
            StringBuilder newGenePartBuilder = new StringBuilder();
            String senseParamString = genePart.substring(0, GeneUtils.SENSE_PARAM_LEN);
            String oldSenseParamSensitivity = genePart.substring(GeneUtils.SENSE_PARAM_LEN, GeneUtils.SENSE_PARAM_LEN + GeneUtils.NUM_LEN);
            newGenePartBuilder.append(senseParamString).append(mutateString(oldSenseParamSensitivity, mutationProbability));

            for (int i = GeneUtils.SENSE_PARAM_LEN + GeneUtils.NUM_LEN; i < genePart.length(); i += GeneUtils.ACTION_LEN + GeneUtils.NUM_LEN) {
                String actionString = genePart.substring(i, i + GeneUtils.ACTION_LEN);
                String oldActionSensitivity = genePart.substring(i + GeneUtils.ACTION_LEN, i + GeneUtils.ACTION_LEN + GeneUtils.NUM_LEN);
                newGenePartBuilder.append(actionString).append(mutateString(oldActionSensitivity, mutationProbability));
            }
            newGeneBuilder.append(newGenePartBuilder).append("$");
        }
        return newGeneBuilder.toString();
    }

    private static String mutateString(String s, double mutationProbability) {
        StringBuilder mutatedString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (RandomController.roll(mutationProbability)) {
                char mutationChar = (char) RandomController.randomInt(GeneUtils.MIN_CHAR, GeneUtils.MAX_CHAR);
                mutatedString.append(mutationChar);
            } else {
                char nonMutationChar = s.charAt(i);
                mutatedString.append(nonMutationChar);
            }
        }
        return mutatedString.toString();
    }

    //AAIABBAABABB$AAJABBAACABC$AAKABBAADABD$AALABBAAEABE
    public static String generateSimpleRandomGen(List<SenseParamType> paramTypes, List<CreatureAction> actions) {
        StringBuilder geneBuilder = new StringBuilder();
        for (SenseParamType senseParamType : paramTypes) {
            int paramTypeId = senseParamType.getId();
            StringBuilder genePartBuilder = new StringBuilder(GeneUtils.toStr(paramTypeId));
            int paramSensitivity = RandomController.randomInt(0, GeneUtils.MAX_NUM);
            genePartBuilder.append(GeneUtils.toStr(paramSensitivity));
            for (CreatureAction action : actions) {
                int actionId = action.getId();
                int actionSensitivity = RandomController.randomInt(0, GeneUtils.MAX_NUM);
                genePartBuilder.append(GeneUtils.toStr(actionId)).append(GeneUtils.toStr(actionSensitivity));
            }
            geneBuilder.append(genePartBuilder).append("$");
        }
        return geneBuilder.toString();
    }
}
