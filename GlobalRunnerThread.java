import java.util.*;
import java.io.*;

public class GlobalRunnerThread extends Thread{
    private File file;
    private int pageCharacterLength;

    public GlobalRunnerThread(File file, int pageCharacterLength) {
        this.file = file;
        this.pageCharacterLength = pageCharacterLength;
    }
    private Scanner getScanner(File file){
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    public void run() {
        TreeMap<String, TreeSet<Integer>> index = new TreeMap<>();
        int characterCount = 0;
        int pageCount = 1;
        Scanner input = getScanner(file);
        while (input.hasNextLine()) {
            String line = input.nextLine();                
            String[] wordList = line.split("\\s+");
            for (String word : wordList) {
                if (word.length() == 0) {
                    continue;
                }
                word = word.toLowerCase();
                characterCount += word.length();
                if (characterCount > pageCharacterLength) {
                    pageCount += 1;
                    characterCount = word.length();
                }
                if (index.get(word) == null) {
                    index.put(word, new TreeSet<>());
                }
                index.get(word).add(pageCount);
            }
        }
        TreeMap<String, String> indexFormatted = new TreeMap<>();
        for (String word : index.keySet()) {
            ArrayList<String> pgNums = new ArrayList<>();
            for (Integer pgNum : index.get(word)) {
               pgNums.add(pgNum.toString()); 
            }
            indexFormatted.put(word, String.join(":", pgNums));
        }
        GlobalRunner.addIndex(file.getName(), indexFormatted);
    }
}