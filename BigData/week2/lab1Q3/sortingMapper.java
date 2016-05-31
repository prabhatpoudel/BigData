package lab1Q3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import lab1Q3.keyValuePair;

public class sortingMapper {

	public List<keyValuePair<keyValuePair<String, String>,String>> valuesList = new ArrayList<>();
	public List<keyValuePair<keyValuePair<String, String>,String>> relativeFrequency = new ArrayList<>();
	public List<keyValuePair<String,String>> initialValue = new ArrayList<>();

	String filename;

	public sortingMapper(String fileLocation) {
		this.filename = fileLocation;
	}

	public List<keyValuePair<keyValuePair<String, String>,String>> getValuesList() {
		return valuesList;
	}

	public List<keyValuePair<keyValuePair<String, String>,String>> mapValues() {
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				Scanner line = new Scanner(scan.nextLine());
				List<String> numberArray = new ArrayList<>();
				while (line.hasNext()) {
					String val = line.next(); 
					
					numberArray.add(val);
					//System.out.println(numberArray);
				}
				for(int i =0; i<numberArray.size(); i=i+3)
				{
					valuesList.add(new keyValuePair<keyValuePair<String, String>,String> (new keyValuePair<String,String> (numberArray.get(i+1),numberArray.get(i)) ,numberArray.get(i+2)));
				}
				//System.out.println(valuesList);
			}

		} catch (FileNotFoundException e) {
		}
		return valuesList;
	}
/*
	public List<keyValuePair<keyValuePair<Integer, Integer>,Integer>> inMapperPairs() {
		mapValues();
//		System.out.println(valuesList);
		List<keyValuePair<keyValuePair<Integer, Integer>,Integer>> inMapperList = new ArrayList<>();

		for (keyValuePair<keyValuePair<Integer, Integer>,Integer> valuePair : valuesList) {
			if (inMapperList.size() < 1) {
				inMapperList.add(valuePair);
			} else {
				boolean matched = false;
				for (int i = 0; i < inMapperList.size(); i++) {

					if (inMapperList.get(i).getKey().getKey().equals(valuePair.getKey().getKey()) 
							&& inMapperList.get(i).getKey().getValue().equals(valuePair.getKey().getValue())) {
						//added for Relative Frequency
						int keyValue= inMapperList.get(i).getValue();
						int pairKeyValue= valuePair.getValue();
						inMapperList.get(i).setValue(keyValue+pairKeyValue);
						matched = true;
						break;
					}
				}
//				relativeFrequency.get(i).setValue(relativeFrequency.s+pairKeyValue);
				if (!matched) {
					inMapperList.add(valuePair);
				}
			}
		}
		return inMapperList;

	}
*/
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
