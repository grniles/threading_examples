import java.util.*;
import java.io.*;

public class IndexRunner{
	public static void main(String args[]) throws InterruptedException{
		//Start your timing here.
		long start = System.currentTimeMillis();
        File[] theFiles = new File(args[0]).listFiles();
        //IndexRunnerThread[] bookThreads = new IndexRunnerThread[theFiles.length];
        File outputFolder = new File(args[1]);
        int pageLength = Integer.parseInt(args[2]);

		int numWorkers = 8;
		IndexRunnerThread[] workers = new IndexRunnerThread[numWorkers];
        for(File file : theFiles){
            for(int i=0; i<numWorkers; i++){
                //Create a new thread and start it.
                //This is where you pass information to the Thread for it 
                //to use. In the sample below, a boolean and an int are passed in:
                //workers[i] = new IndexRunnerThread(true, i);
                workers[i] = new IndexRunnerThread(file, outputFolder, pageLength);  //add any arguments between ( )
                workers[i].start();
            }
        }

		//Print out how long it took.
		long end = System.currentTimeMillis();
		System.out.println("Time taken: "+(end-start));
	}
}