package creature;

import java.util.ArrayList;
import java.util.List;

import brain.Brain;
import brain.BrainBuilder;
import gene.GeneGenerator;
import map.objects.FoodType;
import util.RandomController;

public class CreaturePool {

    private List<Creature> currentCreatures;
    private final int size;
    private final int defaultEnergy;

    private CreaturePool(int size, int defaultEnergy, List<Creature> currentCreatures) {
        this.size = size;
        this.currentCreatures = currentCreatures;
        this.defaultEnergy = defaultEnergy;
    }

    public static CreaturePool withParams(int size, int defaultEnergy) {
        return new CreaturePool(size, defaultEnergy, new ArrayList<>());
    }

    public void replicateCreaturesWithTopEarnedEnergy(int except) {
        List<Creature> newCreatures = new ArrayList<>();
        int totalEnergyEarned = currentCreatures.stream().mapToInt(Creature::getEarnedEnergy).sum();
        for (Creature creature : currentCreatures) {
            int replicationNum = (int) ((size - except) * ((double) creature.getEarnedEnergy() / (double) totalEnergyEarned));
            for (int i = 0; i < replicationNum; i++) {
                newCreatures.add(creature.getMutatedReplication(creature.getName() + "_" + i, 125, 0.02));
            }
        }
        this.currentCreatures = newCreatures;
        fillWithRandomCreatures();
    }

    public void fillWithRandomCreatures() {
        while (currentCreatures.size() < size) {
            String name = RandomController.randomString(5);
            Brain brain = BrainBuilder.buildFromGene(GeneGenerator.generateSimpleCreatureGen());
            currentCreatures.add(new Creature(0, 0, name, defaultEnergy, List.of(FoodType.GRASS), brain));
        }
    }

    public List<Creature> getCurrentCreatures() {
        return currentCreatures;
    }
}
