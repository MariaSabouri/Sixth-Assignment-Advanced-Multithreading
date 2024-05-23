package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Controller {

    /**
     * A number of operators are trying to access a resource in parallel to each other.
     * A maximum of 2 operators should access the resource concurrently. Once 2 operators have entered
     the critical section, others must wait for at least one of them to leave.

     * Use a Semaphore to solve this synchronization problem. Semaphores behave similarly to locks,
     with the exception that they can allow more than 1 thread to enter the critical section.
     * Similar to a lock, a single instance of the Semaphore class must be shared between multiple
     threads, otherwise the threads won't be synchronized.

     * You are allowed to add new code to any of the three provided classes.
     * Do NOT change any existing lines of code. You can add new attributes and method parameters.

     * Note that every time a thread accesses the resource, you must print its Name and the System Time.
     */
    static String result[][]=new String[5][2];

    public static void main(String[] args) throws InterruptedException {
        Semaphore sem=new Semaphore(2);


        Operator operator1 = new Operator("operator1",sem);
        Operator operator2 = new Operator("operator2",sem);
        Operator operator3 = new Operator("operator3",sem);
        Operator operator4 = new Operator("operator4",sem);
        Operator operator5 = new Operator("operator5",sem);

        operator1.start();
        operator2.start();
        operator3.start();
        operator4.start();
        operator5.start();

        operator1.join();
        operator2.join();
        operator3.join();
        operator4.join();
        operator5.join();
        /*
        TODO
            Use a Semaphore to solve the synchronization problem.
            Every time a thread accesses the resource, print its Name and the current System Time.
            Combine the 4 resulting blocks to create the final matrix product and return it.
         */
        System.out.println("\n\n\n The RESULT: ");
        for (int i=0;i<result.length;i++){
            System.out.println("##################################");
            for (int j=0;j<2;j++){
                System.out.print(result[i][j]+"     ");
            }
            System.out.println();
        }
        System.out.println("##################################");
    }
}
