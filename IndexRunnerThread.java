import java.util.*;
import java.io.*;
public class IndexRunnerThread extends Thread{
    private File outputFolder;
    private File file;
    private int pageLength;
    
    public IndexRunnerThread(File file, File outputFolder, int pageLength){
        this.file = file;
        this.outputFolder = outputFolder;
        this.pageLength = pageLength;
    }
    
    private Scanner S(File file){
        try{
            return new Scanner(file);
        } catch (FileNotFoundException e){
            return null;
        }
    }
	
    private PrintWriter PrintW(File file){
        try {
            return new PrintWriter(file);
        } catch (IOException ioe){
            return null;
        }
    }
    
    private FileWriter FileW(File name){
        try{
            return new FileWriter(name);
        } catch (IOException ioe){
            return null;
        }
    }
	
	public void run(){
		Scanner s = S(file);
            try{
                // Create TreeMap with string and integer
                Map<String, TreeSet<Integer>> index = new TreeMap<>();
                // characterCount keeps track of pagelength, pageCount keeps track of page number
                int characterCount = 0;
                int pageCount = 1;
                // While there's more text, split on whitespace and put into words array
                while(s.hasNext()){
                    String line = s.nextLine();
                    String[] words = line.trim().split("\\s+");
                    // for each word in words, make lowercase, add length to characterCount
                    for(String word : words){
                        word = word.toLowerCase();
                        characterCount += word.length();
                        // if page length is reached, increase page count, start from length of current word
                        if(characterCount > pageLength){
                            pageCount += 1;
                            characterCount = word.length();
                        }
                        // if word isn't in index yet, add it and the page
                        if(index.get(word) == null){
                            index.put(word, new TreeSet<>());
                        }
                        index.get(word).add(pageCount);
                    }
                }
                // Get file name, convert it to specified name and attach name of output folder

                String name = file.getName();
                name = name.replace(".txt", "_output.txt");
                name = outputFolder + "/" + name;
                File fileName = new File(name);
                //FileWriter writeName = PrintW(fileName);
                PrintWriter printWrite = PrintW(fileName);
                // Write contents of TreeMap to file
                for (String word : index.keySet()){
                    ArrayList<String> number = new ArrayList<>();
                    for(Integer pageNumber : index.get(word)){
                        number.add(pageNumber.toString());
                    }
                    printWrite.println(word + " " + String.join(", ", number));
                }
                
            }finally{
                if (s != null) {
                    s.close();
                }
            } 
        }
	}