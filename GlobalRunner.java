import java.util.*;
import java.io.*;


public class GlobalRunner {
    public static TreeMap<String, TreeMap<String, String>> indexes = new TreeMap<>();
    public synchronized static void addIndex(String filename, TreeMap<String, String> index) {
        indexes.put(filename, index);
    }
    public static void main(String[] args) throws InterruptedException, FileNotFoundException{
        long start = System.currentTimeMillis();
        File[] theFiles = new File(args[0]).listFiles();
        File outputFolder = new File(args[1]);
        int pageLength = Integer.parseInt(args[2]);
        GlobalRunnerThread[] GlobalRunnerThreads = new GlobalRunnerThread[theFiles.length];
        int i = 0;
        for (File file : theFiles) {
            GlobalRunnerThreads[i] = new GlobalRunnerThread(file, pageLength);
            GlobalRunnerThreads[i].start();
            i++;
        }
        for (GlobalRunnerThread st : GlobalRunnerThreads) {
            if (st.isAlive()) {
                st.join();
            }
        }
        TreeSet<String> globalWordSet = new TreeSet<>();
        for (String filename : indexes.keySet()) {
            TreeMap<String, String> index = indexes.get(filename);
            for (String word : index.keySet()) {
                globalWordSet.add(word);
            }
        }
        String name = outputFolder + "/" + "output.txt";
        File fileName = new File(name);
        PrintWriter out = new PrintWriter(fileName);
        out.println("Word, " + String.join(", ", indexes.keySet()));
        for (String globalWord : globalWordSet) {
            ArrayList<String> pgNumsList = new ArrayList<>();
            for (String filename : indexes.keySet()) {
                String pgNums;
                if ((pgNums = indexes.get(filename).get(globalWord)) != null) {
                    pgNumsList.add(" " + pgNums);
                } else {
                    pgNumsList.add(" ");
                }
            }
            String globalPageNums = String.join(",", pgNumsList);
            out.println(globalWord + "," + globalPageNums);
        }
        long end = System.currentTimeMillis();
        System.out.println("This took: " + (end - start) + " milliseconds.");
    }
}