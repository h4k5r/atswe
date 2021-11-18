package Jacobi;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Jacobi.IndividualTestCase.generateNextGen;
import static Jacobi.IndividualTestCase.generatePopulation;

public class Genetic {
    public static void main(String[] args) {
        BigInteger bigInteger = new BigInteger("0");
        int populationSize = 500;
//        System.out.println("Generation: " + bigInteger);
        List<IndividualTestCase> population = generatePopulation(populationSize);
        Collections.sort(population);

//        int [] dummy = {1,1};
//        IndividualTestCase dummyIndividualTestCase = new IndividualTestCase(dummy);
//        dummyIndividualTestCase.calcFitness();
//        System.out.println(dummyIndividualTestCase.getFitness());
//        System.out.println(Arrays.toString(dummyIndividualTestCase.targets));

        while (true) {
            bigInteger = bigInteger.add(BigInteger.ONE);
            System.out.println("Generation: " + bigInteger);
            population = generateNextGen(population);
//            population.forEach(individualTestCase -> {
//                if(individualTestCase.exceptionTriggered){
//                    System.out.println(Arrays.toString(individualTestCase.targets));
//                    System.out.println(individualTestCase.getFitness());
//                }
//            });
            System.out.println("Best: " + population.get(populationSize - 1).getFitness());
            System.out.println("Worst: " + population.get(0).getFitness());
//            population.forEach(individualTestCase -> System.out.println(individualTestCase.getFitness()));

            if (population.get(populationSize - 1).getFitness() == 0) {
                population.forEach(individualTestCase -> System.out.println(individualTestCase.getFitness()));
                return;
            }
        }
    }
}
