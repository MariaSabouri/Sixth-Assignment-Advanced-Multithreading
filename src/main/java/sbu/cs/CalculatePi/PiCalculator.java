package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {
    public static BigDecimal sum;
    static MathContext mc = new MathContext(1500);
    public static class CalculationPiInv implements Runnable{
        int n;


        public CalculationPiInv(int n) {
            this.n=n;
        }

        @Override
        public void run() {

            BigDecimal b2=new BigDecimal("545140134",mc).multiply(new BigDecimal(n),mc).add(new BigDecimal("13591409",mc),mc);
            BigDecimal b3=factorial(6*n);
            BigDecimal b4=factorial(3*n);
            BigDecimal b5=factorial(n);
            BigDecimal b6=new BigDecimal("-262537412640768000",mc).pow(n,mc);

            BigDecimal result=b2.multiply(b3,mc).divide(b4.multiply(b5.pow(3,mc),mc).multiply(b6,mc),mc);

//            System.out.println(Thread.currentThread().getName()+": "+"sum was:"+sum+"\nadd:"+result);

            addToSum(result);

        }
        public BigDecimal factorial(int n){
            BigDecimal temp = new BigDecimal(1);
            for (int i = 1; i <= n; i++) {
                temp = temp.multiply(new BigDecimal(i), mc);
            }

            return temp;
        }

    }

    public static synchronized BigDecimal addToSum(BigDecimal value)
    {
        // TODO
        sum=sum.add(value);
        return sum;
    }


    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */

    public static String calculate(int floatingPoint)
    {
        // TODO
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        sum=new BigDecimal(0);


        for (int i=0;i<1000;i++){
            CalculationPiInv task=new CalculationPiInv(i);
            threadPool.execute(task);
        }
        threadPool.shutdown();


        try {
            threadPool.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BigDecimal b1=new BigDecimal("426880",mc).multiply(new BigDecimal("10005",mc).sqrt(mc));
        BigDecimal Pi=b1.divide(sum,mc);
        Pi = Pi.setScale(floatingPoint, RoundingMode.FLOOR);

        System.out.println(Pi);
        return Pi.toString();
    }

    public static void main(String[] args) {
        // Use the main function to test the code yourself
//        calculate(100);
//        calculate(4);
//        calculate(7);

    }
}
