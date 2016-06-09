package stripeApproach;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

import stripeApproach.KeyPair;

public class StripeReducer extends
		Reducer<Text, MapWritable, Text, StripeWriteable> {

	StripeWriteable finalOutput;

	@Override
	protected void reduce(Text term, Iterable<MapWritable> stripes,
			Reducer<Text, MapWritable, Text, StripeWriteable>.Context context)
			throws IOException, InterruptedException {

		finalOutput = new StripeWriteable();
		int total = 0;
		for (MapWritable stripe : stripes) {
			total += addAllStripes(stripe);
		}

		Set<Writable> keys = finalOutput.keySet();
		for (Writable key : keys) {
			IntWritable pCount = (IntWritable) finalOutput.get(key);
			double relativeFrequency = pCount.get() * 1.0 / total;
			finalOutput.put(key, new DoubleWritable(relativeFrequency));
		}
		context.write(term, finalOutput);

	}

	private int addAllStripes(MapWritable stripe) {
		Set<Writable> keys = stripe.keySet();
		int total = 0;
		for (Writable key : keys) {
			IntWritable count = (IntWritable) stripe.get(key);
			total += count.get();
			if (finalOutput.containsKey(key)) {
				IntWritable pCount = (IntWritable) finalOutput.get(key);
				pCount.set(count.get() + pCount.get());

			} else {
				finalOutput.put(key, count);

			}
		}
		return total;
	}

}
