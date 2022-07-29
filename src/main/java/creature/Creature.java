package creature;

import java.awt.Color;
import java.util.List;

import action.Action;
import brain.Brain;
import brain.BrainBuilder;
import gene.GeneGenerator;
import map.objects.Food;
import map.objects.FoodType;
import map.objects.MapObject;
import sense.Sense;

public class Creature extends MapObject {
    private final String name;
    private final List<FoodType> diet;
    private int currentEnergy;
    private int earnedEnergy;
    private final Brain brain;

    public Creature(int x, int y, String name, int currentEnergy, List<FoodType> diet, Brain brain) {
        super(x, y, Color.RED, 100);
        this.name = name;
        this.diet = diet;
        this.brain = brain;
        this.currentEnergy = currentEnergy;
        this.earnedEnergy = 0;
    }

    @Override
    public Action getCurrentAction() {
        //System.out.println(this.brain.getScalp());
        return brain.getActionFromOutputCells();
    }

    public Creature getMutatedReplication(String name, int energy, double mutationProbability) {
        return new Creature(
                this.getXCoordinate(),
                this.getYCoordinate(),
                name,
                energy,
                diet,
                BrainBuilder.buildFromGene(GeneGenerator.mutateGen(this.brain.getGene(), mutationProbability))
        );
    }

    public void addEnergy(int value) {
        currentEnergy += value;
        earnedEnergy += value;
    }

    public void reduceEnergy(int value) {
        currentEnergy -= value;
    }

    public void fillSense(Sense sense) {
        brain.computeSense(sense);
    }

    public boolean canEat(Food food) {
        return diet.contains(food.getFoodType());
    }

    public String getName() {
        return name;
    }

    public int getEarnedEnergy() {
        return earnedEnergy;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public List<FoodType> getDiet() {
        return diet;
    }

    public String getGene() {
        return brain.getGene();
    }
}
