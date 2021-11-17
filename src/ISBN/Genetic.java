package ISBN;

import java.util.List;

import static ISBN.IndividualTestCase.generatePopulation;

public class Genetic {

    public static void main(String[] args) {
        int populationSize = 500;
        List<IndividualTestCase> population = generatePopulation(populationSize);
        population.forEach(individualTestCase -> {
            System.out.println(individualTestCase.getTestCase());
        });
    }
}
