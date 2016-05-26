package Lab3;

import java.util.ArrayList;
import java.util.List;

import Lab3.groupByPair;

public class wordProcess {

	private mapper[] mapper;
	private reducer[] reducer;

	public wordProcess(int m, int r) {
		this.mapper = new mapper[m];
		this.reducer = new reducer[r];
	}

	public int getPartition(String key) {
		return (int) key.hashCode() % reducer.length;
	}

	public static void main(String args[]) {
		wordProcess wp = new wordProcess(3, 4);
		wp.processWord();
	}

	private void processWord() {
		List<List<keyValuePair<String, Integer>>> mappedPairs = new ArrayList<>();
		for (int i = 0; i < mapper.length; i++) {
			mapper[i] = new mapper(
					"C:/Users/DELL/Git_new/BigData/src/Lab3/words"
							+ i + ".txt");
			
			
			mapper[i].mapValues();
			// mappers[i].printPairs();

			List<keyValuePair<String, Integer>> list = mapper[i].valuesList;
			System.out.println("\n_____________Mapper " + i
					+ " Output_____________\n");
			mappedPairs.add(list);
			printListOfkeyValuePair(list);
		}

		// Now apply shuffle Sort

		List<List<keyValuePair<String, Integer>>> partitionPairs = shuffleSort(mappedPairs);

		// Combine the mapped and partioned pairs to in a single list
		List<List<groupByPair<String, Integer>>> reducerInputs = new ArrayList<List<groupByPair<String, Integer>>>();
		for (int i = 0; i < reducer.length; i++) {

			reducer reducer = new reducer();
			List<groupByPair<String, Integer>> reducerInput = reducer
					.groupKey(partitionPairs.get(i));
			System.out.println("\n_____________Reducer " + i
					+ " Input_____________\n");
			printListOfGroupByPair(reducerInput);

			reducerInputs.add(reducerInput);

		}

		// Reduce the pairs values
		for (int i = 0; i < reducer.length; i++) {

			reducer reducer = new reducer();
			List<groupByPair<String, Integer>> reducerInput = reducerInputs
					.get(i);
			System.out.println("\n_____________Reducer " + i
					+ " Output_____________\n");
			List<keyValuePair<String, Integer>> reducerOutput = reducer
					.wordReduce(reducerInput);
			printListOfkeyValuePair(reducerOutput);
		}
	}

	private List<List<keyValuePair<String, Integer>>> shuffleSort(
			List<List<keyValuePair<String, Integer>>> allMappedPairs) {
		List<List<keyValuePair<String, Integer>>> partitionPairs = new ArrayList<List<keyValuePair<String, Integer>>>();
		List<List<List<keyValuePair<String, Integer>>>> shuffledKeysList = new ArrayList<>();

		for (int i = 0; i < this.mapper.length; i++) {
			shuffledKeysList.add(new ArrayList<List<keyValuePair<String, Integer>>>());
		}
		for (int i = 0; i < this.mapper.length; i++) {
			for (int j = 0; j < this.reducer.length; j++) {
				shuffledKeysList.get(i).add(new ArrayList<keyValuePair<String, Integer>>());
			}
		}

		for (int i = 0; i < this.reducer.length; i++) {
			partitionPairs.add(new ArrayList<keyValuePair<String, Integer>>());
		}

		// shuffle step
		int i = 0;
		for (List<keyValuePair<String, Integer>> list : allMappedPairs) {
			for (keyValuePair<String, Integer> pair : list) {
				int partitionLevel = getPartition(pair.getKey());

				shuffledKeysList.get(i).get(partitionLevel).add(pair);
				partitionPairs.get(partitionLevel).add(pair);
			}

			i++;

		}
		comparator<String, Integer> comparator = new comparator<>();
		for (int j = 0; j < this.mapper.length; j++) {
			for (int k = 0; k < this.reducer.length; k++) {
				System.out.println("\n________Pairs sent from Mapper " + j + " to Reducer " + k + "__________\n");
				if (shuffledKeysList.size() > j && shuffledKeysList.get(j).size() > k) {
					List<keyValuePair<String, Integer>> partitionedList = shuffledKeysList.get(j).get(k);
					comparator.sort(partitionedList);
					for (keyValuePair<String, Integer> keyVal : partitionedList)
						System.out.println("<" + keyVal.getKey() + "," + keyVal.getValue() + ">");
				}
			}
		}

		return partitionPairs;
	}

	private void printListOfkeyValuePair(
			List<keyValuePair<String, Integer>> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (keyValuePair<String, Integer> word : list) {
				System.out.println("<" + word.getKey() + "," + word.getValue()
						+ ">");

			}
		}
	}

	private static void printListOfGroupByPair(
			List<groupByPair<String, Integer>> list) {
		if (list != null) {
			for (groupByPair<String, Integer> item : list) {
				System.out.println("<" + item.getKey() + "," + item.getValues()
						+ ">");

			}
		}
	}
	
	

}
