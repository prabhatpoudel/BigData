package lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lab1.keyValuePair;
import lab1.groupByPair;

public class pairReducer {

	public List<groupByPair<keyValuePair<Integer, Integer>, Integer>> groupKey(
			List<keyValuePair<keyValuePair<Integer, Integer>, Integer>> list) {
		List<groupByPair<keyValuePair<Integer, Integer>, Integer>> groupByPairs = new ArrayList<groupByPair<keyValuePair<Integer, Integer>, Integer>>();
		if (list != null) {

			pariComparator<Integer, Integer, Integer> comparator = new pariComparator<>();
			comparator.sort(list);

			keyValuePair<Integer, Integer> prevKey = new keyValuePair<>();
			groupByPair<keyValuePair<Integer, Integer>, Integer> groupPair = new groupByPair<keyValuePair<Integer, Integer>, Integer>();
			for (keyValuePair<keyValuePair<Integer, Integer>, Integer> keyVal : list) {

				keyValuePair<Integer, Integer> key = keyVal.getKey();
				int val = keyVal.getValue();

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

	public keyValuePair<keyValuePair<Integer, Integer>, Integer> reducePairs(
			groupByPair<keyValuePair<Integer, Integer>, Integer> groupByPair) {

		// keyValuePair<Integer,Integer> keyVal = new keyValuePair<>();
		int sum = 0;
		if (groupByPair != null) {
			keyValuePair<Integer, Integer> key = groupByPair.getKey();
			for (int val : groupByPair.getValues()) {
				sum += val;
			}

			return new keyValuePair<>(key, sum);
		}

		return null;
	}

	public List<keyValuePair<keyValuePair<Integer, Integer>, Double>> numberReduce(
			List<groupByPair<keyValuePair<Integer, Integer>, Integer>> pairs) {

		List<keyValuePair<keyValuePair<Integer, Integer>, Integer>> reducedList = new ArrayList<>();
		List<keyValuePair<keyValuePair<Integer, Integer>, Double>> finalReducedList = new ArrayList<>();
		if (pairs != null) {
			
			int sum=0;
			int prevKey=0;

			for (groupByPair<keyValuePair<Integer, Integer>, Integer> pair : pairs) {

				reducedList.add(reducePairs(pair));
			}
			
			for(keyValuePair<keyValuePair<Integer,Integer>,Integer> finalPair: reducedList)
			{
				if(finalPair.getKey().getKey() != prevKey)
				{
					prevKey=0;
					sum=0;
				}
				if(prevKey ==0)
				{
					sum=finalPair.getValue();
					prevKey=finalPair.getKey().getKey();
				}
				if(finalPair.getKey().getKey()==prevKey)
				{
					if(finalPair.getKey().getValue() !=0)
					{
						finalReducedList.add(new keyValuePair<keyValuePair<Integer,Integer>,Double> (finalPair.getKey(), (double) finalPair.getValue()/ sum));
					}
				}
			}

			return finalReducedList;
		}

		return null;
	}
}
