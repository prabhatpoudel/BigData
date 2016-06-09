package stripeApproach;

import java.util.Set;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;

public class StripeWriteable extends MapWritable{

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Set<Writable> keys = keySet();
		String output="";		
		for(Writable key : keys){
			output += "{" + key + "," + get(key) + "} ";
		}
		return output;
	}
}
