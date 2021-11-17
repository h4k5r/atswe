package Jacobi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class IndividualTestCase implements Comparable<IndividualTestCase> {
    int[] testCase;
    double fitness;
    static int K = 1000;
    int n, k;
    List<String> targetsString = new ArrayList(Arrays.asList("t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"));

    private void setFalse() {
        Arrays.fill(this.targets, false);
    }

    public IndividualTestCase() {
        this.setFalse();
    }

    public IndividualTestCase(int[] testCase) {
        this.testCase = testCase;
        this.setFalse();
    }

    public int[] getTestCase() {
        return testCase;
    }

    public double getFitness() {
        return fitness;
    }

    public void calcFitness() {
        this.jacobiSymbol(this.testCase[0], this.testCase[1]);
    }

    boolean[] targets = new boolean[8];

    int[] branch = new int[8];

    static int generateRandom(double min, double max) {
        double random = (Math.random() * (max - min) + min);
        return (int) random;
    }

    static double normalize(int d) {
        double normalized = 1 - Math.pow(1.001, -d);
        //-(-1.13216546E145)
//        if(Math.pow(1.001,-d) == Double.POSITIVE_INFINITY) {
//            return d;
//        }
        return normalized;
    }

    double singleTargetFitness(String target, int n, int k) {
        int approachLevel = 0;
        int branchDistance = 0;
        switch (target) {
            case "t0":
                break;
            case "t1":
                if (!this.targets[1]) {
                    approachLevel = 0;
                    branchDistance = Math.abs(k - 0) + Math.abs(n % 2 - 0) + K;
                }
                break;
            case "t2":
                if (!this.targets[2]) {
                    approachLevel = 0;
                    branchDistance = Math.abs(0 - k) + K;
                }
                break;
            case "t3":
                if (!this.targets[3] && this.targets[2]) {
                    approachLevel = 1;
                    branchDistance = Math.abs(k % 2 - 0) + K;
                }
                break;
            case "t4":
                if (!this.targets[4] && this.targets[3] && this.targets[2]) {
                    approachLevel = 0;
                    branchDistance = Math.min(Math.abs((n % 8) - 3), Math.abs((n % 8) - 5)) + K;

                } else if (!this.targets[3] && this.targets[2]) {
                    approachLevel = 1;
                    branchDistance = Math.abs(k % 2 - 0) + K;

                } else if (!this.targets[2]) {
                    approachLevel = 2;
                    branchDistance = Math.abs(0 - k) + K;

                }
                break;
            case "t5":
                if (!this.targets[5] && this.targets[2]) {
                    approachLevel = 0;
                    branchDistance = Math.abs(k % 4 - 3) + Math.abs(this.n % 4 - 3) + K;

                } else if (!this.targets[2]) {
                    approachLevel = 1;
                    branchDistance = Math.abs(0 - k) + K;
                }
                break;
            case "t6":
                if (!this.targets[6]) {
                    approachLevel = 0;
                    branchDistance = Math.abs(n - 1) + K;

                }
                break;
            case "t7":
                if (!this.targets[7]) {
                    approachLevel = 0;
                    branchDistance = 0 + K;
                }
                break;
            default:
                break;
        }
        //normalize
        return approachLevel + normalize(branchDistance);
    }


    private int jacobiSymbol(int k, int n) {
        this.fitness += this.singleTargetFitness("t0", n, k);
        //t0
        this.targets[0] = true;
        //10011001
        if (k < 0 && n % 2 == 0) {
            //t1
            this.targets[1] = true;
//            System.out.println(new IllegalArgumentException("Invalid value. k = " + k + ", n = " + n));
//            throw new IllegalArgumentException("Invalid value. k = " + k + ", n = " + n);
            return -1;
        }
        this.fitness += this.singleTargetFitness("t1", n, k);
        k %= n;
        int jacobi = 1;
        while (k > 0) {
            //t2
            this.targets[2] = true;
            while (k % 2 == 0) {
                //t3
                this.targets[3] = true;
                k /= 2;
                this.k = k;
                int r = n % 8;
                if (r == 3 || r == 5) {
                    //t4
                    this.targets[4] = true;
                    jacobi = -jacobi;
                }
                this.fitness += this.singleTargetFitness("t4", n, k);
            }
            this.fitness += this.singleTargetFitness("t3", n, k);
            //n,k
            int temp = n;
            n = k;
            k = temp;
            this.k = k;
            this.n = n;
            //k,n
            if (k % 4 == 3 && n % 4 == 3) {
                //t5
                this.targets[5] = true;
                jacobi = -jacobi;
            }
            this.fitness += this.singleTargetFitness("t5", n, k);
            k %= n;
        }
        this.fitness += this.singleTargetFitness("t2", n, k);
        if (n == 1) {
            //t6
            this.targets[6] = true;
            return jacobi;
        }
        this.fitness += this.singleTargetFitness("t6", n, k);
        //t7
        this.targets[7] = true;
        this.fitness += this.singleTargetFitness("t7", n, k);
        return 0;
    }

    private static int[] testcase() {
//        -2147483647
        int n = generateRandom(-65000, 65000);
        int k = generateRandom(-65000, 65000);
        return new int[]{n, k};
    }

    public static List<IndividualTestCase> generatePopulation(int size) {
        List<IndividualTestCase> population = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            IndividualTestCase individualTestCase = new IndividualTestCase(testcase());
            individualTestCase.calcFitness();
            population.add(individualTestCase);
        }
        return population;
    }

    public static List<IndividualTestCase> crossOver(IndividualTestCase parent1, IndividualTestCase parent2) {
        List<IndividualTestCase> children = new ArrayList<>();
        int[] parent1Testcase = parent1.getTestCase();
        int[] parent2Testcase = parent2.getTestCase();
        int[] child1Testcase = new int[2];
        int[] child2Testcase = new int[2];
        int crossOverPoint = generateRandom(0, 2);
        for (int i = 0; i < 2; i++) {
            if (i < crossOverPoint) {
                child1Testcase[i] = parent1Testcase[i];
                child2Testcase[i] = parent2Testcase[i];
            } else {
                child1Testcase[i] = parent2Testcase[i];
                child2Testcase[i] = parent1Testcase[i];
            }
        }
        IndividualTestCase child1 = new IndividualTestCase(child1Testcase);
        IndividualTestCase child2 = new IndividualTestCase(child2Testcase);
        child1.mutate();
        child2.mutate();
        child1.calcFitness();
        child2.calcFitness();
        children.add(child1);
        children.add(child2);
        return children;
    }

    public int mutateBinary(int n) {
        String binary = Integer.toBinaryString(n);
        int randomLocation = generateRandom(0, binary.length() - 1);
        char[] binaryArray = binary.toCharArray();
        if (binaryArray[randomLocation] == '0') {
            binaryArray[randomLocation] = '1';
        } else {
            binaryArray[randomLocation] = '0';
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < binaryArray.length; i++) {
            sb.append(binaryArray[i]);
        }
        return Integer.parseUnsignedInt(sb.toString(), 2);
    }

    public void mutate() {
        if (generateRandom(0, 2) == 1) {
            this.testCase[1] = generateRandom(-2147483647, 2147483647);
        } else {
            this.testCase[0] = generateRandom(-2147483647, 2147483647);
        }
    }

    public static List<IndividualTestCase> generateNextGen(List<IndividualTestCase> population) {
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
        double x = o.fitness - this.fitness;
        return x > 0 ? 1 : x == 0 ? 0 : -1;
    }
}
