package Jacobi;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static Jacobi.IndividualTestCase.generateNextGen;
import static Jacobi.IndividualTestCase.generatePopulation;

public class Genetic {
    public static void main(String[] args) {
        BigInteger bigInteger = new BigInteger("0");
        int populationSize = 500;
        List<IndividualTestCase> population = generatePopulation(populationSize);
        Collections.sort(population);
        while (true) {
            System.out.println("Generation: " + bigInteger);
            population = generateNextGen(population);
            System.out.println("Best: " + population.get(populationSize - 1).getFitness());
            System.out.println("Worst: " + population.get(0).getFitness());
            bigInteger = bigInteger.add(BigInteger.ONE);
            if (population.get(populationSize - 1).getFitness() == 0) {
                return;
            }
        }
    }
}
