package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class stripsMapper {

	public List<keyValuePair<Integer, HashMap<Integer, Integer>>> valuesList = new ArrayList<>();
	// public List<keyValuePair<Integer,Integer>> initialValue = new
	// ArrayList<>();

	String filename;

	public stripsMapper(String fileLocation) {
		this.filename = fileLocation;
	}

	public List<keyValuePair<Integer, HashMap<Integer, Integer>>> getValuesList() {
		return valuesList;
	}

	public List<keyValuePair<Integer, HashMap<Integer, Integer>>> mapValues() {
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				Scanner line = new Scanner(scan.nextLine());
				List<Integer> numberArray = new ArrayList<>();
				while (line.hasNext()) {
					int number = line.nextInt();

					numberArray.add(number);
					// System.out.println(numberArray);
				}
				for (int i = 0; i < numberArray.size(); i++) {
					HashMap<Integer, Integer> neighbourValue = new HashMap<>();
					int count = 0;
					for (int j = i + 1; j < numberArray.size(); j++) {
						if (numberArray.get(i) == numberArray.get(j)) {
							break;
						}
						if (neighbourValue.get(numberArray.get(j)) == null) {
							count = 1;
						} else {
							count = neighbourValue.get(numberArray.get(j)) + 1;
						}
						neighbourValue.put(numberArray.get(j), count);

					}
					if (!neighbourValue.isEmpty()) {
						valuesList.add(new keyValuePair<Integer, HashMap<Integer, Integer>>(numberArray.get(i),
								neighbourValue));

					}
				}
				// System.out.println(valuesList);
			}

		} catch (FileNotFoundException e) {
		}
		return valuesList;

	}

	public void printSortedList() {
		System.out.println("-----------Sorted List-----------");
		valuesList.stream().sorted().forEach(System.out::println);
		System.out.println("---------------------------------");
	}

	public void printUnSortedList() {
		System.out.println("-----------UnSorted List-----------");
		valuesList.stream().forEach(System.out::println);
		System.out.println("---------------------------------");
	}

}
