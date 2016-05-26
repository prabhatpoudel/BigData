package Lab3;

import Lab3.groupByPair;

public class wordProcess {

	private mapper[] mapper;
	private reducer[] reducer;

	public wordProcess(int m, int r) {
		this.mapper = new mapper[m];
		this.reducer = new reducer[r];
	}

	public int getPartition(String key) {
		return (int) Math.abs(key.hashCode() % reducer.length);
	}

	public static void main(String args[]) {
		/*
		 * mapper map = new
		 * mapper("C:/Users/DELL/Git_new/BigData/src/Lab3/words.txt");
		 * map.mapValues(); map.printUnSortedList(); map.printSortedList();
		 * 
		 * reducer reduce = new reducer(map.mapValues());
		 * 
		 * reduce.groupKey();
		 * 
		 * reduce.printGroupedList(); reduce.printReducedList();
		 */
		wordProcess wp = new wordProcess(3, 4);
		wp.shuffleSort();
	}

	public void shuffleSort() {
		for (int r = 0; r < reducer.length; r++) {
			reducer[r] = new reducer();
		}

		for (int i = 0; i < mapper.length; i++) {
			// System.out.println("Mapper " + i + " output");

			mapper[i] = new mapper("C:/Users/DELL/Git_new/BigData/src/Lab3/words"+i+".txt");
			mapper[i].mapValues();
			// mappers[i].printPairs();

			for (keyValuePair<String, Integer> m : mapper[i].getValuesList()) {
				reducer[getPartition(m.getKey())].addReduceList(m);
			}
		}

		System.out.println("------------------------------------");
		for (int m = 0; m < reducer.length; m++) {
			System.out.println("-----Reducer " + m + " mapped output--------");
			reducer[m].getReduceList().stream().sorted().forEach(System.out::println);
		}

		System.out.println("------------------------------------");
		for (int r = 0; r < reducer.length; r++) {
			System.out.println("Reducer " + r + " merged output");
			reducer[r].groupKey().stream().sorted().forEach(System.out::println);
		}
		// System.out.println("------------------------------------");
		// for (int red= 0; red < reducer.length; red++) {
		// System.out.println("Reducer " + red + " reduced output");
		// reducer[red].groupKey().stream().sorted().forEach(System.out::println);
		// }
		System.out.println("--------------------------");
		for (int r = 0; r < reducer.length; r++) {
			System.out.println("Reducer "+r+" output");
			for (groupByPair groupWord : reducer[r].groupKey()) {
				System.out.println("< " + groupWord.getKey() + " , "
						+ groupWord.getValues().stream().mapToInt(i -> i.intValue()).sum() + " >");
			}
		}
	}

}
