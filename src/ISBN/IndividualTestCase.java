package ISBN;

import java.math.BigDecimal;
import java.util.*;

public class IndividualTestCase implements Comparable<IndividualTestCase> {
    String testCase;
    double fitness;
    boolean[] targets = new boolean[6];
    static int K = 100;
    static String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public IndividualTestCase() {
    }

    public IndividualTestCase(String testCase, double fitness) {
        this.testCase = testCase;
        this.fitness = fitness;
    }

    public String getTestCase() {
        return testCase;
    }

    public double getFitness() {
        return fitness;
    }

    private void setFalse() {
        Arrays.fill(this.targets, false);
    }


    public void calculateFitness() {
        double fitness = 0;
        List<String> targetsString = new ArrayList(Arrays.asList("t0", "t1", "t2", "t3", "t4", "t5"));
        this.isISBN13(this.testCase);
        targetsString.forEach(target -> {

        });
    }
    static double normalize(int d){
        double normalized = 1 - Math.pow(1.001,-d);
        if(Math.pow(1.001,-d) == Double.POSITIVE_INFINITY) {
            BigDecimal bd = new BigDecimal(1.001).pow(-d);
            System.out.println(bd);
            System.out.println("d = " + d);
        }
        return normalized;
    }
    double singleTargetString(String target) {
        int approachLevel = 0;
        int branchDistance = 0;
        switch (target) {
            case "t0":
                break;
            case "t1":
                if(!targets[1]) {
                    approachLevel = 0;
                    branchDistance = K;
                }
                break;
            case "t2":
                if (!targets[2]) {
                    approachLevel = 0;
                    branchDistance = K;
                }
                break;
            case "t3":
                break;
            case "t4":
                approachLevel = 0;
                branchDistance = Math.abs(38);
                break;
            case "t5":
                if (!targets[5]) {
                    approachLevel = 0;
                    branchDistance = 0;
                }
                break;
            default:
                break;
        }
        return approachLevel + normalize(branchDistance);
    }

    public boolean isISBN13(String in) {
        //t0
        this.targets[0] = true;
        int pre = Integer.parseInt(in.substring(0, 3));
        if (pre != 978) {
            //t1
            this.targets[1] = true;
            return false;
        }
        String postStr = in.substring(4);
        if (postStr.length() != 10) {
            //t2
            this.targets[2] = true;
            return false;
        }
        int post = Integer.parseInt(postStr);
        int sum = 38;
        for (int x = 0; x < 10; x += 2) {
            //t3
            this.targets[3] = true;
            sum += (postStr.charAt(x) - 48) * 3 + ((postStr.charAt(x + 1) - 48));
        }

        if (sum % 10 == 0) {
            //t4
            this.targets[4] = true;
            return true;
        }
        //t5
        targets[5] = true;
        return false;
    }

    public static int generateRandom(float max) {
        return (int) Math.floor(Math.random() * max);
    }

    static String generateSequence() {

        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < 14; i++) {
            sequence.append(source.charAt(generateRandom(source.length() - 1)));
        }
        return sequence.toString();
    }

    public static List<IndividualTestCase> generatePopulation(int size) {
        List<IndividualTestCase> population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            IndividualTestCase testCase = new IndividualTestCase(generateSequence(), 0);
            population.add(testCase);
        }
        return population;
    }

    void mutate() {
        char randomGene = source.charAt(generateRandom(source.length() - 1));
        int randomPosition = generateRandom(testCase.length() - 1);
        testCase = testCase.substring(0, randomPosition) + randomGene + testCase.substring(randomPosition + 1);
    }

    private static List<IndividualTestCase> crossOver(IndividualTestCase individualTestCase, IndividualTestCase individualTestCase1) {
        List<IndividualTestCase> children = new ArrayList<>();
        int crossOverPoint = generateRandom(individualTestCase.getTestCase().length() - 1);
        String child1 = individualTestCase.getTestCase().substring(0, crossOverPoint) + individualTestCase1.getTestCase().substring(crossOverPoint);
        String child2 = individualTestCase1.getTestCase().substring(0, crossOverPoint) + individualTestCase.getTestCase().substring(crossOverPoint);
        children.add(new IndividualTestCase(child1, 0));
        children.add(new IndividualTestCase(child2, 0));
        children.forEach(IndividualTestCase::mutate);
        return children;
    }

    static List<IndividualTestCase> generateNextGen(List<IndividualTestCase> population) {
        List<IndividualTestCase> nextGen = new ArrayList<>();
        List<IndividualTestCase> Q1 = population.subList((int) Math.floor((population.size() - 1) * 0.75),
                population.size() - 1);
        List<IndividualTestCase> Q2 = population.subList((int) Math.floor((population.size() - 1) * 0.5),
                (int) Math.floor((population.size() - 1) * 0.75));
        List<IndividualTestCase> Q3 = population.subList((int) Math.ceil((population.size() - 1) * 0.25),
                (int) Math.ceil((population.size() - 1) * 0.5));
        for (int i = 0; i < Q1.size(); i++) {
            nextGen.addAll(crossOver(Q3.get(i), Q1.get(i)));
            nextGen.addAll(crossOver(Q3.get(i), Q2.get(i)));
        }
        Collections.sort(nextGen);
        return nextGen;
    }

    @Override
    public int compareTo(IndividualTestCase o) {
        return (int) (this.fitness - o.getFitness());
    }
}
