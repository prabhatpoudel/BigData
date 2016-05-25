package Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Question1 {

	public static List<keyValuePair<String, Integer>> valuesList = new ArrayList<keyValuePair<String, Integer>>();
	public static List<groupByPair> groupByPairList = new ArrayList<groupByPair>();

	public static void main(String args[]) {
		//Mapping the Word and sorting it 
		mapValues();
		//Grouping the Same Key and adding the values in the List
		groupKey();
		//Printing the Values
		for (groupByPair groupWord : groupByPairList) {
			System.out.println("< " + groupWord.getKey() + " , "
					+ groupWord.getValues().stream().mapToInt(i -> i.intValue()).sum() + " >");
		}
	}

	private static void mapValues() {
		try {
			Scanner scan = new Scanner(new File("C:/WorkSpace/BigData/src/Lab2/words.txt"));
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
			Collections.sort(valuesList, (val1, val2) -> val1.getKey().compareToIgnoreCase(val2.getKey()));
			// System.out.print(valuesList );
		} catch (FileNotFoundException e) {
		}
	}

	public static void groupKey() {
		for (int i = 0; i < valuesList.size(); i++) {
			String key = valuesList.get(i).getKey();
			groupByPair group = new groupByPair(key);

			// Searching for the Same Key if exist then add the value 1 in the
			// list
			while (i < valuesList.size() - 1 && valuesList.get(i + 1).getKey().equals(key))
			// if(valuesList.get(i).getKey().equals(key))
			{
				group.addValue();
				i++;
			}
			groupByPairList.add(group);
		}
	}
}
