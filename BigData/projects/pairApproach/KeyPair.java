package pairApproach;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class KeyPair implements Writable, WritableComparable<KeyPair> {

	private Text key;
	private Text value;

	public KeyPair(Text key, Text value) {
		this.key = key;
		this.value = value;
	}

	public KeyPair() {
		key = new Text();
		value = new Text();
	}

	public Text getKey() {
		return key;
	}

	public void setKey(Text key) {
		this.key = key;
	}

	public Text getValue() {
		return value;
	}

	public void setValue(Text value) {
		this.value = value;
	}

	@Override
	public void readFields(DataInput in) throws IOException {

		System.out.println("read");
		this.key.readFields(in);
		this.value.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {

		System.out.println("write");
		key.write(out);
		value.write(out);
	}

	@Override
	public int compareTo(KeyPair o) {

		System.out.println("compare");
		if (!(this.key.toString().equals(o.key.toString())))
			return this.key.toString().compareTo(o.key.toString());

		return this.value.toString().compareTo(o.value.toString());

		// KeyPair pair2 = (KeyPair) o;
		// if(this.getKey().compareTo(pair2.getKey())==0){
		// if(this.value.equals("*")){
		// return 1;
		// }else if (pair2.value.equals("*")){
		// return -1;
		// }
		// return this.getValue().compareTo(pair2.getValue());
		// }
		// return this.getKey().compareTo(pair2.getKey());
	}

	@Override
	public int hashCode() {
		int result = 0;
		result += key.hashCode() + value.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "{" + key + ", " + value + "}";
	}

	@Override
	public boolean equals(Object o) {
		KeyPair pair2 = (KeyPair) o;
		if (pair2.key.equals(this.key) && pair2.value.equals(this.value))
			return true;
		return false;
	}
}
