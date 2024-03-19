package csc435.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
//import java.util.Scanner;

public class CountWords
{
    public long dataset_size = 0;
    public double execution_time = 0.0;

    public void count_words(String input_dir, String output_dir)
    {
        // TO-DO implement count words logic
		Path in_dir = Paths.get(input_dir);
		
		if (!Files.exists(in_dir)) {
			System.out.println("Input directory not found.");
			return;
		}

		File[] file_list = new File(input_dir).listFiles();

		for (File file : file_list) {
			if (file.isDirectory()) {
				String out_path = output_dir+"\\"+file.getName();
				count_words(file.getAbsolutePath(), out_path);

			} else {
				File in_file = new File(file.getAbsolutePath());
				File out_path = new File(output_dir);
				out_path.mkdirs();
				File out_file = new File(output_dir+"\\"+file.getName());
				
				HashMap<String,Integer> wordList = new HashMap<String,Integer>();
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(in_file));
					FileWriter writer = new FileWriter(out_file);
					int nextChar = reader.read();
					String word = "";
					
					while (nextChar  != -1) {
						this.dataset_size+=2;
						while((nextChar>=97&&nextChar<=122)||(nextChar>=65&&nextChar<=90)||(nextChar>=48&&nextChar<=57)){
							word+=Character.toString(nextChar);
							//this.dataset_size+=2;
							nextChar =  reader.read();
						}
						if (wordList.containsKey(word)){
							int val = wordList.get(word);
							wordList.put(word, val + 1);
						}else {
							wordList.put(word, 1);
						}
						word = "";
						
						nextChar =  reader.read();
					}
					reader.close();
					
					for(String key : wordList.keySet()) {
						writer.write(key+ " "+wordList.get(key)+"\n");
					}
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
			}
		}
    }

    public static void main(String[] args)
    {
        if (args.length != 2) {
            System.err.println("improper number of arguments");
            System.exit(1);
        }

        CountWords countWords = new CountWords();

        long start = System.currentTimeMillis();
        
        countWords.count_words(args[0], args[1]);
        
        long end = System.currentTimeMillis();
		countWords.execution_time = end - start;
        
        System.out.print("Finished counting " + countWords.dataset_size/1048576.0 + " MiB of words");
        System.out.println(" in " + countWords.execution_time + " miliseconds");
    }
}
