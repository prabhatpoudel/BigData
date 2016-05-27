package lab4Q2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lab4Q2.keyValuePair;
import lab4Q2.groupByPair;

public class reducer {
		
	public List<groupByPair<String, Integer>> groupKey(List<keyValuePair<String, Integer>> list){
		
		List<groupByPair<String, Integer>> groupByPairs = new ArrayList<groupByPair<String, Integer>>();
		if(list != null){
			//sort
			comparator<String, Integer> comparator = new comparator<>();
			comparator.sort(list);
			
			String prevKey = "";
			groupByPair<String, Integer> groupPair = new groupByPair<String, Integer>();
			for(keyValuePair<String, Integer> keyVal : list){
				
				String key = keyVal.getKey();
				int val = keyVal.getValue();
				
				if(prevKey.toLowerCase().equals(key.toLowerCase())){
					List<Integer> values = groupPair.getValues();
					List<Integer> listValues = new ArrayList<>(values);
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
