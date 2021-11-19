package ISBN;

import java.math.BigInteger;

public class Random {
    public static void main(String[] args) {
        // "t1", "t2" "t4", "t5"
        String target = "t1";
        BigInteger bigInteger = new BigInteger("0");
        while (true) {
            System.out.println("Iteration :" + bigInteger);
            IndividualTestCase individualTestCase = new IndividualTestCase(IndividualTestCase.generateSequence());
            individualTestCase.calculateFitness(target);
            System.out.println(individualTestCase.getTestCase() + " " + individualTestCase.getFitness());
            if (individualTestCase.getFitness() == 0) {
                return;
            }
            bigInteger = bigInteger.add(BigInteger.ONE);
        }
    }
}
