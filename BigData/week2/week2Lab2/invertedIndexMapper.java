package week2Lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import week2Lab2.keyValuePair;

public class invertedIndexMapper {

	public List<keyValuePair<keyValuePair<String, Integer>, Integer>> valuesList = new ArrayList<>();
	public List<keyValuePair<keyValuePair<String, Integer>, Integer>> relativeFrequency = new ArrayList<>();
	public List<keyValuePair<String, Integer>> initialValue = new ArrayList<>();

	String filename;

	public invertedIndexMapper(String fileLocation) {
		this.filename = fileLocation;
	}

	public List<keyValuePair<keyValuePair<String, Integer>, Integer>> getValuesList() {
		return valuesList;
	}

	public List<keyValuePair<keyValuePair<String, Integer>, Integer>> mapValues() {
		try {
			int lineNumber = 0;
			List<String> firstLineArray = null;
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				Scanner line = new Scanner(scan.nextLine());
				List<String> numberArray = new ArrayList<>();
				// System.out.println(lineNumber);
				if (lineNumber == 0) {
					firstLineArray = new ArrayList<>();
					while (line.hasNext()) {
						String val = line.next();
						firstLineArray.add(val);
						// System.out.println(firstLineArray);
					}
				} else {

					while (line.hasNext()) {
						String val = line.next();

						numberArray.add(val);
						// System.out.println(numberArray);
					}
					for (int i = 0; i < numberArray.size(); i++) {
						if (numberArray.get(i).matches("\"??([A-Za-z]+-??[A-Za-z]+|[A-Za-z])\"??\\.??")) {
							String word = numberArray.get(i);
							word = word.replace(".", "").toLowerCase();
							valuesList.add(new keyValuePair<keyValuePair<String, Integer>, Integer>(
									new keyValuePair<String, Integer>(word,  Integer.parseInt(firstLineArray.get(2))), 1));
						}
					}

				}
				lineNumber++;
				// System.out.println(valuesList);
			}

		} catch (FileNotFoundException e) {
		}
		return valuesList;
	}

	public List<keyValuePair<keyValuePair<String, Integer>, Integer>> inMapperPairs() {
		mapValues(); // System.out.println(valuesList);
		List<keyValuePair<keyValuePair<String, Integer>, Integer>> inMapperList = new ArrayList<>();

		for (keyValuePair<keyValuePair<String, Integer>, Integer> valuePair : valuesList) {
			if (inMapperList.size() < 1) {
				inMapperList.add(valuePair);
			} else {
				boolean matched = false;
				for (int i = 0; i < inMapperList.size(); i++) {

					if (inMapperList.get(i).getKey().getKey().equals(valuePair.getKey().getKey())) { 
						// added
						int keyValue= inMapperList.get(i).getValue();
						int pairKeyValue = valuePair.getValue();
						inMapperList.get(i).setValue(keyValue + pairKeyValue);
						matched = true;
						break;
					}
				} 
				if (!matched) {
					inMapperList.add(valuePair);
				}
			}
		}
		return inMapperList;

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
