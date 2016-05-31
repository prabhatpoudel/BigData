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
		return (int) string.hashCode() % reducer.length;
	}

	public static void main(String args[]) {
		InvertedIndexProcess wp = new InvertedIndexProcess(4, 3);
		wp.processWord();
	}

	private void processWord() {
        
		final String dir = System.getProperty("user.dir");
        //System.out.println("current dir = " + dir);
        
		List<List<keyValuePair<keyValuePair<String, String>,String>>> mappedPairs = new ArrayList<>();
		for (int i = 0; i < mapper.length; i++) {
			mapper[i] = new invertedIndexMapper(dir+
					"/week2/week2Lab2/Mapper"
							+ i + ".txt");
			
			
//			mapper[i].mapValues();
			// mappers[i].printPairs();
			//mapper[i].mapValues();
			//List<keyValuePair<keyValuePair<String, String>,String>> list = mapper[i].inMapperPairs();
			List<keyValuePair<keyValuePair<String, String>,String>> list = mapper[i].mapValues();
			System.out.println("\n Mapper " + i + " Output \n");
			mappedPairs.add(list);
			printListOfkeyValuePair(list);
		}

		// Now apply shuffle Sort

		List<List<keyValuePair<keyValuePair<String, String>,String>>> partitionPairs = shuffleSort(mappedPairs);
		
	//System.out.println(partitionPairs);

		// Combine the mapped and partioned pairs to in a single list
		List<List<groupByPair<keyValuePair<String, String>,String>>> reducerInputList = new ArrayList<List<groupByPair<keyValuePair<String, String>,String>>>();
		for (int i = 0; i < reducer.length; i++) {

			InvertedIndexReducer reducer = new InvertedIndexReducer();
			List<groupByPair<keyValuePair<String, String>,String>> reducerInput = reducer
					.groupKey(partitionPairs.get(i));
			System.out.println("\nReducer " + i+ " Input \n");
			printListOfGroupByPair(reducerInput);

			reducerInputList.add(reducerInput);

		}

		// Reduce the pairs values
		for (int i = 0; i < reducer.length; i++) {

			InvertedIndexReducer reducer = new InvertedIndexReducer();
			List<groupByPair<keyValuePair<String, String>,String>> reducerInput = reducerInputList.get(i);
			System.out.println("\n Reducer " + i + " Output \n");
			List<keyValuePair<keyValuePair<String,String>, String>> reducerOutput = reducer.numberReduce(reducerInput);
			printKeyValue(reducerOutput);
		}

	}



	private List<List<keyValuePair<keyValuePair<String, String>,String>>> shuffleSort(
			List<List<keyValuePair<keyValuePair<String, String>,String>>> allMappedPairs) {
		List<List<keyValuePair<keyValuePair<String, String>,String>>> partitionPairs = new ArrayList<List<keyValuePair<keyValuePair<String, String>,String>>>();
		List<List<List<keyValuePair<keyValuePair<String, String>,String>>>> shuffledKeysList = new ArrayList<>();

		for (int i = 0; i < this.mapper.length; i++) {
			shuffledKeysList.add(new ArrayList<List<keyValuePair<keyValuePair<String, String>,String>>>());
		}
		for (int i = 0; i < this.mapper.length; i++) {
			for (int j = 0; j < this.reducer.length; j++) {
				shuffledKeysList.get(i).add(new ArrayList<keyValuePair<keyValuePair<String, String>,String>>());
			}
		}

		for (int i = 0; i < this.reducer.length; i++) {
			partitionPairs.add(new ArrayList<keyValuePair<keyValuePair<String, String>,String>>());
		}

		// shuffle step
		int i = 0;
//		System.out.println(shuffledKeysList);
		for (List<keyValuePair<keyValuePair<String, String>,String>> list : allMappedPairs) {
			for (keyValuePair<keyValuePair<String, String>,String> pair : list) {
				int partitionLevel = getPartition(pair.getKey().getKey());

				shuffledKeysList.get(i).get(partitionLevel).add(pair);
				partitionPairs.get(partitionLevel).add(pair);
			}

			i++;

		}
		InvertedIndexComparator<String, String,String> comparator = new InvertedIndexComparator<>();
		for (int j = 0; j < this.mapper.length; j++) {
			for (int k = 0; k < this.reducer.length; k++) {
				System.out.println("\n Pairs send from Mapper " + j + " to Reducer " + k + "\n");
				if (shuffledKeysList.size() > j && shuffledKeysList.get(j).size() > k) {
					List<keyValuePair<keyValuePair<String, String>,String>> partitionedList = shuffledKeysList.get(j).get(k);
					comparator.descSort(partitionedList);
					for (keyValuePair<keyValuePair<String, String>,String> keyVal : partitionedList)
						System.out.println("<" + keyVal.getKey() + "," + keyVal.getValue() + ">");
				}
			}
		}

		return partitionPairs;
	

	}
	private void printListOfkeyValuePair(
			List<keyValuePair<keyValuePair<String, String>,String>> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (keyValuePair<keyValuePair<String, String>,String> word : list) {
				System.out.println("< " + word.getKey() + " , " + word.getValue()
						+ " >");

			}
		}
	}

	private static void printListOfGroupByPair(
			List<groupByPair<keyValuePair<String, String>,String>> reducerInput) {
		if (reducerInput != null) {
			for (groupByPair<keyValuePair<String, String>,String> item : reducerInput) {
				System.out.println("<" + item.getKey() + "," + item.getValues()
						+ ">");

			}
		}
	}
	
	private void printKeyValue(List<keyValuePair<keyValuePair<String, String>,String>> reducerOutput) {
		// TODO Auto-generated method stub
		if(reducerOutput !=null)
		{
			for(keyValuePair<keyValuePair<String,String>,String> item : reducerOutput)
			{
				System.out.println("< "+item.getKey()+","+item.getValue()+">");
			}
		}
		
	}
}
