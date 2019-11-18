import java.io.*;
import java.util.*;

public class CS245A1 {
	private static Storage data;
	//Method that reads the dictionary file to store all the read words.
	public static void readDictionary()
	{
		//It trys to read over the file using try-catch in case of an Exception
		try {
			File file = new File("english.0");
			Scanner scan = new Scanner(file);
			//It reads over the file as long as there are words to read and we insert them into the given storage
			while(scan.hasNext()) {
				data.insert(scan.next());
			}
			scan.close(); 
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	//Method that accepts a configuration file in order to determine the data structure (tree or trie)
	public static void acceptConfig() {
		//Here, it tries to read over the config file using try-catch in case of Exception
		try {
			Properties properties = new Properties();
			FileInputStream fis = new FileInputStream(new File("config.properties"));
			properties.load(fis);
			String storage = properties.getProperty("storage");
			if(storage == null) 
				throw new IllegalArgumentException("Property 'storage' not found");
			if(storage.equals("tree"))
				data = new BinarySearchTree();
			if(storage.equals("trie"))
				data = new Trie();
			if(data == null) 
				throw new IllegalArgumentException("Not valid storage configuration.");
		}
		//If an Exception occurs, it just prints a message
		catch (FileNotFoundException e) {
			System.out.println("Configuration file not found.");
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error reading the configuration file.");
			System.exit(1);
		}
	}
	//Method that receives an input and output files, reads line by line, and outputs the word (or suggestions) 
	public static void processFile(String inputpath, String outputpath)
	{
		try {
			//create both files
			File input = new File(inputpath);
			File output = new File(outputpath);
			//read over the input file and put the results in the output file
			FileWriter fw = new FileWriter(output); 
			Scanner scan = new Scanner(input);
			while(scan.hasNextLine()) {
				String word = scan.nextLine();
				//this looks for the word in the data structure.
				//If it exists, it writes it in the output file
				if(data.search(word)) {
					fw.write(word);
				} else {
					//If not, it gets the suggestions
					List<String> suggestions = data.suggest(word);
					if(suggestions.size() == 0) {
						fw.write("There are no suggestions for "+ word + ".");
					} else {
						String sugg = "";
						for(String s : suggestions) {
							sugg += s +" ";
						}
						fw.write(sugg);
					}
				}
				fw.write("\r\n"); //this adds a new line
			}
			fw.close(); 
			scan.close(); 
		} catch (Exception e) {
			//If any exception, an error is printed 
			System.out.println("Error reading or writing the files");
		}
		
	}
	public static void main(String[] args)
	{
		//First, it reads for the config file and the dictionary to store it on the correct Storage object
		acceptConfig();
		readDictionary();
		//Then, it reads the input file and it prints the word (if contained on the file) or the suggestions
		//(if not contained) in the output file. These files are given by command line arguments
		processFile(args[0], args[1]);
		
	}
}
