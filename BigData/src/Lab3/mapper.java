package Lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import Lab3.keyValuePair;

public class mapper {
	public List<keyValuePair<String, Integer>> valuesList = new ArrayList<keyValuePair<String, Integer>>();

	String filename;

	public mapper(String fileLocation) {
		this.filename = fileLocation;
	}

	public List<keyValuePair<String, Integer>> getValuesList() {
		return valuesList;
	}

	public List<keyValuePair<String, Integer>> mapValues() {
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				Scanner line = new Scanner(scan.nextLine());
				while (line.hasNext()) {
					String word = line.next().replace('"', '\0').trim().toLowerCase();
					if (word.matches("\"??([A-Za-z]+-??[A-Za-z]+|[A-Za-z])\"??\\.??")) {
						valuesList.add(new keyValuePair<String, Integer>(word, 1));
					}
				}
			}
			// sorting
//			Collections.sort(valuesList, (val1, val2) -> val1.getKey().compareToIgnoreCase(val2.getKey()));
			// System.out.print(valuesList );

		} catch (FileNotFoundException e) {
		}
		return valuesList;
	}
	
	public void printSortedList(){
		System.out.println("-----------Sorted List-----------");
		valuesList.stream().sorted().forEach(System.out::println);
		System.out.println("---------------------------------");
	}
	
	public void printUnSortedList(){
		System.out.println("-----------UnSorted List-----------");
		valuesList.stream().forEach(System.out::println);
		System.out.println("---------------------------------");
	}

}
