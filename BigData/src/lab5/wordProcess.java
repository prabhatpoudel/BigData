package lab5;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lab5.groupByPair;

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
		wordProcess wp = new wordProcess(4, 3);
		wp.processWord();
	}

	private void processWord() {
        
		final String dir = System.getProperty("user.dir");
        //System.out.println("current dir = " + dir);
        
		List<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>> mappedPairs = new ArrayList<>();
		for (int i = 0; i < mapper.length; i++) {
			mapper[i] = new mapper(dir+
					"/src/Lab5/words"
							+ i + ".txt");
			
			
//			mapper[i].mapValues();
			// mappers[i].printPairs();
			mapper[i].mapValues();
//			List<keyValuePair<keyValuePair<Integer, Integer>,Integer>> list = mapper[i].inMapperPairs();
			System.out.println("\n_____________Mapper " + i
					+ " Output_____________\n");
//			mappedPairs.add(list);
//			printListOfkeyValuePair(list);
		}
/*
		// Now apply shuffle Sort

		List<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>> partitionPairs = shuffleSort(mappedPairs);
	
		// Combine the mapped and partioned pairs to in a single list
		List<List<groupByPair<Character,keyValuePair<Integer, Integer>>>> reducerInputs = new ArrayList<List<groupByPair<Character,keyValuePair<Integer, Integer>>>>();
		for (int i = 0; i < reducer.length; i++) {

			reducer reducer = new reducer();
			List<groupByPair<Character,keyValuePair<Integer, Integer>>> reducerInput = reducer
					.groupKey(partitionPairs.get(i));
			System.out.println("\n_____________Reducer " + i
					+ " Input_____________\n");
			printListOfGroupByPair(reducerInput);

			reducerInputs.add(reducerInput);

		}

		// Reduce the pairs values
		for (int i = 0; i < reducer.length; i++) {

			reducer reducer = new reducer();
			List<groupByPair<Character,keyValuePair<Integer, Integer>>> reducerInput = reducerInputs
					.get(i);
			System.out.println("\n_____________Reducer " + i
					+ " Output_____________\n");
			List<keyValuePair<Character, Double>> reducerOutput = reducer
					.wordReduce(reducerInput);
			printKeyValue(reducerOutput);
		}*/
	}



	private List<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>> shuffleSort(
			List<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>> allMappedPairs) {
		List<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>> partitionPairs = new ArrayList<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>>();
		List<List<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>>> shuffledKeysList = new ArrayList<>();

		for (int i = 0; i < this.mapper.length; i++) {
			shuffledKeysList.add(new ArrayList<List<keyValuePair<Character,keyValuePair<Integer, Integer>>>>());
		}
		for (int i = 0; i < this.mapper.length; i++) {
			for (int j = 0; j < this.reducer.length; j++) {
				shuffledKeysList.get(i).add(new ArrayList<keyValuePair<Character,keyValuePair<Integer, Integer>>>());
			}
		}

		for (int i = 0; i < this.reducer.length; i++) {
			partitionPairs.add(new ArrayList<keyValuePair<Character,keyValuePair<Integer, Integer>>>());
		}

		// shuffle step
		int i = 0;
		for (List<keyValuePair<Character,keyValuePair<Integer, Integer>>> list : allMappedPairs) {
			for (keyValuePair<Character,keyValuePair<Integer, Integer>> pair : list) {
				int partitionLevel = getPartition(pair.getKey().toString());

				shuffledKeysList.get(i).get(partitionLevel).add(pair);
				partitionPairs.get(partitionLevel).add(pair);
			}

			i++;

		}
		comparator<Character,keyValuePair<Integer, Integer>> comparator = new comparator<>();
		for (int j = 0; j < this.mapper.length; j++) {
			for (int k = 0; k < this.reducer.length; k++) {
				System.out.println("\n________Pairs sent from Mapper " + j + " to Reducer " + k + "__________\n");
				if (shuffledKeysList.size() > j && shuffledKeysList.get(j).size() > k) {
					List<keyValuePair<Character,keyValuePair<Integer, Integer>>> partitionedList = shuffledKeysList.get(j).get(k);
					comparator.sort(partitionedList);
					for (keyValuePair<Character,keyValuePair<Integer, Integer>> keyVal : partitionedList)
						System.out.println("<" + keyVal.getKey() + "," + keyVal.getValue() + ">");
				}
			}
		}

		return partitionPairs;
	

	}
	private void printListOfkeyValuePair(
			List<keyValuePair<Character, keyValuePair<Integer, Integer>>> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (keyValuePair<Character, keyValuePair<Integer, Integer>> word : list) {
				System.out.println("<" + word.getKey() + "," + word.getValue()
						+ ">");

			}
		}
	}

	private static void printListOfGroupByPair(
			List<groupByPair<Character, keyValuePair<Integer, Integer>>> reducerInput) {
		if (reducerInput != null) {
			for (groupByPair<Character,keyValuePair<Integer, Integer>> item : reducerInput) {
				System.out.println("<" + item.getKey() + "," + item.getValues()
						+ ">");

			}
		}
	}
	
	private void printKeyValue(List<keyValuePair<Character, Double>> reducerOutput) {
		// TODO Auto-generated method stub
		if(reducerOutput !=null)
		{
			for(keyValuePair<Character,Double> item : reducerOutput)
			{
				System.out.println("< "+item.getKey()+","+item.getValue().doubleValue()+">");
			}
		}
		
	}

}
