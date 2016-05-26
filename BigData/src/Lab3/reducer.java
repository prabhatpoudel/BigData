package Lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Lab3.keyValuePair;
import Lab3.groupByPair;

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
	
	public keyValuePair<String, Integer> reducePairs(groupByPair<String, Integer> groupByPair){
		
		keyValuePair<String, Integer> keyVal = new keyValuePair<>();
		if(groupByPair != null){
			String key = groupByPair.getKey();
			int sum = 0;
			for(Integer val : groupByPair.getValues()){
				sum += val;
			}
			
			return new keyValuePair<>(key, sum);
		}
		
		return null;
	}

	public List<keyValuePair<String, Integer>> wordReduce(List<groupByPair<String, Integer>> pairs){
		
		List<keyValuePair<String, Integer>> reducedList = new ArrayList<>();
		if(pairs != null){
		
			for(groupByPair<String, Integer> pair : pairs){
				
				reducedList.add(reducePairs(pair));
			}
			
			return reducedList;
		}
		
		return null;
	}
	
}
