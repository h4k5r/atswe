package ISBN;

import java.util.*;

public class IndividualTestCase implements Comparable<IndividualTestCase> {
    String testCase;
    double fitness;
    boolean[] targets = new boolean[6];
    static int K = 100;
    static int maxLength = 14;
    //"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890/*-+\\|/<,>.:;\"'{}[]()&*%^#$!@~` "
    static String source = "1234568790";
    private double mutationRate = 0.6;
    private int pre;
    private int postStrLen;
    private int sum;

    public IndividualTestCase() {
    }

    public IndividualTestCase(String testCase) {
        this.testCase = testCase;
        this.fitness = 0;
        setFalse();
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
        String[] targets = {"t0", "t1", "t2", "t3", "t4", "t5"};
        this.isISBN13(this.testCase);
        for (String target : targets) {
            this.calcSingleTargetFitness(target);
        }
    }

    static double normalize(int d) {
        return 1 - Math.pow(1.001, -d);
    }

    double t1() {
        int approachLevel = 0;
        int branchDistance = 0;
        if (!targets[1]) {
            approachLevel = 0;
            branchDistance = Math.abs(this.pre - 978) + K;
        }
        return approachLevel + normalize(branchDistance);
    }

    double t2() {
        int approachLevel = 0;
        int branchDistance = 0;
        if (!targets[2]) {
            approachLevel = 0;
            branchDistance = Math.abs(this.postStrLen - 10) + K;
        }
        return approachLevel + normalize(branchDistance);
    }

    double t4() {
        int approachLevel = 0;
        int branchDistance = 0;
        if (!targets[4]) {
            approachLevel = 0;
            branchDistance = Math.abs(this.sum % 10 - 0) + K;
        }
        return approachLevel + normalize(branchDistance);
    }

    double t5() {
        int approachLevel = 0;
        int branchDistance = 0;
        if (!targets[5]) {
            approachLevel = 0;
            branchDistance = 0 + K;
        }
        return approachLevel + normalize(branchDistance);
    }

    void calcSingleTargetFitness(String target) {
        switch (target) {
            case "t0":
                break;
            case "t1":
                this.fitness += this.t1();
                break;
            case "t2":
                this.fitness += this.t2();
                break;
            case "t3":
                break;
            case "t4":
                this.fitness += this.t4();
                break;
            case "t5":
                this.fitness += this.t5();
                break;
            default:
                break;
        }
    }

    public boolean isISBN13(String in) {
        //t0
        this.targets[0] = true;
        int pre = 0;
        try {
            pre = Integer.parseInt(in.substring(0, 3));
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return false;
        }
        if (pre != 978) {
            //t1
            this.targets[1] = true;
            return false;
        }
        this.pre = pre;
        String postStr = in.substring(4);
        this.postStrLen = postStr.length();
        if (postStr.length() != 10) {
            //t2
            this.targets[2] = true;
            return false;
        }
        try {
            long post = Long.parseLong(postStr);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return false;
        }
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
        this.sum = sum;
        //t5
        targets[5] = true;
        return false;
    }

    static int generateRandom(double min, double max) {
        double random = (Math.random() * (max - min) + min);
        return (int) random;
    }

    static int generateRandom(int max) {
        double random = (Math.random() * (max));
        return (int) random;
    }

    static String generateSequence() {

        StringBuilder sequence = new StringBuilder();
//        sequence.append("978");
        for (int i = 0; i < maxLength; i++) {
            sequence.append(source.charAt(generateRandom(source.length() - 1)));
        }
        return sequence.toString();
    }

    public static List<IndividualTestCase> generatePopulation(int size) {
        List<IndividualTestCase> population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            IndividualTestCase testCase = new IndividualTestCase(generateSequence());
            testCase.calculateFitness();
            population.add(testCase);
        }
        Collections.sort(population);
        return population;
    }

    void mutate() {
        double mutationRateRate = Math.random();
        if (mutationRateRate < this.mutationRate) {
            char randomGene = source.charAt(generateRandom(source.length() - 1));
            int randomPosition = generateRandom(this.testCase.length() - 1);
            this.testCase = this.testCase.substring(0, randomPosition) + randomGene + this.testCase.substring(randomPosition + 1);
        }
    }

    private static List<IndividualTestCase> crossOver(IndividualTestCase individualTestCase, IndividualTestCase individualTestCase1) {
        List<IndividualTestCase> children = new ArrayList<>();
        int crossOverPoint = generateRandom(individualTestCase.getTestCase().length() - 1);
        String child1Str = individualTestCase.getTestCase().substring(0, crossOverPoint) + individualTestCase1.getTestCase().substring(crossOverPoint);
        String child2Str = individualTestCase1.getTestCase().substring(0, crossOverPoint) + individualTestCase.getTestCase().substring(crossOverPoint);
        IndividualTestCase child1 = new IndividualTestCase(child1Str);
        IndividualTestCase child2 = new IndividualTestCase(child2Str);
        child1.mutate();
        child2.mutate();
        child1.calculateFitness();
        child2.calculateFitness();
        children.add(child1);
        children.add(child2);
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
            nextGen.addAll(crossOver(Q3.get(i), Q2.get(i)));
        }
        nextGen.addAll(Q1);
        nextGen.addAll(Q3);
        Collections.sort(nextGen);
        return nextGen;
    }

    @Override
    public int compareTo(IndividualTestCase o) {
        double x = o.fitness - this.fitness;
        return x > 0 ? 1 : x == 0 ? 0 : -1;
//        return (int) (this.fitness - o.getFitness());
    }
}
