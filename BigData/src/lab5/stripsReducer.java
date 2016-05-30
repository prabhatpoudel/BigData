package lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import lab5.keyValuePair;
import lab5.groupByPair;

public class stripsReducer {

	public List<groupByPair<Integer, HashMap<Integer, Integer>>> groupKey(
			List<keyValuePair<Integer, HashMap<Integer, Integer>>> list) {
		List<groupByPair<Integer, HashMap<Integer, Integer>>> groupByPairs = new ArrayList<groupByPair<Integer, HashMap<Integer, Integer>>>();
		if (list != null) {

			stripsComparator<Integer, HashMap<Integer, Integer>> comparator = new stripsComparator<>();
			comparator.sort(list);

			int prevKey = 0;
			groupByPair<Integer, HashMap<Integer, Integer>> groupPair = new groupByPair<Integer, HashMap<Integer, Integer>>();
			for (keyValuePair<Integer, HashMap<Integer, Integer>> keyVal : list) {

				int key = keyVal.getKey();
				HashMap<Integer, Integer> val = keyVal.getValue();

				if (prevKey == key) {
					List<HashMap<Integer, Integer>> values = groupPair.getValues();
					List<HashMap<Integer, Integer>> listValues = new ArrayList<>(values);
					listValues.add(val);
					groupPair.setValues(listValues);
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

	public keyValuePair<Integer, HashMap<Integer, Integer>> reducePairs(
			groupByPair<Integer, HashMap<Integer, Integer>> groupByPair) {

		// keyValuePair<Integer,Integer> keyVal = new keyValuePair<>();
		if (groupByPair != null) {
			int key = groupByPair.getKey();
			HashMap<Integer, Integer> finalHashValue = new HashMap<>();

			for (HashMap<Integer, Integer> val : groupByPair.getValues()) {
				if (finalHashValue.isEmpty()) {
					finalHashValue.putAll(val);
				} else {
					for (Entry<Integer, Integer> item : val.entrySet()) {
						// System.out.println("****Checking Item********");
						// System.out.println(item);
						// System.out.println("Key "+item.getKey());
						// System.out.println("Key "+item.getValue());
						// System.out.println("finalHashValue Key
						// "+finalHashValue.get(item.getKey()));

						if (finalHashValue.get(item.getKey()) == null) {
							finalHashValue.put(item.getKey(), item.getValue());
						} else {
							finalHashValue.put(item.getKey(), finalHashValue.get(item.getKey()) + item.getValue());
						}
						// System.out.println("hashValue "+finalHashValue);
					}
				}

				// System.out.println(" End ");
			}

			return new keyValuePair<>(key, finalHashValue);
		}

		return null;
	}

	public List<keyValuePair<Integer, HashMap<Integer, Integer>>> numberReduce(
			List<groupByPair<Integer, HashMap<Integer, Integer>>> pairs) {

		List<keyValuePair<Integer, HashMap<Integer, Integer>>> reducedList = new ArrayList<>();
		if (pairs != null) {

			for (groupByPair<Integer, HashMap<Integer, Integer>> pair : pairs) {
				// System.out.println(pairs);

				reducedList.add(reducePairs(pair));
			}

			return reducedList;
		}

		return null;
	}
}