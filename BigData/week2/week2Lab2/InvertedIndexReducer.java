package week2Lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import week2Lab2.keyValuePair;
import week2Lab2.groupByPair;

public class InvertedIndexReducer {

	public List<groupByPair<keyValuePair<String, Integer>, Integer>> groupKey(
			List<keyValuePair<keyValuePair<String, Integer>, Integer>> list) {
		List<groupByPair<keyValuePair<String, Integer>, Integer>> groupByPairs = new ArrayList<groupByPair<keyValuePair<String, Integer>, Integer>>();
		if (list != null) {

			InvertedIndexComparator<String, Integer, Integer> comparator = new InvertedIndexComparator<>();
			comparator.descSort(list);

			keyValuePair<String, Integer> prevKey = new keyValuePair<>();
			groupByPair<keyValuePair<String, Integer>, Integer> groupPair = new groupByPair<keyValuePair<String, Integer>, Integer>();
			for (keyValuePair<keyValuePair<String, Integer>, Integer> keyVal : list) {

				keyValuePair<String, Integer> key = keyVal.getKey();
				Integer val = keyVal.getValue();

				if (prevKey.getKey() != null) {
					if (prevKey.getKey() == (key.getKey()) && prevKey.getValue() == (key.getValue())) {
						List<Integer> values = groupPair.getValues();
						List<Integer> listValues = new ArrayList<>(values);
						listValues.add(val);
						groupPair.setValues(listValues);
					}

					else {

						if (groupPair.getKey() != null)
							groupByPairs.add(groupPair);
						groupPair = new groupByPair<>();
						groupPair.setKey(key);
						groupPair.setValues(Arrays.asList(val));
					}
				} else {
					if (groupPair.getKey() != null)
						groupByPairs.add(groupPair);
					groupPair = new groupByPair<>();
					groupPair.setKey(key);
					groupPair.setValues(Arrays.asList(val));
				}
				prevKey = key;
			}
			if (groupPair.getKey() != null)
				groupByPairs.add(groupPair);

			return groupByPairs;
		}

		return null;
	}

	public keyValuePair<String,keyValuePair<Integer,Integer>> reducePairs(
			groupByPair<keyValuePair<String, Integer>, Integer> groupByPair) {

		// keyValuePair<Integer,Integer> keyVal = new keyValuePair<>();
		int sum = 0;
		if (groupByPair != null) {
			keyValuePair<String, Integer> key = groupByPair.getKey();
			for (int val : groupByPair.getValues()) {
				sum += val;
			}

			return new keyValuePair<>(key.getKey(), new keyValuePair<Integer,Integer> (key.getValue(),sum));
		}

		return null;
	}

	public List<keyValuePair<String, List<keyValuePair<Integer, Integer>>>> numberReduce(
			List<groupByPair<keyValuePair<String, Integer>, Integer>> pairs) {

		List<keyValuePair<String, List<keyValuePair<Integer, Integer>>>> reducedList = new ArrayList<>();
		List<keyValuePair<keyValuePair<String, Integer>, Integer>> finalReducedList = new ArrayList<>();
		if (pairs != null) {
			
			String prevKey="";
			
			for (groupByPair<keyValuePair<String, Integer>, Integer> pair : pairs) {
				keyValuePair<String, keyValuePair<Integer, Integer>> reducePairs = reducePairs(pair);
				
				if(prevKey.equals(reducePairs.getKey()))
				{
					List<keyValuePair<Integer, Integer>> collect = new ArrayList<>(reducedList.get(reducedList.size() - 1).getValue());
					collect.add(reducePairs.getValue());
					reducedList.get(reducedList.size() - 1).setValue(collect);
				}
				else
				{
					reducedList.add(new keyValuePair<>(reducePairs.getKey(), Arrays.asList(
					new keyValuePair<>(reducePairs.getValue().getKey(), reducePairs.getValue().getValue()))));
				
				}
				prevKey=reducePairs.getKey();
			}
			return reducedList;
		}

		return null;
	}
}
