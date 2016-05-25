package Lab2;

import java.util.ArrayList;
import java.util.List;

public class groupByPair{

	private String key;
	private List<Integer> values;
	
	groupByPair(String key)
	{
		this.key =  key;
		values = new ArrayList<Integer>();
		addValue();
	}
	
	public void addValue()
	{
		this.values.add(1);
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public List<Integer> getValues()
	{
		return values;
	}
	
	@Override
	public String toString()
	{
		return "< " + key + " , " + values.toString() + " >";
	}
}
