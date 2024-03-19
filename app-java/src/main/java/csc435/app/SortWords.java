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
import java.util.TreeMap;
import java.util.Map;
import java.util.Comparator;

public class SortWords
{
    public long num_words = 0;
    public double execution_time = 0.0;

    public void sort_words(String input_dir, String output_dir)
    {
		Path in_dir = Paths.get(input_dir);
		
		if (!Files.exists(in_dir)) {
			System.out.println("Input directory not found.");
			return;
		}
		
		File[] file_list = new File(input_dir).listFiles();

		for (File file : file_list) {
			if (file.isDirectory()) {
				String out_path = output_dir+"\\"+file.getName();
				sort_words(file.getAbsolutePath(), out_path);

			} else {
				File in_file = new File(file.getAbsolutePath());
				File out_path = new File(output_dir);
				out_path.mkdirs();
				File out_file = new File(output_dir+"\\"+file.getName());
				
				HashMap<String,Integer> wordList = new HashMap<String,Integer>();
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(in_file));

					int nextChar = reader.read();
					String word = "";
					String val = "";
					
					while (nextChar  != -1) {
						while((nextChar>=97&&nextChar<=122)||(nextChar>=65&&nextChar<=90)||(nextChar>=48&&nextChar<=57)){
							word+=Character.toString(nextChar);
							nextChar =  reader.read();
						}
						nextChar =  reader.read();
						while((nextChar>=48&&nextChar<=57)) {
							val+=Character.toString(nextChar);
							nextChar = reader.read();
						}
						wordList.put(word,Integer.parseInt(val));
						this.num_words++;
						word = "";
						val = "";
						nextChar = reader.read();
					}
					
					reader.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Map<String, Integer> sortedList = new TreeMap<String, Integer>(new Comparator<String>() {
					//@override
					public int compare(String word1, String word2) {
						int comp = wordList.get(word2) - wordList.get(word1);
						return comp == 0 ? 1 : comp;
					}
				});
				sortedList.putAll(wordList);
				
				FileWriter writer;
				try {
					writer = new FileWriter(out_file);
					for(Map.Entry<String, Integer> entry : sortedList.entrySet()) {
						writer.write(entry.getKey() + " " + entry.getValue()+"\n");
					}
					writer.close();
				} catch (IOException e) {
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

        SortWords sortWords = new SortWords();

        long start = System.currentTimeMillis();
        
        sortWords.sort_words(args[0], args[1]);
        
        long end = System.currentTimeMillis();
		sortWords.execution_time = end - start;
        
        System.out.print("Finished sorting " + sortWords.num_words + " words");
        System.out.println(" in " + sortWords.execution_time + " miliseconds");
    }
}
