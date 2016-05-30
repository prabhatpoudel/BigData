package lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import lab5.keyValuePair;

public class pairMapper {

	public List<keyValuePair<keyValuePair<Integer, Integer>,Integer>> valuesList = new ArrayList<>();
	public List<keyValuePair<Integer,Integer>> initialValue = new ArrayList<>();

	String filename;

	public pairMapper(String fileLocation) {
		this.filename = fileLocation;
	}

	public List<keyValuePair<keyValuePair<Integer, Integer>,Integer>> getValuesList() {
		return valuesList;
	}

	public void mapValues() {
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				Scanner line = new Scanner(scan.nextLine());
				List<Integer> numberArray = new ArrayList<>();
				while (line.hasNext()) {
					int number = line.nextInt(); 
					
					numberArray.add(number);
					//System.out.println(numberArray);
				}
				for(int i =0; i<numberArray.size(); i++)
				{
					//System.out.println("     "+numberArray.get(i));
				   for(int j = i+1; j<numberArray.size(); j++)
				   {
					   
					   if(numberArray.get(i)==numberArray.get(j))
					   {
						   break;
					   }
					   //System.out.println(" I am value of J "+numberArray.get(j));
					   initialValue.add(new keyValuePair<Integer,Integer> (numberArray.get(i),numberArray.get(j)));
					   valuesList.add(new keyValuePair<keyValuePair<Integer, Integer>,Integer> (new keyValuePair<Integer,Integer> (numberArray.get(i),numberArray.get(j)) ,1));
				   }
				}
				//System.out.println(valuesList);
			}

		} catch (FileNotFoundException e) {
		}
		
	}

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
					
//						System.out.println("<"+inMapperList.get(i).getKey().getKey()+"><"+inMapperList.get(i).getKey().getValue()+">");
						int keyValue= inMapperList.get(i).getValue();
						int pairKeyValue= valuePair.getValue();
						inMapperList.get(i).setValue(keyValue+pairKeyValue);
						
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
