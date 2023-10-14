import java.util.*;
import java.io.*;

public class Index {
    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();
        File[] inputFiles = new File(args[0]).listFiles();
        File outputFiles = new File(args[1]);
        int pageLength = Integer.parseInt(args[2]);
        
        for(File file : inputFiles){
            
            TreeMap<String, TreeSet<Integer>> index = new TreeMap();
            int charCount = 0;
            int pageCount = 1;
	    Scanner input = new Scanner(file);
            while(input.hasNextLine()){
		String line = input.nextLine();
                String[] wordList = line.trim().split("\\s+");
                for(String word : wordList) {
		    if(word.length() == 0){
                    	continue;
		    }
                
                word = word.toLowerCase();
                charCount += word.length();
                if(charCount > pageLength){
                    pageCount += 1;
                    charCount = word.length();
                }
                if (index.get(word) == null) {
                    index.put(word, new TreeSet<>());
                }
                index.get(word).add(pageCount);
		}
            }
	    String name = file.getName();
	    name = name.replace(".txt", "_output.txt");
	    name = outputFiles + "/" + name;
	    File fileName = new File(name);
            PrintWriter output = new PrintWriter(fileName);
            for(String word : index.keySet()){
                ArrayList<String> pageNumbers = new ArrayList<>();
                for(Integer pageNumber : index.get(word)){
                    pageNumbers.add(pageNumber.toString());
                }
                output.println(word + " " + String.join(", ", pageNumbers));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " milliseconds");
     }
}
