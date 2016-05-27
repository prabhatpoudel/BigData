package lab4Q2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import lab4Q2.keyValuePair;

public class mapper {

	public List<keyValuePair<Character,keyValuePair<Integer, Integer>>> valuesList = new ArrayList<>();

	String filename;

	public mapper(String fileLocation) {
		this.filename = fileLocation;
	}

	public List<keyValuePair<Character,keyValuePair<Integer, Integer>>> getValuesList() {
		return valuesList;
	}

	public void mapValues() {
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				Scanner line = new Scanner(scan.nextLine());
				while (line.hasNext()) {
					String word = line.next().replace("\"", "").replace("'", "").trim().toLowerCase();
					String[] words = word.split("-");
					for (String token : words) {

						// check for valid word
						if (Pattern.matches("([a-zA-Z]+\\.)||([a-zA-Z]+)", token)) {
							token = token.replace(".", "");
							valuesList.add(new keyValuePair<Character,keyValuePair<Integer, Integer>>(token.charAt(0),new keyValuePair<Integer,Integer> (token.length(), 1)));
						}
					}

				}
			}

		} catch (FileNotFoundException e) {
		}
		
	}

	public List<keyValuePair<Character,keyValuePair<Integer, Integer>>> inMapperPairs() {
		mapValues();
		List<keyValuePair<Character,keyValuePair<Integer, Integer>>> inMapperList = new ArrayList<>();

		for (keyValuePair<Character,keyValuePair<Integer, Integer>> valuePair : valuesList) {
			if (inMapperList.size() < 1) {
				inMapperList.add(valuePair);
			} else {
				boolean matched = false;
				for (int i = 0; i < inMapperList.size(); i++) {

					if (inMapperList.get(i).getKey().equals(valuePair.getKey())) {
					
						keyValuePair<Integer,Integer> keyValue= inMapperList.get(i).getValue();
						keyValuePair<Integer,Integer> pairKeyValue= valuePair.getValue();
						
						inMapperList.get(i).setValue(new keyValuePair<Integer,Integer>(keyValue.getKey()+pairKeyValue.getKey(),keyValue.getValue()+pairKeyValue.getValue()));
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
