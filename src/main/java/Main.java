import java.util.concurrent.TimeUnit;

import controller.TurnController;
import creature.Creature;
import creature.CreaturePool;
import map.ObjectMap;
import map.PixelMap;
import map.objects.ObjectGenerator;

public class Main {
    public static void main(String[] ags) throws InterruptedException {
        int width = 100;
        int height = 100;
        int pixelSize = 5;
        PixelMap pixelMap = PixelMap.withParams(width * pixelSize, height * pixelSize, pixelSize);
        pixelMap.show();
        ObjectMap objectMap = ObjectMap.withParams(width, height);

        CreaturePool creaturePool = CreaturePool.withParams(100, 125);

        while (true) {
            TurnController.processTurn(pixelMap, objectMap);
            if (objectMap.countCreatures() == 0) {
                objectMap.clear();
                for (int i = 75; i < 100; i++) {
                    for (int j = 75; j < 100; j++) {
                        ObjectGenerator.generateGrass(objectMap, i, j, 2);
                        ObjectGenerator.generateGrass(objectMap, 100 - i, 100 - j, 2);
                    }
                }
                System.out.println("Replicating strongest creatures...");
                TimeUnit.MILLISECONDS.sleep(100);
                creaturePool.replicateCreaturesWithTopEarnedEnergy(10);
                for (Creature creature : creaturePool.getCurrentCreatures()) {
                    System.out.println(creature.getName() + "\nGene: " + creature.getGene());
                }
                objectMap.addCreaturesFromPool(creaturePool, 45, 55, 45, 55);
            }
        }
    }
}