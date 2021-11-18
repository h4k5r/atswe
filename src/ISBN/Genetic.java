package ISBN;

import java.math.BigInteger;
import java.util.List;

import static ISBN.IndividualTestCase.generatePopulation;

public class Genetic {

    public static void main(String[] args) {
        int populationSize = 500;
        double previousBest = 1;
        BigInteger bigInteger = new BigInteger("0");
        List<IndividualTestCase> population = generatePopulation(populationSize);
//        System.out.println("Generated population");
//        System.out.println(population.get(populationSize - 1).getFitness());
        System.out.println("Generation: " + bigInteger);
        System.out.println("Best: " + population.get(populationSize - 1).getFitness());
        System.out.println("Worst: " + population.get(0).getFitness());
        while (true) {
            bigInteger = bigInteger.add(BigInteger.ONE);
            population = IndividualTestCase.generateNextGen(population);
//            System.out.println("Generation: " + bigInteger);
            if (population.get(populationSize - 1).getFitness() < previousBest) {
                System.out.println("Best: " + population.get(populationSize - 1).getFitness()+" "+population.get(populationSize - 1).getTestCase());
                previousBest = population.get(populationSize - 1).getFitness();
//                population.forEach(individualTestCase -> System.out.println(individualTestCase.getFitness()));
//                return;
            }
//            System.out.println("Worst: " + population.get(0).getFitness());
//            population.forEach(individualTestCase -> System.out.println(individualTestCase.getTestCase()));
            if (population.get(populationSize - 1).getFitness() == 0) {

            }

        }
    }
}
