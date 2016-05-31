package week2Lab2;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import week2Lab2.groupByPair;

public class InvertedIndexProcess {

	private invertedIndexMapper[] mapper;
	private InvertedIndexReducer[] reducer;

	public InvertedIndexProcess(int m, int r) {
		this.mapper = new invertedIndexMapper[m];
		this.reducer = new InvertedIndexReducer[r];
	}

	public int getPartition(String string) {
		return  Math.abs((int) string.hashCode()) % reducer.length;
		
	}

	public static void main(String args[]) {
		InvertedIndexProcess wp = new InvertedIndexProcess(4, 4);
		wp.processWord();
	}

	private void processWord() {
        
		final String dir = System.getProperty("user.dir");
        //System.out.println("current dir = " + dir);
        
		List<List<keyValuePair<keyValuePair<String, Integer>,Integer>>> mappedPairs = new ArrayList<>();
		for (int i = 0; i < mapper.length; i++) {
			mapper[i] = new invertedIndexMapper(dir+
					"/week2/week2Lab2/Mapper"
							+ i + ".txt");
			
			List<keyValuePair<keyValuePair<String, Integer>,Integer>> list = mapper[i].inMapperPairs();
			System.out.println("\n Mapper " + i + " Output \n");
			mappedPairs.add(list);
			printListOfkeyValuePair(list);
		}

		// Now apply shuffle Sort

		List<List<keyValuePair<keyValuePair<String, Integer>,Integer>>> partitionPairs = shuffleSort(mappedPairs);
		
	//System.out.println(partitionPairs);

		// Combine the mapped and partioned pairs to in a single list
		List<List<groupByPair<keyValuePair<String, Integer>,Integer>>> reducerInputList = new ArrayList<List<groupByPair<keyValuePair<String, Integer>,Integer>>>();
		for (int i = 0; i < reducer.length; i++) {

			InvertedIndexReducer reducer = new InvertedIndexReducer();
			List<groupByPair<keyValuePair<String, Integer>,Integer>> reducerInput = reducer
					.groupKey(partitionPairs.get(i));
			System.out.println("\nReducer " + i+ " Input \n");
			printListOfGroupByPair(reducerInput);

			reducerInputList.add(reducerInput);

		}

		// Reduce the pairs values
		for (int i = 0; i < reducer.length; i++) {

			InvertedIndexReducer reducer = new InvertedIndexReducer();
			List<groupByPair<keyValuePair<String, Integer>,Integer>> reducerInput = reducerInputList.get(i);
			System.out.println("\n Reducer " + i + " Output \n");
			List<keyValuePair<String, List<keyValuePair<Integer, Integer>>>> reducerOutput = reducer.numberReduce(reducerInput);
			printKeyValue(reducerOutput);
		}

	}



	private List<List<keyValuePair<keyValuePair<String, Integer>,Integer>>> shuffleSort(
			List<List<keyValuePair<keyValuePair<String, Integer>,Integer>>> allMappedPairs) {
		List<List<keyValuePair<keyValuePair<String, Integer>,Integer>>> partitionPairs = new ArrayList<List<keyValuePair<keyValuePair<String, Integer>,Integer>>>();
		List<List<List<keyValuePair<keyValuePair<String, Integer>,Integer>>>> shuffledKeysList = new ArrayList<>();

		for (int i = 0; i < this.mapper.length; i++) {
			shuffledKeysList.add(new ArrayList<List<keyValuePair<keyValuePair<String, Integer>,Integer>>>());
		}
		for (int i = 0; i < this.mapper.length; i++) {
			for (int j = 0; j < this.reducer.length; j++) {
				shuffledKeysList.get(i).add(new ArrayList<keyValuePair<keyValuePair<String, Integer>,Integer>>());
			}
		}

		for (int i = 0; i < this.reducer.length; i++) {
			partitionPairs.add(new ArrayList<keyValuePair<keyValuePair<String, Integer>,Integer>>());
		}

		// shuffle step
		int i = 0;
//		System.out.println(shuffledKeysList);
		for (List<keyValuePair<keyValuePair<String, Integer>,Integer>> list : allMappedPairs) {
			for (keyValuePair<keyValuePair<String, Integer>,Integer> pair : list) {
				int partitionLevel = getPartition(pair.getKey().getKey());

				shuffledKeysList.get(i).get(partitionLevel).add(pair);
				partitionPairs.get(partitionLevel).add(pair);
			}

			i++;

		}
		InvertedIndexComparator<String, Integer,Integer> comparator = new InvertedIndexComparator<>();
		for (int j = 0; j < this.mapper.length; j++) {
			for (int k = 0; k < this.reducer.length; k++) {
				System.out.println("\n Pairs send from Mapper " + j + " to Reducer " + k + "\n");
				if (shuffledKeysList.size() > j && shuffledKeysList.get(j).size() > k) {
					List<keyValuePair<keyValuePair<String, Integer>,Integer>> partitionedList = shuffledKeysList.get(j).get(k);
					comparator.descSort(partitionedList);
					for (keyValuePair<keyValuePair<String, Integer>,Integer> keyVal : partitionedList)
						System.out.println("<" + keyVal.getKey() + "," + keyVal.getValue() + ">");
				}
			}
		}

		return partitionPairs;
	

	}
	private void printListOfkeyValuePair(
			List<keyValuePair<keyValuePair<String, Integer>,Integer>> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (keyValuePair<keyValuePair<String, Integer>,Integer> word : list) {
				System.out.println("< " + word.getKey() + " , " + word.getValue()
						+ " >");

			}
		}
	}

	private static void printListOfGroupByPair(
			List<groupByPair<keyValuePair<String, Integer>, Integer>> reducerInput) {
		if (reducerInput != null) {
			for (groupByPair<keyValuePair<String, Integer>,Integer> item : reducerInput) {
				System.out.println("<" + item.getKey() + "," + item.getValues()
						+ ">");

			}
		}
	}
	
	private void printKeyValue(List<keyValuePair<String, List<keyValuePair<Integer, Integer>>>> reducerOutput) {
		// TODO Auto-generated method stub
		if(reducerOutput !=null)
		{
			for(keyValuePair<String, List<keyValuePair<Integer, Integer>>> item : reducerOutput)
			{
				System.out.println("< "+item.getKey()+","+item.getValue()+">");
			}
		}
		
	}
}
