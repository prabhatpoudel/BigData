package Lab3;

public class keyValuePair<k extends Comparable<k>, v> implements Comparable<keyValuePair<k, v>> {
	private k key;
	private v value;

	public keyValuePair(k key, v value) {
		this.key = key;
		this.value = value;
	}

	public k getKey() {
		return key;
	}

	public v getValue() {
		return value;
	}

	public void setKey(k key) {
		this.key = key;
	}

	public void setValue(v value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "<" + key + " , " + value + " >";
	}


	@Override
	public int compareTo(keyValuePair<k, v> o) {
		// TODO Auto-generated method stub
		return key.compareTo(o.getKey());
	}
}