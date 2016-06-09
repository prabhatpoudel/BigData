package hybridApproach;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

public class HybridReducer extends Reducer<KeyPair, IntWritable, Text, Text> {

	// Initialization
	private MapWritable mapper = new MapWritable();
	private String previousKey = "";
	private int totalFreq = 0;

	@Override
	public void reduce(KeyPair key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		int sum = 0;

		for (IntWritable value : values) {
			sum += value.get();
		}

		String keyKeyPair = key.getKey().toString();
		String neighborKeyPair = key.getValue().toString();

		// emit
		if (!previousKey.equals(keyKeyPair)) {
			String result = "";
			int count = 0;
			for (Entry<Writable, Writable> valEntry : mapper.entrySet()) {

				String keyItem = ((Text) valEntry.getKey()).toString();
				double valueItem = (double) ((IntWritable) valEntry.getValue())
						.get() / totalFreq;

				count++;
				if (count == mapper.size())
					result += keyItem + " = " + valueItem;
				else
					result += keyItem + " = " + valueItem + " , ";
			}

			context.write(new Text(previousKey), new Text(result));

			mapper.clear();
			totalFreq = 0;
		}

		totalFreq += sum;
		IntWritable newValue = mapper.get(neighborKeyPair) == null ? new IntWritable(
				sum) : new IntWritable(
				((IntWritable) mapper.get(neighborKeyPair)).get() + sum);
		mapper.put(new Text(neighborKeyPair), newValue);
		previousKey = keyKeyPair;
	}

	@Override
	public void cleanup(Context context) throws IOException,
			InterruptedException {

		String result = "";
		int count = 0;

		// emit last entry
		for (Entry<Writable, Writable> valEntry : mapper.entrySet()) {
			String keyItem = ((Text) valEntry.getKey()).toString();
			double valueItem = (double) ((IntWritable) valEntry.getValue())
					.get() / totalFreq;

			count++;
			if (count == mapper.size())
				result += keyItem + " = " + valueItem;
			else
				result += keyItem + " = " + valueItem + " , ";

		}

		context.write(new Text(previousKey), new Text(result));
		mapper.clear();
	}

}
