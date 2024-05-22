package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {
    public static class CalculationPi implements Runnable{
        /*
        initial values:        pi=3,n=2,sign=1
        Pi = Pi + sign*(4/(n) * (n+1) * (n+2))
         */
//        MathContext mc;

        int n;
        MathContext mc = new MathContext(1000);

        public CalculationPi(int n) {
//            this.mc = mc;
            this.n=2*n-2;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            System.out.println("sum was"+ sum);

            BigDecimal sign =new BigDecimal(1);
            if ((n+2)/2%2==1){
                sign=new BigDecimal(-1);
            }
            //new BigDecimal(4/((n) * (n+1) * (n+2)))
            BigDecimal result=sign.multiply(new BigDecimal(4),mc).divide(new BigDecimal(n).multiply(new BigDecimal(n+1),mc).multiply(new BigDecimal(n+2),mc),mc);
            System.out.println(Thread.currentThread().getName()+": "+"sum was:"+sum+"\nn: "+(n+2)/2+"sign: "+sign+"\nadd:"+result);

            addToSum(result);

        }

    }
    //initial value of Pi is 3
    public static BigDecimal sum;

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param value the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */


    public static synchronized void addToSum(BigDecimal value)
    {
        // TODO
        sum=sum.add(value);
        System.out.println("sum plus "+value+":\nis: "+sum);

//        return sum.toString();
    }

    public static void main(String[] args) {
        // Use the main function to test the code yourself
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        sum=new BigDecimal(3);

        for (int i=2;i<1000;i++){
            CalculationPi task=new CalculationPi(i);
            System.out.println("i: "+i);
            threadPool.execute(task);
        }
        threadPool.shutdown();


        try {
            threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum = sum.setScale(1000, RoundingMode.HALF_DOWN);
        System.out.println("Calculated Value:  " + sum);

    }
}
