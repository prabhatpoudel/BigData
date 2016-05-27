package lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lab5.keyValuePair;
import lab5.groupByPair;

public class reducer {
		
	public List<groupByPair<Character,keyValuePair<Integer, Integer>>> groupKey(List<keyValuePair<Character,keyValuePair<Integer, Integer>>> list){
		
		List<groupByPair<Character,keyValuePair<Integer, Integer>>> groupByPairs = new ArrayList<groupByPair<Character,keyValuePair<Integer, Integer>>>();
		if(list != null){
			//sort
			comparator<Character,keyValuePair<Integer, Integer>> comparator = new comparator<>();
			comparator.sort(list);
			
			Character prevKey = Character.MIN_VALUE;
			groupByPair<Character,keyValuePair<Integer, Integer>> groupPair = new groupByPair<Character,keyValuePair<Integer, Integer>>();
			for(keyValuePair<Character,keyValuePair<Integer, Integer>> keyVal : list){
				
				Character key = keyVal.getKey();
				keyValuePair<Integer,Integer> val = keyVal.getValue();
				
				if(prevKey.toString().toLowerCase().equals(key.toString().toLowerCase())){
					List<keyValuePair<Integer,Integer>> values = groupPair.getValues();
					List<keyValuePair<Integer,Integer>> listValues = new ArrayList<>(values);
					listValues.add(val);
					groupPair.setValues(listValues);
				}
				
				else{
					
					if(groupPair.getKey() != null)
						groupByPairs.add(groupPair);
					groupPair = new groupByPair<>();
					groupPair.setKey(key);
					groupPair.setValues(Arrays.asList(val));
				}
				
				prevKey = key;
			}
			if(groupPair.getKey() != null)
				groupByPairs.add(groupPair);
			
			return groupByPairs;
		}
		
		return null;
	}
	
	public keyValuePair<Character, Double> reducePairs(groupByPair<Character, keyValuePair<Integer, Integer>> groupByPair){
		
		keyValuePair<Character, keyValuePair<Integer, Integer>> keyVal = new keyValuePair<>();
		if(groupByPair != null){
			Character key = groupByPair.getKey();
			int sum = 0;
			int count =0;
			for(keyValuePair<Integer, Integer> val: groupByPair.getValues()){
				sum += val.getKey();
				count+=val.getValue();
			}
			
			return new keyValuePair<>(key, (double) sum/count);
		}
		
		return null;
	}

	public List<keyValuePair<Character, Double>> wordReduce(List<groupByPair<Character, keyValuePair<Integer,Integer>>> pairs){
		
		List<keyValuePair<Character, Double>> reducedList = new ArrayList<>();
		if(pairs != null){
		
			for(groupByPair<Character, keyValuePair<Integer,Integer>> pair : pairs){
				
				reducedList.add(reducePairs(pair));
			}
			
			return reducedList;
		}
		
		return null;
	}
	
}
