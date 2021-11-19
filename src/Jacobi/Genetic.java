package Jacobi;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Jacobi.IndividualTestCase.generateNextGen;
import static Jacobi.IndividualTestCase.generatePopulation;

public class Genetic {
    public static void main(String[] args) {
        // "t1", "t2", "t3", "t4", "t5", "t6", "t7"
        String target = "t7";

        BigInteger bigInteger = new BigInteger("0");
        int populationSize = 500;

        List<IndividualTestCase> population = generatePopulation(populationSize, target);
        Collections.sort(population);

        while (true) {
            bigInteger = bigInteger.add(BigInteger.ONE);
            System.out.println("Generation: " + bigInteger);
            population = generateNextGen(population,target);
            System.out.println("Best: " + population.get(populationSize - 1).getFitness());
            System.out.println("Worst: " + population.get(0).getFitness());
            if (population.get(populationSize - 1).getFitness() == 0) {
                population.forEach(individualTestCase -> System.out.println(Arrays.toString(individualTestCase.getTestCase()) +" "+individualTestCase.getFitness()));
                return;
            }
        }
    }
}
