package lab1;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lab1.groupByPair;

public class stripsNumberProcess {

	private stripsMapper[] mapper;
	private pairReducer[] reducer;

	public stripsNumberProcess(int m, int r) {
		this.mapper = new stripsMapper[m];
		this.reducer = new pairReducer[r];
	}

	public int getPartition(Integer integer) {
		return (int) integer.hashCode() % reducer.length;
	}

	public static void main(String args[]) {
		stripsNumberProcess wp = new stripsNumberProcess(2, 4);
		wp.processNumber();
	}

	private void processNumber() {
        
		final String dir = System.getProperty("user.dir");
        //System.out.println("current dir = " + dir);
        
		List<List<keyValuePair<Integer,HashMap<Integer, Integer>>>> mappedPairs = new ArrayList<>();
		for (int i = 0; i < mapper.length; i++) {
			mapper[i] = new stripsMapper(dir+
					"/src/Lab5/words"+i+".txt");
			
			
//			mapper[i].mapValues();
			// mappers[i].printPairs();
			System.out.println(" I am "+i);
			//mapper[i].mapValues();
			List<keyValuePair<Integer,HashMap<Integer,Integer>>> list = mapper[i].mapValues();
			System.out.println("\n_____________Mapper " + i
					+ " Output_____________\n");
			mappedPairs.add(list);
			printListOfkeyValuePair(list);
		}

		// Now apply shuffle Sort

		List<List<keyValuePair<Integer,HashMap<Integer,Integer>>>> partitionPairs = shuffleSort(mappedPairs);

	//System.out.println(partitionPairs);

		// Combine the mapped and partioned pairs to in a single list
		List<List<groupByPair<Integer,HashMap<Integer,Integer>>>> reducerInputList = new ArrayList<List<groupByPair<Integer,HashMap<Integer,Integer>>>>();
		for (int i = 0; i < reducer.length; i++) {

			stripsReducer reducer = new stripsReducer();
			List<groupByPair<Integer,HashMap<Integer,Integer>>> reducerInput = reducer
					.groupKey(partitionPairs.get(i));
			System.out.println("\n_____________Reducer " + i
					+ " Input_____________\n");
			printListOfGroupByPair(reducerInput);

			reducerInputList.add(reducerInput);

		}

		// Reduce the pairs values
		for (int i = 0; i < reducer.length; i++) {

			stripsReducer reducer = new stripsReducer();
			List<groupByPair<Integer,HashMap<Integer,Integer>>> reducerInput = reducerInputList.get(i);
			System.out.println("\n_____________Reducer " + i
					+ " Output_____________\n");
			List<keyValuePair<Integer,HashMap<Integer,Integer>>> reducerOutput = reducer
					.numberReduce(reducerInput);
			printKeyValue(reducerOutput);
		}
	}



	private List<List<keyValuePair<Integer,HashMap<Integer,Integer>>>> shuffleSort(
			List<List<keyValuePair<Integer,HashMap<Integer,Integer>>>> allMappedPairs) {
		List<List<keyValuePair<Integer,HashMap<Integer,Integer>>>> partitionPairs = new ArrayList<List<keyValuePair<Integer,HashMap<Integer,Integer>>>>();
		List<List<List<keyValuePair<Integer,HashMap<Integer,Integer>>>>> shuffledKeysList = new ArrayList<>();

		for (int i = 0; i < this.mapper.length; i++) {
			shuffledKeysList.add(new ArrayList<List<keyValuePair<Integer,HashMap<Integer, Integer>>>>());
		}
		for (int i = 0; i < this.mapper.length; i++) {
			for (int j = 0; j < this.reducer.length; j++) {
				shuffledKeysList.get(i).add(new ArrayList<keyValuePair<Integer,HashMap<Integer, Integer>>>());
			}
		}

		for (int i = 0; i < this.reducer.length; i++) {
			partitionPairs.add(new ArrayList<keyValuePair<Integer,HashMap<Integer, Integer>>>());
		}

		// shuffle step
		int i = 0;
//		System.out.println(shuffledKeysList);
		for (List<keyValuePair<Integer,HashMap<Integer, Integer>>> list : allMappedPairs) {
			for (keyValuePair<Integer,HashMap<Integer, Integer>> pair : list) {
				int partitionLevel = getPartition(pair.getKey());

				shuffledKeysList.get(i).get(partitionLevel).add(pair);
				partitionPairs.get(partitionLevel).add(pair);
			}

			i++;

		}
		stripsComparator<Integer, HashMap<Integer,Integer>> comparator = new stripsComparator<>();
		for (int j = 0; j < this.mapper.length; j++) {
			for (int k = 0; k < this.reducer.length; k++) {
				System.out.println("\n________Pairs sent from Mapper " + j + " to Reducer " + k + "__________\n");
				if (shuffledKeysList.size() > j && shuffledKeysList.get(j).size() > k) {
					List<keyValuePair<Integer,HashMap<Integer, Integer>>> partitionedList = shuffledKeysList.get(j).get(k);
					comparator.sort(partitionedList);
					for (keyValuePair<Integer,HashMap<Integer, Integer>> keyVal : partitionedList)
						System.out.println("<" + keyVal.getKey() + "," + keyVal.getValue() + ">");
				}
			}
		}

		return partitionPairs;
	

	}
	
	private void printListOfkeyValuePair(
			List<keyValuePair<Integer,HashMap<Integer,Integer>>> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (keyValuePair<Integer,HashMap<Integer,Integer>> word : list) {
				System.out.println("<" + word.getKey() + "," + word.getValue()
						+ ">");

			}
		}
	}

	private static void printListOfGroupByPair(
			List<groupByPair<Integer,HashMap<Integer,Integer>>> reducerInput) {
		if (reducerInput != null) {
			for (groupByPair<Integer,HashMap<Integer,Integer>> item : reducerInput) {
				System.out.println("<" + item.getKey() + "," + item.getValues()
						+ ">");

			}
		}
	}
	
	private void printKeyValue(List<keyValuePair<Integer,HashMap<Integer,Integer>>> reducerOutput) {
		// TODO Auto-generated method stub
		if(reducerOutput !=null)
		{
			for(keyValuePair<Integer,HashMap<Integer,Integer>> item : reducerOutput)
			{
				System.out.println("< "+item.getKey()+","+item.getValue()+">");
			}
		}
		
	}
	
}
