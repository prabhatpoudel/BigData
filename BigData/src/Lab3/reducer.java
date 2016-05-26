package Lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Lab3.keyValuePair;
import Lab3.groupByPair;

public class reducer {
	
	public  List<groupByPair> reducedList = new ArrayList<groupByPair>();
	public List<keyValuePair<String, Integer>> pairList = new ArrayList<keyValuePair<String, Integer>>();
	
	public reducer(List<keyValuePair<String, Integer>> list)
	{
		this.pairList=list;
	}
	
	public reducer()
	{
		reducedList = new ArrayList<groupByPair>();
		pairList = new ArrayList<keyValuePair<String, Integer>>();
	}
	public List<groupByPair> groupKey() {
		Collections.sort(pairList);
		for (int i = 0; i < pairList.size(); i++) {
			String key = pairList.get(i).getKey();
			groupByPair group = new groupByPair(key);

			// Searching for the Same Key if exist then add the value 1 in the
			// list
			while (i < pairList.size() - 1 && pairList.get(i + 1).getKey().equals(key))
			// if(valuesList.get(i).getKey().equals(key))
			{
				group.addValue();
				i++;
			}
			reducedList.add(group);
		}
		return reducedList;
	}

	public void printGroupedList()
	{
		System.out.println("-------Grouped List---------");
		reducedList.stream().forEach(System.out::println);
		System.out.println("----------------------------");
	}
	
	public void printReducedList()
	{
		for (groupByPair reduceWord : reducedList) {
			System.out.println("< " + reduceWord.getKey() + " , "
					+ reduceWord.getValues().stream().mapToInt(i -> i.intValue()).sum() + " >");
		}

	}
	
	public void addReduceList(keyValuePair<String,Integer> v)
	{
		pairList.add(v);
	}
	
	public List<keyValuePair<String, Integer>> getReduceList() {
		return pairList;
	}

	@Override
	public String toString() {
		return "reducer [reducedList=" + reducedList + ", pairList=" + pairList + "]";
	}
	
	
}
