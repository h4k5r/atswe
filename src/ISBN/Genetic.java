package ISBN;

import java.math.BigInteger;
import java.util.List;

import static ISBN.IndividualTestCase.generatePopulation;

public class Genetic {

    public static void main(String[] args) {
        // "t1", "t2", "t4", "t5"
        String target = "t5";
        int populationSize = 500;
        double previousBest = 1;
        BigInteger bigInteger = new BigInteger("0");
        List<IndividualTestCase> population = generatePopulation(populationSize,target);
//        System.out.println("Generated population");
//        System.out.println(population.get(populationSize - 1).getFitness());
        System.out.println("Generation: " + bigInteger);
        System.out.println("Best: " + population.get(populationSize - 1).getFitness());
        System.out.println("Worst: " + population.get(0).getFitness());
        while (true) {
            population = IndividualTestCase.generateNextGen(population,target);
//            System.out.println("Generation: " + bigInteger);
//            System.out.println("Best: " + population.get(populationSize - 1).getFitness());
//            System.out.println("Worst: " + population.get(0).getFitness());
            if (population.get(populationSize - 1).getFitness() == 0) {
                return;
            }
            bigInteger = bigInteger.add(BigInteger.ONE);
        }
    }
}
