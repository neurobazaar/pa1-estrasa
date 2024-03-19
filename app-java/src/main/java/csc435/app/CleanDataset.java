package csc435.app;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CleanDataset {
	public long dataset_size = 0;
	public double execution_time = 0.0;

	public void clean_dataset(String input_dir, String output_dir) {

		Path in_dir = Paths.get(input_dir);

		if (!Files.exists(in_dir)) {
			System.out.println("Input directory not found.");//("No such path.");
			return;
		}

		File[] file_list = new File(input_dir).listFiles();

		for (File file : file_list) {
			if (file.isDirectory()) {
				
				String out_path = output_dir+"\\"+file.getName();
				
				clean_dataset(file.getAbsolutePath(), out_path);

			} else {
				File in_file = new File(file.getAbsolutePath());
				
				File out_path = new File(output_dir);

				out_path.mkdirs();
				File out_file = new File(output_dir+"\\"+file.getName());
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(in_file));
					
					FileWriter writer = new FileWriter(out_file);
					
					int nextChar =  reader.read();
					int prevChar = nextChar;
					boolean dChain = false;
					while (nextChar  != -1) {
						this.dataset_size+=2;
						if((nextChar>=97&&nextChar<=122)||(nextChar>=65&&nextChar<=90)||(nextChar>=48&&nextChar<=57)){
							if(dChain==true) {
								writer.write((char)prevChar);
								dChain = false;
							}
							writer.write((char)nextChar);
						}
						if((nextChar==32)||(nextChar==10)||(nextChar==9)) {//9horizontaltab 10linefeed\n 13carriagereturn\r 32space
							prevChar = nextChar;
							dChain = true;
						}
						nextChar =  reader.read();
					}
					reader.close();
					writer.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("improper number of arguments");
			System.exit(1);
		}

		CleanDataset cleanDataset = new CleanDataset();

		long start = System.currentTimeMillis();

		cleanDataset.clean_dataset(args[0], args[1]);

		long end = System.currentTimeMillis();
		cleanDataset.execution_time = end - start;
		

		System.out.print("Finished cleaning " + cleanDataset.dataset_size/1048576.0 + " MiB of data");
		System.out.println(" in " + cleanDataset.execution_time + " miliseconds");
	}
}

        CleanDataset cleanDataset = new CleanDataset();

        cleanDataset.clean_dataset(args[0], args[1]);
        
        System.out.print("Finished cleaning " + cleanDataset.dataset_size + " MiB of data");
        System.out.println(" in " + cleanDataset.execution_time + " miliseconds");
    }
}
