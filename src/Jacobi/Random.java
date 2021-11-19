package Jacobi;

import java.math.BigInteger;
import java.util.Arrays;

public class Random {
    public static void main(String[] args) {

        // "t1", "t2", "t3", "t4", "t5", "t6", "t7"
        String target = "t7";

        BigInteger bigInteger = new BigInteger("0");
        while (true) {
            System.out.println("Iteration: "+bigInteger);
            IndividualTestCase individualTestCase = new IndividualTestCase(IndividualTestCase.testcase());
            individualTestCase.calcFitness(target);
            System.out.println(Arrays.toString(individualTestCase.getTestCase()) +" "+individualTestCase.getFitness());
            if (individualTestCase.getFitness() == 0) {
                return;
            }
            bigInteger = bigInteger.add(BigInteger.ONE);
        }
    }
}
