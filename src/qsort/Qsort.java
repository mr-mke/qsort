package qsort;

import java.io.*;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Qsort {

    public static void main(String[] args) throws IOException {
        
        int i,r,c;
        int isize,size = 1000;
        int runs=10;
        int tottime=0;
        int[] data;
        String row;
        int[] header;
        int step=10000;
        int cols=5;
        float[] avgs;
        
        //loading command line parameters
        if(args.length == 1)
            size=Integer.parseInt(args[0]);
        if(args.length == 2){
            size=Integer.parseInt(args[0]);
            runs=Integer.parseInt(args[1]);
        }
        if(args.length == 3){
            size=Integer.parseInt(args[0]);
            runs=Integer.parseInt(args[1]);
            cols=Integer.parseInt(args[2]);
        }
        if(args.length == 4){
            size=Integer.parseInt(args[0]);
            runs=Integer.parseInt(args[1]);
            cols=Integer.parseInt(args[2]);
            step=Integer.parseInt(args[3]);
        }
        
        header= new int[cols];
        avgs= new float[cols];
        isize=size;
        
        for(c=0;c<cols;c++){
            tottime=0;
            header[c]=size;
            data = new int[size];
            Random rand = new Random();
            for(r=0;r<runs;r++){
                System.out.println("Starting run no."+r);
                System.out.println("Filling array for run no."+r);

                for (i = 0; i < data.length; i++) {
                    data[i] = rand.nextInt(size + 1);
                }
                System.out.println("Array filled...");

                quickSort quickSort = new quickSort(data);

                ForkJoinPool pool = new ForkJoinPool();

                System.out.println("Sorting array...");

                Instant start = Instant.now();
                pool.invoke(quickSort);
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);

                pool.shutdown();
                
                System.out.println("Array sorted...");
                System.out.println("Array size: "+ data.length +" elements");
                System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds in run no."+r);
                tottime += timeElapsed.toMillis();
            }
            avgs[c]=tottime/runs;
            System.out.println("Average time taken for  "+ runs +" runs = "+ avgs[c] +" milliseconds");
            size=size+step;
        }
        System.out.println("\r\nArray size and average time taken for each size");
        System.out.println("Number of runs:"+runs);
        System.out.println("Number of columns:"+cols);
        System.out.println("value for first column:"+isize);
        System.out.println("Column Step:"+step);
        
        BufferedWriter output = null;
        try {
            File file = new File("results.csv");
            output = new BufferedWriter(new FileWriter(file));
            row=Arrays.toString(header);
            System.out.println(row.substring(1,row.lastIndexOf("]")));
            output.write(row.substring(1,row.lastIndexOf("]"))+"\r\n");
            row=Arrays.toString(avgs);
            System.out.println(row.substring(1,row.lastIndexOf("]")));
            output.write(row.substring(1,row.lastIndexOf("]"))+"\r\n");
        }catch ( IOException e ) {
            System.out.println("File write error!...\r\n"+e.getMessage());
        }finally{
            if ( output != null ) {
              output.close();
            }
        }
    }
}
