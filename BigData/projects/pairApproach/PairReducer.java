package pairApproach;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class PairReducer extends
		Reducer<KeyPair, IntWritable, KeyPair, DoubleWritable> {
	DoubleWritable totalCount = new DoubleWritable();
	int total = 0;

	@Override
	protected void reduce(KeyPair pair, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		
		int sum = 0;

		for (IntWritable count : values) {
			// System.out.println(count);
			sum += count.get();
		}
		if (pair.getValue().toString().equals("*")) {
			total = sum;
		} else {
			totalCount.set(sum * 1.0 / total);
			System.out.println(pair.getKey() + "," + pair.getValue() + " "
					+ totalCount.toString());
			context.write(pair, totalCount);
		}

	}
}