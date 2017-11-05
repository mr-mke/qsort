package qsort;

import java.util.Random;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;

public class Qsort {

    public static void main(String[] args) {
        
        int SIZE = 100000;
        
        if(args.length != 0)
            SIZE=Integer.parseInt(args[0]);

        int[] data1 = new int[SIZE];
        
        Random rand = new Random();
    
        for (int i = 0; i < data1.length; i++) {
            data1[i] = rand.nextInt(SIZE + 1);
        }
    
        System.out.println("Array generated...");
    
        quickSort quickSort = new quickSort(data1);

        ForkJoinPool pool = new ForkJoinPool();
        
        System.out.println("Sorting array...");
        
        Instant start = Instant.now();
        pool.invoke(quickSort);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        pool.shutdown();

        System.out.println("Array sorted!");
        System.out.println("Array size: "+ data1.length +" elements");
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
    }    
}
