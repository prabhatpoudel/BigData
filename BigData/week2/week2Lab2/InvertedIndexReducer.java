package week2Lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import week2Lab2.keyValuePair;
import week2Lab2.groupByPair;

public class InvertedIndexReducer {

	public List<groupByPair<keyValuePair<String, String>, String>> groupKey(
			List<keyValuePair<keyValuePair<String, String>, String>> list) {
		List<groupByPair<keyValuePair<String, String>, String>> groupByPairs = new ArrayList<groupByPair<keyValuePair<String, String>, String>>();
		if (list != null) {

			InvertedIndexComparator<String, String, String> comparator = new InvertedIndexComparator<>();
			comparator.descSort(list);

			keyValuePair<String, String> prevKey = new keyValuePair<>();
			groupByPair<keyValuePair<String, String>, String> groupPair = new groupByPair<keyValuePair<String, String>, String>();
			for (keyValuePair<keyValuePair<String, String>, String> keyVal : list) {

				keyValuePair<String, String> key = keyVal.getKey();
				String val = keyVal.getValue();

				if (prevKey.getKey() != null) {
					if (prevKey.getKey() == (key.getKey()) && prevKey.getValue() == (key.getValue())) {
						List<String> values = groupPair.getValues();
						List<String> listValues = new ArrayList<>(values);
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

	public keyValuePair<keyValuePair<String, String>, String> reducePairs(
			groupByPair<keyValuePair<String, String>, String> groupByPair) {

		// keyValuePair<Integer,Integer> keyVal = new keyValuePair<>();
		String sum = "";
		if (groupByPair != null) {
			keyValuePair<String, String> key = groupByPair.getKey();
			for (String val : groupByPair.getValues()) {
				sum += val;
			}

			return new keyValuePair<>(key, sum);
		}

		return null;
	}

	public List<keyValuePair<keyValuePair<String, String>, String>> numberReduce(
			List<groupByPair<keyValuePair<String, String>, String>> pairs) {

		List<keyValuePair<keyValuePair<String, String>, String>> reducedList = new ArrayList<>();
		List<keyValuePair<keyValuePair<String, String>, String>> finalReducedList = new ArrayList<>();
		if (pairs != null) {			
			for (groupByPair<keyValuePair<String, String>, String> pair : pairs) {

				reducedList.add(reducePairs(pair));
			}
			return reducedList;
		}

		return null;
	}
}
