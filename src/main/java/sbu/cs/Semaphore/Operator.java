package sbu.cs.Semaphore;

import java.time.Instant;
import java.util.concurrent.Semaphore;

public class Operator extends Thread {
    private Semaphore sem;
    private static int count=0;

    public Operator(String name,Semaphore sem) {
        super(name);
        this.sem=sem;
    }

    @Override
    public void run() {
        System.out.println("Starting: "+this.getName());
        System.out.println(this.getName()+" is waiting for a permit...");

        try {

            sem.acquire();

            String date=Instant.now().toString();
            System.out.println(this.getName() + " gets a permit."+"in date: "+date);
            Controller.result[count][0]=this.getName();
            Controller.result[count][1]=date;
            count++;

            for (int i = 0; i < 10; i++)
            {
                Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }catch (InterruptedException e){
            System.out.println(e);
        }


        System.out.println(this.getName()+ " releases the permit.");
        sem.release();

    }
}
